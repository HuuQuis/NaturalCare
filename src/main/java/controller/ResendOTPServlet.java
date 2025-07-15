package controller;

import constant.UtilsConstant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.UserAuthUtils;

import java.io.IOException;
import java.security.SecureRandom;

import static utils.UserAuthUtils.generateOtp;

@WebServlet(name = "ResendOTPServlet", urlPatterns = {"/resend-otp"})
public class ResendOTPServlet extends HttpServlet {
    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        
        try {
            // check if registered
            String email = (String) session.getAttribute("reg_email");
            if (email == null) {
                request.setAttribute("error", "Please register first!");
                request.getRequestDispatcher("view/login/register.jsp").forward(request, response);
                return;
            }
            
            // check cooldown time
            Long lastResendTime = (Long) session.getAttribute("lastResendTime");
            long currentTime = System.currentTimeMillis();
            
            if (lastResendTime != null && (currentTime - lastResendTime) < UtilsConstant.RESEND_COOLDOWN) {
                long remainingTime = (UtilsConstant.RESEND_COOLDOWN - (currentTime - lastResendTime)) / 1000;
                request.setAttribute("error", "Please wait " + remainingTime + " seconds before resending OTP.");
                request.getRequestDispatcher("view/login/otp.jsp").forward(request, response);
                return;
            }
            
            // gen new otp
            String newOtp = generateOtp();
            
            // async send email
            new Thread(() -> {
                try {
                    UserAuthUtils.sendOTPEmail(email, newOtp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            
            // update session attributes
            session.setAttribute("otp", newOtp);
            session.setAttribute("otpTime", currentTime);
            session.setAttribute("lastResendTime", currentTime);
            session.setAttribute("otpAttempts", 0); // Reset attempts
            
            request.setAttribute("message", "OTP has been resent to your email. Please check your inbox.");
            request.getRequestDispatcher("view/login/otp.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while resending OTP. Please try again later.");
            request.getRequestDispatcher("view/login/otp.jsp").forward(request, response);
        }
    }
} 