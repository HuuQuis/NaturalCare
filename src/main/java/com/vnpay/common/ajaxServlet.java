package com.vnpay.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dal.OrderDAO;
import dal.ProductVariationDAO;
import model.Cart;
import model.ProductVariation;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(name = "ajaxServlet", urlPatterns = {"/payment"})
public class ajaxServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        int userId = user.getId();
        int addressId = Integer.parseInt(req.getParameter("addressId"));
        String note = req.getParameter("note");
        long amount = Integer.parseInt(req.getParameter("amount")) * 100;
        String bankCode = req.getParameter("bankCode");

        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = Config.getIpAddress(req);
        String vnp_TmnCode = Config.vnp_TmnCode;

        OrderDAO orderDAO = new OrderDAO();
        ProductVariationDAO variationDAO = new ProductVariationDAO();

        // 1. Đọc giỏ hàng từ cookie
        Map<Integer, Integer> cartMap = readCartFromCookie(req);
        List<Cart> cartItems = new ArrayList<>();
        int cartTotal = 0;

        // 2. Kiểm tra tồn kho
        for (Map.Entry<Integer, Integer> entry : cartMap.entrySet()) {
            int variationId = entry.getKey();
            int quantity = entry.getValue();

            ProductVariation variation = variationDAO.getProductVariationById(variationId);
            if (variation == null) continue;

            if (quantity > variation.getQtyInStock()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonObject error = new JsonObject();
                error.addProperty("code", "01");
                error.addProperty("message", "Product \"" + variation.getProductName() +
                        "\" only has " + variation.getQtyInStock() + " item(s) left in stock.");
                resp.getWriter().write(new Gson().toJson(error));
                return;
            }

            cartItems.add(new Cart(variation, quantity));
            cartTotal += variation.getSell_price() * quantity;
        }

        // 3. Tạo order
        int orderId = orderDAO.insertOrderVNPAY(userId, note, addressId, vnp_TxnRef);
        if (orderId == -1) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        // 4. Insert order_detail + trừ tồn kho
        for (Cart item : cartItems) {
            orderDAO.insertOrderDetail(orderId,
                    item.getVariation().getVariationId(),
                    item.getQuantity(),
                    item.getVariation().getSell_price());

            variationDAO.updateStockAfterOrder(item.getVariation().getVariationId(), item.getQuantity());
        }

        // 5. Tạo link thanh toán VNPAY
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Payment for order #" + orderId);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", req.getParameter("language") != null ? req.getParameter("language") : "vn");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String createDate = formatter.format(cal.getTime());
        vnp_Params.put("vnp_CreateDate", createDate);
        cal.add(Calendar.MINUTE, 15);
        String expireDate = formatter.format(cal.getTime());
        vnp_Params.put("vnp_ExpireDate", expireDate);

        // 6. Build query & hash
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (Iterator<String> it = fieldNames.iterator(); it.hasNext(); ) {
            String fieldName = it.next();
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII))
                        .append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (it.hasNext()) {
                    hashData.append('&');
                    query.append('&');
                }
            }
        }

        String secureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        String paymentUrl = Config.vnp_PayUrl + "?" + query.toString() + "&vnp_SecureHash=" + secureHash;

        JsonObject json = new JsonObject();
        json.addProperty("code", "00");
        json.addProperty("message", "success");
        json.addProperty("data", paymentUrl);
        resp.getWriter().write(new Gson().toJson(json));
    }

    private Map<Integer, Integer> readCartFromCookie(HttpServletRequest request) {
        Map<Integer, Integer> map = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("cart".equals(c.getName())) {
                    String[] items = c.getValue().split("\\|");
                    for (String item : items) {
                        String[] parts = item.split(":");
                        if (parts.length == 2) {
                            try {
                                int id = Integer.parseInt(parts[0]);
                                int qty = Integer.parseInt(parts[1]);
                                map.put(id, qty);
                            } catch (NumberFormatException ignored) {}
                        }
                    }
                }
            }
        }
        return map;
    }
}
