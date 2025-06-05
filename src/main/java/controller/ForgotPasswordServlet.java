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
        String action = request.getParameter("action");
        if ("reset".equals(action)) {
            request.setAttribute("token", request.getParameter("token"));
            request.getRequestDispatcher("view/login/reset-password.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("view/login/forgot-password.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String action = request.getParameter("action");
        UserDAO userDAO = new UserDAO();

        if ("send".equals(action)) {
            String email = request.getParameter("email");

            try {
                if (userDAO.checkEmailExists(email)) {
                    String token = UUID.randomUUID().toString();
                    userDAO.saveResetToken(email, token);
                    sendResetEmail(email, token);
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
        } else if ("reset".equals(action)) {
            String token = request.getParameter("token");
            String newPassword = request.getParameter("password");

            try {
                if (userDAO.isValidResetToken(token)) {
                    userDAO.updatePassword(token, newPassword);
                    request.setAttribute("message", "Password reset successfully");
                    request.getRequestDispatcher("view/login/login.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "Invalid or expired token");
                    request.getRequestDispatcher("view/login/forgot-password.jsp").forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "An error occurred");
                request.getRequestDispatcher("view/login/forgot-password.jsp").forward(request, response);
            }
        }
    }

    private void sendResetEmail(String email, String token) throws MessagingException {
        String senderEmail = PropertiesUtils.get("mail.username");
        String senderPassword = PropertiesUtils.get("mail.password");
        String baseUrl = PropertiesUtils.get("app.base.url");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject("Password Reset Request");

        String resetLink = baseUrl + "/forgot?action=reset&token=" + token;
        message.setText("Click this link to reset your password: " + resetLink);

        Transport.send(message);
    }

}
