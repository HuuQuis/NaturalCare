package controller;

import dal.SkillDAO;
import model.Skill;
import jakarta.servlet.ServletException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SkillController", urlPatterns = {"/skill"})
public class SkillController extends HttpServlet {
    SkillDAO dao = new SkillDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("search");
        String sort = request.getParameter("sort");

        List<Skill> skills = dao.getAllSkills(keyword);
        request.setAttribute("skills", skills);
        request.getRequestDispatcher("skill.jsp").forward(request, response);


        response.sendRedirect("skill");
    }
}
