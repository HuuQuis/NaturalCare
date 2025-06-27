package controller;

import dal.OrderDetailDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.OrderDetailItem;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderHistoryDetailServlet", urlPatterns = {"/OrderHistoryDetail"})
public class OrderHistoryDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        OrderDetailDAO dao = new OrderDetailDAO();
        List<OrderDetailItem> detailItems = dao.getOrderDetailsByOrderId(orderId);
        request.setAttribute("orderDetails", detailItems);
        request.getRequestDispatcher("/view/user/order-history-detail.jsp").forward(request, response);
    }
}