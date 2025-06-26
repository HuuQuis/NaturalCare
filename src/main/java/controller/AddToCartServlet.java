package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }

        String json = jsonBuffer.toString();

        int variationId;
        int quantity;
        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject(json);
            variationId = jsonObject.getInt("variationId");
            quantity = jsonObject.getInt("quantity");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Invalid JSON input\"}");
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
                            } catch (Exception ignored) {}
                        }
                    }
                }
            }
        }

        cartMap.put(variationId, cartMap.getOrDefault(variationId, 0) + quantity);

        StringBuilder cookieValue = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : cartMap.entrySet()) {
            if (cookieValue.length() > 0) cookieValue.append("|");
            cookieValue.append(entry.getKey()).append(":").append(entry.getValue());
        }

        Cookie newCookie = new Cookie("cart", cookieValue.toString());
        newCookie.setPath("/");
        newCookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(newCookie);

        response.getWriter().write("{\"message\": \"success\"}");
    }
}

