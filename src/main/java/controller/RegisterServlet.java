package controller;

import java.io.*;
import java.security.SecureRandom;

import constant.UtilsConstant;
import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import utils.UserAuthUtils;

import static utils.UserAuthUtils.generateOtp;
import static utils.UserAuthUtils.isValidOtp;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<h1>Servlet RegisterServlet at " + request.getContextPath() + "</h1>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("view/login/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        String otpParam = request.getParameter("otp");

        if (otpParam != null) {
            // Verify OTP
            verifyOTP(request, response, session, otpParam);
        } else {
            // Register logic
            handleRegister(request, response, session);
        }
    }

    private void verifyOTP(HttpServletRequest request, HttpServletResponse response, HttpSession session, String otpParam) throws ServletException, IOException {
        // get otp
        String sessionOtp = (String) session.getAttribute("otp");
        Long otpTime = (Long) session.getAttribute("otpTime");
        Integer otpAttempts = (Integer) session.getAttribute("otpAttempts");

        if (otpAttempts == null) {
            otpAttempts = 0; // Initialize attempts if not set
        }

        // check otp attempts
        if (otpAttempts >= UtilsConstant.MAX_OTP_ATTEMPTS) {
            request.setAttribute("error", "Maximum OTP attempts exceeded! Please try again later.");
            session.invalidate();
            request.getRequestDispatcher("view/login/register.jsp").forward(request, response);
            return;
        }

        // expired otp
        if (!isValidOtp(sessionOtp, otpTime)) {
            request.setAttribute("error", "Expired OTP! Please try again!");
            session.invalidate();
            request.getRequestDispatcher("view/login/register.jsp").forward(request, response);
            return;
        }

        // invalid otp
        if (!otpParam.equals(sessionOtp)) {
            session.setAttribute("otpAttempts", otpAttempts + 1);
            request.setAttribute("error", "Invalid OTP! Please try again!");
            request.getRequestDispatcher("view/login/otp.jsp").forward(request, response);
            return;
        }

        // valid OTP -> register user
        String username = (String) session.getAttribute("reg_username");
        String password = (String) session.getAttribute("reg_password");
        String email = (String) session.getAttribute("reg_email");
        String firstName = (String) session.getAttribute("reg_firstName");
        String lastName = (String) session.getAttribute("reg_lastName");
        String phone = (String) session.getAttribute("reg_phone");
        try {
            userDAO.registerUser(username, password, email, firstName, lastName, phone);
            session.invalidate();
            request.getRequestDispatcher("view/login/login.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to register user! Please try again!");
            request.getRequestDispatcher("view/login/register.jsp").forward(request, response);
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phone = request.getParameter("phone");

        // existed user
        if (userDAO.checkUsernameExists(username)) {
            request.setAttribute("error", "This username already exists!");
            request.setAttribute("email", email);
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("phone", phone);
            request.getRequestDispatcher("view/login/register.jsp").forward(request, response);
            return;
        }

        // existed email
        if (userDAO.checkEmail(email)) {
            request.setAttribute("error", "This email already exists!");
            request.setAttribute("email", email);
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("phone", phone);
            request.getRequestDispatcher("view/login/register.jsp").forward(request, response);
            return;
        }

        // create OTP
        String otp = generateOtp();
        try {
            // async send OTP email
            new Thread(() -> {
                try {
                    UserAuthUtils.sendOTPEmail(email, otp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to send OTP email! Please try again!");
            request.getRequestDispatcher("view/login/register.jsp").forward(request, response);
            return;
        }

        // save user to session
        session.setAttribute("reg_username", username);
        session.setAttribute("reg_password", password);
        session.setAttribute("reg_email", email);
        session.setAttribute("reg_firstName", firstName);
        session.setAttribute("reg_lastName", lastName);
        session.setAttribute("reg_phone", phone);
        session.setAttribute("otp", otp);
        session.setAttribute("otpTime", System.currentTimeMillis());
        request.getRequestDispatcher("view/login/otp.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
