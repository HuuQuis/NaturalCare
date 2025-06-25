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
        for (Map.Entry<Integer, Integer> entry : cartMap.entrySet()) {
            int variationId = entry.getKey();
            int quantity = entry.getValue();

            ProductVariation variation = dao.getProductVariationById(variationId);
            if (variation != null) {
                cartItems.add(new Cart(variation, quantity));
            }
        }

        request.setAttribute("cartItems", cartItems);
        request.getRequestDispatcher("/view/home/cart-modal.jsp").forward(request, response);

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
                            map.put(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                        }
                    }
                }
            }
        }
        return map;
    }
}
