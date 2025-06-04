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
        boolean remember = "on".equals(request.getParameter("remember-account"));
        String validate = "Username or password is incorrect.";
        UserDAO userDAO = new UserDAO();

        User user = (User) userDAO.checkUser(username, password);
        if (user != null) {
            // Thiết lập session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(30 * 60); // 30 phút

            // Xử lý "remember me"
            if (remember) {
                //implement remember me functionality
            }
            // Chuyển hướng theo vai trò
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
            request.getSession().setAttribute("validate", "Username or password is incorrect.");
            response.sendRedirect("login");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
