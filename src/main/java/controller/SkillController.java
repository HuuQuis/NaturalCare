package controller;

import dao.SkillDAO;
import model.Skill;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class SkillController extends HttpServlet {
    private SkillDAO skillDAO;

    @Override
    public void init() {
        skillDAO = new SkillDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String keyword = req.getParameter("search");

        if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            skillDAO.deleteSkill(id);
            resp.sendRedirect("skill");
            return;
        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            Skill skill = skillDAO.getSkillById(id);
            req.setAttribute("skill", skill);
        }

        List<Skill> skillList = skillDAO.getAllSkills(keyword);
        req.setAttribute("skills", skillList);
        req.getRequestDispatcher("skill-management.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        String name = req.getParameter("name");

        if (idStr != null && !idStr.isEmpty()) {
            Skill s = new Skill(Integer.parseInt(idStr), name);
            skillDAO.updateSkill(s);
        } else {
            skillDAO.insertSkill(new Skill(0, name));
        }

        resp.sendRedirect("skill");
    }
}
