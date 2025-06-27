package controller;

import dal.OrderDAO;
import dal.ProductVariationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Order;
import model.OrderDetail;
import model.ProductVariation;

import java.io.IOException;
import java.util.*;

@WebServlet("/order-success")
public class OrderSuccessServlet extends HttpServlet {
    private final OrderDAO orderDAO = new OrderDAO();
    private final ProductVariationDAO variationDAO = new ProductVariationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderIdStr = request.getParameter("orderId");
        if (orderIdStr == null || !orderIdStr.matches("\\d+")) {
            response.sendRedirect("home");
            return;
        }

        int orderId = Integer.parseInt(orderIdStr);
        Order order = orderDAO.getOrderById(orderId);
        if (order == null) {
            response.sendRedirect("home");
            return;
        }

        List<OrderDetail> orderDetails = orderDAO.getOrderDetails(orderId);
        Map<Integer, ProductVariation> variationMap = new HashMap<>();

        for (OrderDetail detail : orderDetails) {
            ProductVariation variation = variationDAO.getProductVariationById(detail.getVariationId());
            if (variation != null) {
                variationMap.put(detail.getVariationId(), variation);
            }
        }

        request.setAttribute("order", order);
        request.setAttribute("orderDetails", orderDetails);
        request.setAttribute("variationMap", variationMap);
        request.getRequestDispatcher("/view/checkout/order-success.jsp").forward(request, response);
    }
}
