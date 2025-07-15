package controller;

import dal.ProductVariationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Cart;
import model.ProductVariation;

import java.io.IOException;
import java.util.*;

@WebServlet("/cart-items")
public class CartItemsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<Integer, Integer> cartMap = readCartFromCookie(request);
        ProductVariationDAO dao = new ProductVariationDAO();

        List<Cart> cartItems = new ArrayList<>();
        int cartTotal = 0;
        int totalQuantity = 0;

        // Danh sách các ID sản phẩm đã hết hàng cần xóa
        List<Integer> toRemove = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : cartMap.entrySet()) {
            int variationId = entry.getKey();
            int quantity = entry.getValue();

            ProductVariation variation = dao.getProductVariationById(variationId);
            // Remove from cart if variant is null, out of stock, or inactive
            if (variation != null && variation.getQtyInStock() > 0 && variation.isActive()) {
                cartItems.add(new Cart(variation, quantity));
                cartTotal += variation.getSell_price() * quantity;
                totalQuantity += quantity;
            } else {
                toRemove.add(variationId);
            }
        }

        // Cập nhật lại cookie nếu có sản phẩm hết hàng
        if (!toRemove.isEmpty()) {
            for (Integer removeId : toRemove) {
                cartMap.remove(removeId);
            }
            writeCartToCookie(response, cartMap);
        }

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("cartTotal", cartTotal);
        request.setAttribute("totalQuantity", totalQuantity);
        request.getRequestDispatcher("/view/checkout/cart-modal.jsp").forward(request, response);
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

    private void writeCartToCookie(HttpServletResponse response, Map<Integer, Integer> cartMap) {
        StringBuilder cookieValue = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : cartMap.entrySet()) {
            if (cookieValue.length() > 0) cookieValue.append("|");
            cookieValue.append(entry.getKey()).append(":").append(entry.getValue());
        }

        Cookie updatedCookie = new Cookie("cart", cookieValue.toString());
        updatedCookie.setPath("/");
        updatedCookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(updatedCookie);
    }
}
