/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.*;
import dal.*;
import java.util.ArrayList;

/**
 *
 * @author macbook
 */
@WebServlet(name="OrderDetailServlet", urlPatterns={"/OrderDetail"})
public class OrderDetailServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet OrderDetailServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderDetailServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        ProductOrderDAO productOrderDAO = new ProductOrderDAO();
        UserDAO userDAO = new UserDAO();
        CouponDAO couponDAO = new CouponDAO();
        
        ProductOrder order = productOrderDAO.getOrderById(orderId);
        List<User> shippers = userDAO.getAllShippers();
        AddressDAO addressDAO = new AddressDAO();
        List<Address> addresses = addressDAO.getAllAddresses();
        List<Coupon> coupons = couponDAO.getAllCoupons();
        String userName = userDAO.getUserNameById(order.getUserId());
        request.setAttribute("userName", userName);
        OrderStatusDAO statusDAO = new OrderStatusDAO();
        List<OrderStatus> statusList = statusDAO.getAllStatuses();
        
        request.setAttribute("statusList", statusList);
        request.setAttribute("order", order);
        request.setAttribute("shippers", shippers);
        request.setAttribute("addresses", addresses);
        request.setAttribute("coupons", coupons);

        request.getRequestDispatcher("/view/staff/order-detail.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String note = request.getParameter("note");
        int statusId = Integer.parseInt(request.getParameter("statusId"));

        String shipperStr = request.getParameter("shipperId");
        Integer shipperId = (shipperStr != null && !shipperStr.isEmpty()) ? Integer.parseInt(shipperStr) : null;

        int addressId = Integer.parseInt(request.getParameter("addressId"));

        String couponStr = request.getParameter("couponId");
        Integer couponId = (couponStr != null && !couponStr.isEmpty()) ? Integer.parseInt(couponStr) : null;

        ProductOrderDAO productOrderDAO = new ProductOrderDAO();
        
        productOrderDAO.updateOrder(orderId, note, statusId, shipperId, addressId, couponId);

        response.sendRedirect("OrderDetail?orderId=" + orderId);
    } 

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
