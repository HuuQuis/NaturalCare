package controller;

import dal.ProductVariationDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.ProductVariation;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");

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
            JSONObject jsonObject = new JSONObject(json);
            variationId = jsonObject.getInt("variationId");
            quantity = jsonObject.getInt("quantity");
        } catch (Exception e) {
            response.getWriter().write("error|Invalid JSON input");
            return;
        }

        ProductVariationDAO dao = new ProductVariationDAO();
        ProductVariation variation = dao.getProductVariationById(variationId);

        if (variation == null) {
            response.getWriter().write("error|Product variation not found");
            return;
        }

        if (variation.getQtyInStock() == 0) {
            response.getWriter().write("error|This product is out of stock");
            return;
        }

        Map<Integer, Integer> cartMap = readCartFromCookie(request);
        int currentQty = cartMap.getOrDefault(variationId, 0);
        int maxAllowed = variation.getQtyInStock();
        int newQty = currentQty + quantity;

        if (newQty > maxAllowed) {
            response.getWriter().write("error|Cannot add more than " + (maxAllowed - currentQty) + " item(s) to cart");
            return;
        }

        cartMap.put(variationId, newQty);
        writeCartToCookie(response, cartMap);

        int totalQty = getTotalQuantity(cartMap);
        response.getWriter().write("success|" + totalQty);
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
                            } catch (Exception ignored) {}
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

        Cookie newCookie = new Cookie("cart", cookieValue.toString());
        newCookie.setPath("/");
        newCookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(newCookie);
    }

    private int getTotalQuantity(Map<Integer, Integer> cartMap) {
        int total = 0;
        for (int qty : cartMap.values()) {
            total += qty;
        }
        return total;
    }

}