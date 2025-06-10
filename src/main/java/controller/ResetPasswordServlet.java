package controller;

import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = {"/reset"})
public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String token = request.getParameter("token");
        if (token != null && !token.isEmpty()) {
            request.setAttribute("token", token);
            request.getRequestDispatcher("view/login/reset-password.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Invalid reset link");
            request.getRequestDispatcher("view/login/forgot-password.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String token = request.getParameter("token");
        String newPassword = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        UserDAO userDAO = new UserDAO();

        try {
            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("error", "Passwords do not match");
                request.setAttribute("token", token);
                request.getRequestDispatcher("view/login/reset-password.jsp").forward(request, response);
                return;
            }

            if (userDAO.isValidResetToken(token)) {
                userDAO.updatePassword(token, newPassword);
                request.setAttribute("message", "Password reset successfully. Please login with your new password.");
                request.getRequestDispatcher("view/login/login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Invalid or expired token");
                request.getRequestDispatcher("view/login/forgot-password.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred");
            request.setAttribute("token", token);
            request.getRequestDispatcher("view/login/reset-password.jsp").forward(request, response);
        }
    }
} 