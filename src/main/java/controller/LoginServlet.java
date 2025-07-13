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

        UserDAO userDAO = new UserDAO();
        User user = userDAO.checkUser(username, password);

        if (user != null) {
            if (request.getSession(false) != null) {
                request.getSession(false).invalidate();
            }
            HttpSession session = request.getSession(true);

            session.setAttribute("user", user);
            session.setMaxInactiveInterval(10 * 60);

            // Redirect based on role
            switch (user.getRole()) {
                case 2: // Staff
                    response.sendRedirect("staffHome");
                    break;
                case 3: // Admin
                    response.sendRedirect("view/home/manage.jsp");
                    break;
                case 4: // Manager
                    request.getRequestDispatcher("view/home/manage.jsp").forward(request, response);
                    break;
                case 5: // Marketer
                    request.getRequestDispatcher("view/home/marketer.jsp").forward(request, response);
                    break;
                default:
                    request.setAttribute("error", "");
                    response.sendRedirect("home");
                    break;
            }
        } else {
            request.setAttribute("error", "Invalid username or password.");
            request.setAttribute("username", username);
            request.getRequestDispatcher("view/login/login.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
