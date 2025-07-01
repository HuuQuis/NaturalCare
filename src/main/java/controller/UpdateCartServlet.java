package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;

import dal.ProductVariationDAO;
import model.ProductVariation;

@WebServlet("/update-cart")
public class UpdateCartServlet extends HttpServlet {
    private final ProductVariationDAO productVariationDAO = new ProductVariationDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");

        String variationIdStr = request.getParameter("variationId");
        String quantityStr = request.getParameter("quantity");

        int variationId, quantity;
        try {
            variationId = Integer.parseInt(variationIdStr);
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            response.getWriter().write("error|Invalid parameters");
            return;
        }

        ProductVariation variation = productVariationDAO.getProductVariationById(variationId);
        if (variation == null) {
            response.getWriter().write("error|Product variation not found");
            return;
        }

        if (variation.getQtyInStock() == 0) {
            removeFromCartCookie(request, response, variationId);
            response.getWriter().write("removed|" + variationId + "|0|This item is out of stock and has been removed from your cart.");
            return;
        }

        if (quantity > variation.getQtyInStock()) {
            response.getWriter().write("error|Requested quantity exceeds available stock (" + variation.getQtyInStock() + ")");
            return;
        }

        Map<Integer, Integer> cartMap = readCartFromCookie(request);
        String action;
        if (quantity > 0) {
            cartMap.put(variationId, quantity);
            action = "updated";
        } else {
            cartMap.remove(variationId);
            action = "removed";
        }

        writeCartToCookie(response, cartMap);
        response.getWriter().write(action + "|" + variationId + "|" + quantity);
    }

    private Map<Integer, Integer> readCartFromCookie(HttpServletRequest request) {
        Map<Integer, Integer> cartMap = new HashMap<>();
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
                                cartMap.put(id, qty);
                            } catch (NumberFormatException ignored) {}
                        }
                    }
                }
            }
        }
        return cartMap;
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

    private void removeFromCartCookie(HttpServletRequest request, HttpServletResponse response, int removeId) {
        Map<Integer, Integer> cartMap = readCartFromCookie(request);
        cartMap.remove(removeId);
        writeCartToCookie(response, cartMap);
    }
}
