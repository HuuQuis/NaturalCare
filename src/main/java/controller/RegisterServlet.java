package controller;

import java.io.*;

import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

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

        // Register the user
        try {
            userDAO.registerUser(username, password, email, firstName, lastName, phone);
            request.getRequestDispatcher("view/login/login.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Registration failed. Please try again.");
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("phone", phone);
            request.getRequestDispatcher("view/login/register.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
