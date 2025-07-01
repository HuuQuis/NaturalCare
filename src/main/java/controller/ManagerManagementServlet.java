package controller;

import dal.UserDAO;
import model.User;
import utils.PaginationUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static constant.UtilsConstant.ITEMS_PER_PAGE;
import static constant.UtilsConstant.MAX_VISIBLE_PAGES;

@WebServlet(name = "AdminManagerServlet", urlPatterns = {"/admin/managerManage"})
public class ManagerManagementServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list":
                showManagerList(request, response);
                break;
            case "add":
                showAddForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteManager(request, response);
                break;
            default:
                showManagerList(request, response);
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
            case "create":
                createManager(request, response);
                break;
            case "update":
                updateManager(request, response);
                break;
            default:
                showManagerList(request, response);
                break;
        }
    }

    private void showManagerList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        // Get managers with pagination and search
        List<User> managers = userDAO.getAllManagersWithPagination(search, offset, ITEMS_PER_PAGE);

        // Get total count for pagination
        int totalManagers = userDAO.countManagers(search);
        int totalPages = PaginationUtils.calculateTotalPages(totalManagers, ITEMS_PER_PAGE);

        // Get page numbers for navigation
        int[] pageNumbers = PaginationUtils.getPageNumbers(currentPage, totalPages, MAX_VISIBLE_PAGES);

        // Set attributes for JSP
        request.setAttribute("managers", managers);
        request.setAttribute("search", search);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalManagers", totalManagers);
        request.setAttribute("pageNumbers", pageNumbers);
        request.setAttribute("itemsPerPage", ITEMS_PER_PAGE);

        // Calculate result range for display
        int startResult = offset + 1;
        int endResult = Math.min(offset + ITEMS_PER_PAGE, totalManagers);
        request.setAttribute("startResult", startResult);
        request.setAttribute("endResult", endResult);

        request.setAttribute("view", "admin-manager-management");
        request.getRequestDispatcher("/view/home/manage.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("formAction", "add");
        request.setAttribute("view", "admin-manager-management");
        request.getRequestDispatcher("/view/home/manage.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("id"));
            User user = userDAO.getUserById(userId);

            if (user != null && user.getRole() == 4) { // Only allow editing managers
                request.setAttribute("editUser", user);
                request.setAttribute("formAction", "edit");
                request.setAttribute("view", "admin-manager-management");
                request.getRequestDispatcher("/view/home/manage.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/managerManage?error=Manager not found");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/managerManage?error=Invalid manager ID");
        }
    }

    private void handleSearch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String search = request.getParameter("search");
        if (search == null) search = "";

        // Redirect to GET with search parameter
        String redirectUrl = request.getContextPath() + "/admin/managerManage";
        if (!search.isEmpty()) {
            redirectUrl += "?search=" + java.net.URLEncoder.encode(search, "UTF-8");
        }
        response.sendRedirect(redirectUrl);
    }

    private void createManager(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phone = request.getParameter("phone");

        // Validate input
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                firstName == null || firstName.trim().isEmpty() ||
                lastName == null || lastName.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/managerManage?action=add&error=All required fields must be filled");
            return;
        }

        // Check if username or email already exists
        if (userDAO.checkUsernameExists(username)) {
            response.sendRedirect(request.getContextPath() + "/admin/managerManage?action=add&error=Username already exists");
            return;
        }

        if (userDAO.checkEmailExists(email)) {
            response.sendRedirect(request.getContextPath() + "/admin/managerManage?action=add&error=Email already exists");
            return;
        }

        // Create manager
        boolean success = userDAO.createManager(username, password, email, firstName, lastName, phone);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/admin/managerManage?success=Manager created successfully");
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/managerManage?action=add&error=Failed to create manager");
        }
    }

    private void updateManager(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String phone = request.getParameter("phone");

            // Validate input
            if (username == null || username.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    firstName == null || firstName.trim().isEmpty() ||
                    lastName == null || lastName.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/admin/managerManage?action=edit&id=" + userId + "&error=All required fields must be filled");
                return;
            }

            // Check if username or email already exists (excluding current user)
            if (userDAO.checkUsernameExists(username)) {
                response.sendRedirect(request.getContextPath() + "/admin/managerManage?action=edit&id=" + userId + "&error=Username already exists");
                return;
            }

            if (userDAO.checkEmailExists(email)) {
                response.sendRedirect(request.getContextPath() + "/admin/managerManage?action=edit&id=" + userId + "&error=Email already exists");
                return;
            }

            // Update manager
            boolean success = userDAO.updateManager(userId, username, email, firstName, lastName, phone);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/admin/managerManage?success=Manager updated successfully");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/managerManage?action=edit&id=" + userId + "&error=Failed to update manager");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/managerManage?error=Invalid manager ID");
        }
    }

    private void deleteManager(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("id"));
            boolean success = userDAO.deleteManager(userId);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/admin/managerManage?success=Manager deleted successfully");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/managerManage?error=Failed to delete manager or manager not found");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/managerManage?error=Invalid manager ID");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}