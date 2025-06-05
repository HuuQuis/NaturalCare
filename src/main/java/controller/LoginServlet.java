package controller;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.User;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("view/login/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // Basic validation
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.getSession().setAttribute("validate", "Username and password are required.");
            response.sendRedirect("login");
            return;
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.checkUser(username, password);
        
        if (user != null) {
            // Set up session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(10 * 60); // 10 minutes

            // Redirect based on role
            switch (user.getRole()) {
                case 3:
                    response.sendRedirect("admin");
                    break;
                case 4:
                    response.sendRedirect("manager");
                    break;
                default:
                    session.setAttribute("validate", "");
                    response.sendRedirect("home");
                    break;
            }
        } else {
            request.getSession().setAttribute("validate", "Invalid username or password.");
            response.sendRedirect("login");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
