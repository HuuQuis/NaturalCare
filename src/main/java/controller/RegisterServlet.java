package controller;

import java.io.*;

import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import utils.EmailUtils;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

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

        //Validate exist OTP
        if (otpParam != null) {
            String sessionOtp = (String) session.getAttribute("otp");
            //OTP expired check
            long otpTime = session.getAttribute("otpTime") != null ? (long) session.getAttribute("otpTime") : 0;
            long now = System.currentTimeMillis();

            //OTP null or after 5 mins
            if (sessionOtp == null || now - otpTime > 5 * 60 * 1000) {
                request.setAttribute("error", "OTP expired. Please register again.");
                session.invalidate();
                request.getRequestDispatcher("view/login/register.jsp").forward(request, response);
                return;
            }

            //Invalid OTP
            if (!otpParam.equals(sessionOtp)) {
                request.setAttribute("error", "Invalid OTP. Please try again.");
                request.getRequestDispatcher("view/login/otp.jsp").forward(request, response);
                return;
            }

            // OTP valid -> get user details from session
            String username = (String) session.getAttribute("reg_username");
            String password = (String) session.getAttribute("reg_password");
            String email = (String) session.getAttribute("reg_email");
            String firstName = (String) session.getAttribute("reg_firstName");
            String lastName = (String) session.getAttribute("reg_lastName");
            String phone = (String) session.getAttribute("reg_phone");

            // Back to register logic
            UserDAO userDAO = new UserDAO();
            try {
                userDAO.registerUser(username, password, email, firstName, lastName, phone);
                session.invalidate();
                request.getRequestDispatcher("view/login/login.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Registration failed. Please try again.");
                request.getRequestDispatcher("view/login/register.jsp").forward(request, response);
            }
            return;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phone = request.getParameter("phone");

        UserDAO userDAO = new UserDAO();

        // Check if username already exists
        if (userDAO.checkUsernameExists(username)) {
            request.setAttribute("error", "Username already exists.");
            request.setAttribute("email", email);
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("phone", phone);
            request.getRequestDispatcher("view/login/register.jsp").forward(request, response);
            return;
        }

        // Check if email already exists
        if (userDAO.checkEmail(email)) {
            request.setAttribute("error", "Email is already registered.");
            request.setAttribute("username", username);
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("phone", phone);
            request.getRequestDispatcher("view/login/register.jsp").forward(request, response);
            return;
        }

        String otp = String.valueOf((int) (Math.random() * 900000) + 100000); // 6-digit OTP
        try {
            EmailUtils.sendOTPEmail(email, otp);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to send OTP. Please try again.");
            request.getRequestDispatcher("view/login/register.jsp").forward(request, response);
            return;
        }

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
