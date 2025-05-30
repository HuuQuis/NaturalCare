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
        boolean remember = "".equals(request.getParameter("remember-account"));
        String validate = "Username or password is incorrect.";
        UserDAO userDAO = new UserDAO();
        List<User> isUser = userDAO.checkUser(username, password);
        // Check if the user exists in the database
        if (!isUser.isEmpty()) {
            // If the user exists, set the session attribute and redirect
            try {
                // Retrieve user details from the database
                User user = userDAO.getUser(username, password);
                // Set the user in the session
                request.getSession().setAttribute("user", user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // If the "remember me" option is selected, set cookies for username and password
            if (!remember) {
                Cookie cUsername = new Cookie("username", username);
                Cookie cPassword = new Cookie("password", password);
                cUsername.setMaxAge(60 * 60 * 24);
                cPassword.setMaxAge(60 * 60 * 24);
                response.addCookie(cUsername);
                response.addCookie(cPassword);
            }
            if (userDAO.checkAdmin(username, password)) {
                //send user name to homepage
                request.getSession().setAttribute("user", username);
                response.sendRedirect("home");
                return;
            }
            request.getSession().setAttribute("validate", "");
            response.sendRedirect(request.getContextPath() +"/");
        } else {
            request.getSession().setAttribute("validate", validate);
            response.sendRedirect("login");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
