package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;

@WebServlet("/update-cart")
public class UpdateCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        String variationIdStr = request.getParameter("variationId");
        String quantityStr = request.getParameter("quantity");

        int variationId, quantity;
        try {
            variationId = Integer.parseInt(variationIdStr);
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("text/plain");
            response.getWriter().write("Invalid parameters");
            return;
        }

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

        String action;
        if (quantity > 0) {
            cartMap.put(variationId, quantity);
            action = "updated";
        } else {
            cartMap.remove(variationId);
            action = "removed";
        }

        StringBuilder cookieValue = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : cartMap.entrySet()) {
            if (cookieValue.length() > 0) cookieValue.append("|");
            cookieValue.append(entry.getKey()).append(":").append(entry.getValue());
        }

        Cookie updatedCookie = new Cookie("cart", cookieValue.toString());
        updatedCookie.setPath("/");
        updatedCookie.setMaxAge(60 * 60 * 24 * 7); // 7 ngày
        response.addCookie(updatedCookie);

        response.setContentType("text/plain");
        response.getWriter().write(action + "|" + variationId + "|" + quantity);
    }
}
