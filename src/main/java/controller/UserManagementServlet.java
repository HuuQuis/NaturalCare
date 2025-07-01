package controller;

import java.io.*;
import java.util.List;

import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.User;
import utils.PaginationUtils;

import static constant.UtilsConstant.ITEMS_PER_PAGE;
import static constant.UtilsConstant.MAX_VISIBLE_PAGES;

@WebServlet(name = "UserManageServlet", urlPatterns = {"/staff/customerList"})
public class UserManagementServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list":
                showCustomerList(request, response);
                break;
            default:
                showCustomerList(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "search":
                handleSearch(request, response);
                break;
            default:
                showCustomerList(request, response);
                break;
        }
    }

    private void showCustomerList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get search parameter
        String search = request.getParameter("search");
        if (search == null) search = "";

        // Get current page parameter
        String pageStr = request.getParameter("page");
        int currentPage = 1;
        try {
            if (pageStr != null && !pageStr.isEmpty()) {
                currentPage = Integer.parseInt(pageStr);
                if (currentPage < 1) currentPage = 1;
            }
        } catch (NumberFormatException e) {
            currentPage = 1;
        }

        // Calculate offset for database query
        int offset = PaginationUtils.getOffset(currentPage, ITEMS_PER_PAGE);

        // Get customers with pagination and search
        List<User> customers = userDAO.getAllCustomersWithPagination(search, offset, ITEMS_PER_PAGE);

        // Get total count for pagination
        int totalCustomers = userDAO.countCustomers(search);
        int totalPages = PaginationUtils.calculateTotalPages(totalCustomers, ITEMS_PER_PAGE);

        // Get page numbers for navigation
        int[] pageNumbers = PaginationUtils.getPageNumbers(currentPage, totalPages, MAX_VISIBLE_PAGES);

        // Set attributes for JSP
        request.setAttribute("customers", customers);
        request.setAttribute("search", search);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalCustomers", totalCustomers);
        request.setAttribute("pageNumbers", pageNumbers);
        request.setAttribute("itemsPerPage", ITEMS_PER_PAGE);

        // Calculate result range for display
        int startResult = offset + 1;
        int endResult = Math.min(offset + ITEMS_PER_PAGE, totalCustomers);
        request.setAttribute("startResult", startResult);
        request.setAttribute("endResult", endResult);

        request.setAttribute("view", "user-management");
        request.getRequestDispatcher("/view/home/manage.jsp").forward(request, response);
    }

    private void handleSearch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String search = request.getParameter("search");
        if (search == null) search = "";

        // Redirect to GET with search parameter
        String redirectUrl = request.getContextPath() + "/staff/customerList";
        if (!search.isEmpty()) {
            redirectUrl += "?search=" + java.net.URLEncoder.encode(search, "UTF-8");
        }
        response.sendRedirect(redirectUrl);
    }

    @Override
    public String getServletInfo() {
        return "Staff Customer Management Servlet";
    }

}
