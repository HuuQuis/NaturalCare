package controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import dal.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.*;


@WebServlet(name = "ExpertDetailServlet", value = "/expertDetail")
public class ExpertDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userIdStr = request.getParameter("user_id");
        if (userIdStr == null) {
            response.sendRedirect("expertListManage");
            return;
        }

        int userId = Integer.parseInt(userIdStr);

        ExpertDAO expertDAO = new ExpertDAO();

        ExpertSkill expertDetail = expertDAO.getExpertDetailByUserId(userId);
        List<Skill> allSkills = expertDAO.getAllSkills();

        request.setAttribute("expertDetail", expertDetail);
        request.setAttribute("allSkills", allSkills);

        request.getRequestDispatcher("/view/manage/expert-detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ExpertDAO expertDAO = new ExpertDAO();

        // Lấy dữ liệu từ form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone_number");
        String skillIdRaw = request.getParameter("skill_id");

        // Validate
        String error = null;

        if (username == null || username.trim().isEmpty()) {
            error = "Username cannot be empty.";
        } else if (username.length() < 4 || username.length() > 20) {
            error = "Username must be 4–20 characters.";
        } else if (!username.matches("^[a-zA-Z0-9_]+$")) {
            error = "Username can only contain letters, numbers, and underscores.";
        } else if (expertDAO.isUsernameTaken(username)) {
            error = "Username is already taken.";
        }

        if (error == null && (password == null || password.trim().isEmpty())) {
            error = "Password cannot be empty.";
        } else if (error == null && password.length() < 6) {
            error = "Password must be at least 6 characters.";
        }

        if (error == null && (firstName == null || firstName.trim().isEmpty())) {
            error = "First name is required.";
        } else if (error == null && !firstName.matches("^[A-Z][a-zA-Z ]*$")) {
            error = "First name must start with uppercase and contain only letters.";
        }

        if (error == null && (lastName == null || lastName.trim().isEmpty())) {
            error = "Last name is required.";
        } else if (error == null && !lastName.matches("^[A-Z][a-zA-Z ]*$")) {
            error = "Last name must start with uppercase and contain only letters.";
        }

        if (error == null && (email == null || email.trim().isEmpty())) {
            error = "Email is required.";
        } else if (error == null && !email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            error = "Email format is invalid.";
        } else if (error == null && expertDAO.isEmailTaken(email)) {
            error = "Email is already used.";
        }

        if (error == null && (phone == null || phone.trim().isEmpty())) {
            error = "Phone number is required.";
        } else if (error == null && !phone.matches("^\\d{9,11}$")) {
            error = "Phone number must be 9–11 digits.";
        }

        int skillId = 0;
        if (error == null) {
            try {
                skillId = Integer.parseInt(skillIdRaw);
                if (skillId <= 0) {
                    error = "Invalid skill selected.";
                }
            } catch (NumberFormatException e) {
                error = "Skill ID must be a valid number.";
            }
        }

        // Nếu có lỗi, hiển thị lại form với thông báo
        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("username", username);
            request.setAttribute("first_name", firstName);
            request.setAttribute("last_name", lastName);
            request.setAttribute("email", email);
            request.setAttribute("phone_number", phone);
            request.setAttribute("selectedSkillId", skillId);
            request.setAttribute("allSkills", expertDAO.getAllSkills());
            
            request.setAttribute("view", "expert-insert");
            request.getRequestDispatcher("/view/home/manager.jsp").forward(request, response);
            return;
        }

        // Nếu hợp lệ, thêm vào DB
        expertDAO.addExpert(username, password, firstName, lastName, email, phone, skillId);

        // Hiển thị lại trang với thông báo thành công
        request.setAttribute("message", "Expert added successfully!");
        request.setAttribute("allSkills", expertDAO.getAllSkills());
        
        request.setAttribute("view", "expert-insert");
        request.getRequestDispatcher("/view/home/manager.jsp").forward(request, response);
    }
}
