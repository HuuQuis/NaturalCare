package controller;

import dal.AddressDAO;
import dal.OrderDAO;
import dal.ProductVariationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Address;
import model.Cart;
import model.ProductVariation;
import model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    AddressDAO addressDAO = new AddressDAO();
    OrderDAO orderDAO = new OrderDAO();
    ProductVariationDAO productVariationDAO = new ProductVariationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect("login");
            return;
        }

        List<Address> addressList = addressDAO.getAddressesByUserId(currentUser.getId());
        request.setAttribute("addressList", addressList);

        Address defaultAddress = null;
        for (Address addr : addressList) {
            if (addr.isDefaultAddress()) {
                defaultAddress = addr;
                break;
            }
        }
        request.setAttribute("defaultAddress", defaultAddress);

        Map<Integer, Integer> cartMap = readCartFromCookie(request);
        List<Cart> cartItems = new ArrayList<>();
        int cartTotal = 0;

        for (Map.Entry<Integer, Integer> entry : cartMap.entrySet()) {
            int variationId = entry.getKey();
            int quantity = entry.getValue();

            ProductVariation variation = productVariationDAO.getProductVariationById(variationId);
            if (variation != null) {
                cartItems.add(new Cart(variation, quantity));
                cartTotal += variation.getSell_price() * quantity;
            }
        }

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("cartTotal", cartTotal);
        request.getRequestDispatcher("/view/checkout/checkout.jsp").forward(request, response);
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect("login");
            return;
        }

        // 1. Lấy thông tin form
        String addressIdStr = request.getParameter("addressId");
        String note = request.getParameter("note");

        if (addressIdStr == null || addressIdStr.isEmpty()) {
            request.setAttribute("error", "Please select a shipping address.");
            doGet(request, response);
            return;
        }

        int addressId = Integer.parseInt(addressIdStr);

        // 2. Đọc giỏ hàng từ cookie
        Map<Integer, Integer> cartMap = readCartFromCookie(request);
        List<Cart> cartItems = new ArrayList<>();
        int cartTotal = 0;

        // 3. Kiểm tra tồn kho
        for (Map.Entry<Integer, Integer> entry : cartMap.entrySet()) {
            int variationId = entry.getKey();
            int quantity = entry.getValue();

            ProductVariation variation = productVariationDAO.getProductVariationById(variationId);
            if (variation == null) continue;

            if (quantity > variation.getQtyInStock()) {
                // Nếu vượt quá tồn kho, báo lỗi
                request.setAttribute("error", "Product \"" + variation.getProductName() + "\" only has " +
                        variation.getQtyInStock() + " item(s) left in stock.");
                doGet(request, response);
                return;
            }

            cartItems.add(new Cart(variation, quantity));
            cartTotal += variation.getSell_price() * quantity;
        }

        // 4. Lưu order
        int orderId = orderDAO.insertOrder(currentUser.getId(), note, addressId);

        // 5. Lưu order detail
        for (Cart item : cartItems) {
            orderDAO.insertOrderDetail(orderId,
                    item.getVariation().getVariationId(),
                    item.getQuantity(),
                    item.getVariation().getSell_price());

            // Trừ tồn kho (nếu muốn cập nhật)
            productVariationDAO.updateStockAfterOrder(item.getVariation().getVariationId(), item.getQuantity());
        }

        // 6. Xoá cookie giỏ hàng
        Cookie cookie = new Cookie("cart", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        // 7. Redirect
        response.sendRedirect("order-success?orderId=" + orderId);
    }

}

