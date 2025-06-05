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

            request.setAttribute("message", "Cập nhật kỹ năng thành công.");
        } else if ("add".equals(action)) {
            // Thêm expert mới, lấy dữ liệu từ form
            String userName = request.getParameter("user_name");
            int skillId = Integer.parseInt(request.getParameter("skill_id"));

            expertDAO.addExpert(userName, skillId);
            request.setAttribute("message", "Thêm chuyên gia mới thành công.");
        }

        doGet(request, response);
    }
}
