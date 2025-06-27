package controller;

import dal.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderManagementServlet", urlPatterns = {"/orderManagement"})
public class OrderManagementServlet extends HttpServlet {
    private static final int PAGE_SIZE = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String search = request.getParameter("search");
        String status = request.getParameter("status");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String pageStr = request.getParameter("page");
        int page = pageStr != null ? Integer.parseInt(pageStr) : 1;

        ProductOrderDAO orderDAO = new ProductOrderDAO();

        int totalOrders = orderDAO.countOrders(search, status, fromDate, toDate);
        int totalPages = (int) Math.ceil((double) totalOrders / PAGE_SIZE);

        List<ProductOrder> orders = orderDAO.getOrdersWithPagination(search, status, fromDate, toDate, page, PAGE_SIZE);
        
        UserDAO userDAO = new UserDAO();
        CouponDAO couponDAO = new CouponDAO();
        AddressDAO addressDAO = new AddressDAO();

        for (ProductOrder order : orders) {
            order.setCustomerName(userDAO.getUserNameById(order.getUserId()));

            if (order.getShipperId() != null && order.getShipperId() != 0)
                order.setShipperName(userDAO.getUserNameById(order.getShipperId()));

            if (order.getCouponId() != null && order.getCouponId() != 0)
                order.setCouponCode(couponDAO.getCouponCodeById(order.getCouponId()));

            if (order.getAddressId() != 0)
                order.setAddressDisplay(addressDAO.getAddressDisplayById(order.getAddressId()));
        }

        request.setAttribute("orders", orders);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("search", search);
        request.setAttribute("status", status);
        request.setAttribute("fromDate", fromDate);
        request.setAttribute("toDate", toDate);

        request.getRequestDispatcher("/view/staff/order-management.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            int orderId = Integer.parseInt(request.getParameter("orderId"));

            ProductOrderDAO orderDAO = new ProductOrderDAO();
            ProductOrder order = orderDAO.getOrderById(orderId);

            if (order != null && (order.getStatusId() == 1 || order.getStatusId() == 2)) {
                orderDAO.deleteOrder(orderId);
                request.setAttribute("message", "Deleted successfully");
            } else {
                request.setAttribute("error", "Cannot delete this order");
            }
        }
        doGet(request, response);
    }
}
