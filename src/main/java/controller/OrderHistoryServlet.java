/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.AddressDAO;
import dal.CouponDAO;
import dal.ProductOrderDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.ProductOrder;

@WebServlet(name="OrderHistoryServlet", urlPatterns={"/OrderHistory"})
public class OrderHistoryServlet extends HttpServlet {
   
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
            out.println("<title>Servlet OrderHistoryServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderHistoryServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    private static final int PAGE_SIZE = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("userId"));

        // Lấy tham số lọc/tìm kiếm
        String search = request.getParameter("search");
        String status = request.getParameter("status");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String pageStr = request.getParameter("page");
        int page = (pageStr != null) ? Integer.parseInt(pageStr) : 1;

        ProductOrderDAO orderDAO = new ProductOrderDAO();

        // Đếm tổng đơn để phân trang
        int totalOrders = orderDAO.countOrdersByUser(userId, search, status, fromDate, toDate);
        int totalPages = (int) Math.ceil((double) totalOrders / PAGE_SIZE);

        // Lấy danh sách đơn có phân trang
        List<ProductOrder> orders = orderDAO.getOrdersByUserWithPagination(
                userId, search, status, fromDate, toDate, page, PAGE_SIZE);

        // Bổ sung thông tin hiển thị rõ ràng
        UserDAO userDAO = new UserDAO();
        CouponDAO couponDAO = new CouponDAO();
        AddressDAO addressDAO = new AddressDAO();

        for (ProductOrder order : orders) {
            if (order.getShipperId() != null && order.getShipperId() != 0) {
                order.setShipperName(userDAO.getUserNameById(order.getShipperId()));
            }

            if (order.getCouponId() != null && order.getCouponId() != 0) {
                order.setCouponCode(couponDAO.getCouponCodeById(order.getCouponId()));
            }

            if (order.getAddressId() != 0) {
                order.setAddressDisplay(addressDAO.getAddressDisplayById(order.getAddressId()));
            }
        }

        // Truyền dữ liệu sang JSP
        request.setAttribute("orders", orders);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("search", search);
        request.setAttribute("status", status);
        request.setAttribute("fromDate", fromDate);
        request.setAttribute("toDate", toDate);        
        request.setAttribute("userId", userId);

        request.getRequestDispatcher("/view/user/order-history.jsp").forward(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
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
