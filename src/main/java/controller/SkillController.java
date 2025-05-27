package controller;

import dal.SkillDAO;
import model.Skill;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/skill")
public class SkillController extends HttpServlet {
    SkillDAO dao = new SkillDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("search");
        String sort = request.getParameter("sort");

        List<Skill> skills = dao.getAllSkills(keyword, sort);
        request.setAttribute("skills", skills);
        request.getRequestDispatcher("skill.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            dao.addSkill(request.getParameter("skillName"));
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("skillId"));
            dao.updateSkill(id, request.getParameter("skillName"));
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("skillId"));
            dao.deleteSkill(id);
        }

        response.sendRedirect("skill");
    }
}
