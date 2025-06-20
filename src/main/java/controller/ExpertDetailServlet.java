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

        String action = request.getParameter("action");
        ExpertDAO expertDAO = new ExpertDAO();

        if ("update".equals(action)) {
            int userId = Integer.parseInt(request.getParameter("user_id"));
            int skillId = Integer.parseInt(request.getParameter("skill_id"));

            expertDAO.updateExpertSkill(userId, skillId);

            request.setAttribute("message", "Update expert successfully.");
        } else if ("add".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String firstName = request.getParameter("first_name");
            String lastName = request.getParameter("last_name");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phone_number");
            int skillId = Integer.parseInt(request.getParameter("skill_id"));

            if (!phoneNumber.matches("\\d{10}")) {
                request.setAttribute("error", "Phone number must be exactly 10 digits.");
            } 
            else if (expertDAO.isUsernameTakenForRole(username, 6)) {
                request.setAttribute("error", "Username already exists for an expert.");
            } 
            else {
                expertDAO.addExpert(username, password, firstName, lastName, email, phoneNumber, skillId);
                request.setAttribute("message", "Add new expert successfully.");
            }

            List<Skill> allSkills = expertDAO.getAllSkills();
            request.setAttribute("allSkills", allSkills);

            request.getRequestDispatcher("/view/manage/expert-insert.jsp").forward(request, response);
        }


        doGet(request, response);
    }
}
