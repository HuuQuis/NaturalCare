package controller;

import dal.UserDAO;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EmailUtils;
import utils.PropertiesUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.UUID;

@WebServlet(urlPatterns = {"/forgot"})
public class ForgotPasswordServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<h1>Servlet ForgotPasswordServlet at " + request.getContextPath() + "</h1>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("view/login/forgot-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String email = request.getParameter("email");
        UserDAO userDAO = new UserDAO();

        try {
            if (userDAO.checkEmailExists(email)) {
                String token = UUID.randomUUID().toString();
                userDAO.saveResetToken(email, token);
                String baseUrl = PropertiesUtils.get("config", "app.base.url");
                // Gửi email bất đồng bộ
                new Thread(() -> {
                    try {
                        EmailUtils.sendResetEmail(email, token, baseUrl);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
                request.setAttribute("message", "Reset link has been sent to your email");
            } else {
                request.setAttribute("error", "Email not found");
            }
            request.getRequestDispatcher("view/login/forgot-password.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred");
            request.getRequestDispatcher("view/login/forgot-password.jsp").forward(request, response);
        }
    }

}
