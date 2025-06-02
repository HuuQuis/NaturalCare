package controller;

import dal.SkillDAO;
import model.Skill;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SkillServlet", value = "/skill")
public class SkillServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SkillDAO dao = new SkillDAO();

        String action = req.getParameter("action");
        try {
            if ("form".equals(action)) {
                int id = req.getParameter("id") != null ? Integer.parseInt(req.getParameter("id")) : 0;
                Skill skill = id > 0 ? dao.getById(id) : new Skill();
                req.setAttribute("skill", skill);
                req.getRequestDispatcher("view/skill/form.jsp").forward(req, resp);
                return;
            }

            if ("delete".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                dao.delete(id);
                resp.sendRedirect("skill");
                return;
            }

            String search = req.getParameter("search") != null ? req.getParameter("search") : "";
            String sort = req.getParameter("sort") != null ? req.getParameter("sort") : "asc";
            int page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page")) : 1;
            int pageSize = 5;

            int total = dao.getTotal(search);
            List<Skill> list = dao.getAll(search, sort, (page - 1) * pageSize, pageSize);

            req.setAttribute("list", list);
            req.setAttribute("search", search);
            req.setAttribute("sort", sort);
            req.setAttribute("page", page);
            req.setAttribute("totalPages", (int) Math.ceil((double) total / pageSize));
            req.getRequestDispatcher("view/skill/list.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        SkillDAO dao = new SkillDAO();

        try {
            String idText = req.getParameter("skill_id");
            String name = req.getParameter("skill_name").trim();

            if (name.isBlank()) {
                req.setAttribute("error", "Skill name is required.");
                req.setAttribute("skill", new Skill(idText != null && !idText.isBlank() ? Integer.parseInt(idText) : 0, name));
                req.getRequestDispatcher("view/skill/form.jsp").forward(req, resp);
                return;
            }

            int id = idText != null && !idText.isBlank() ? Integer.parseInt(idText) : 0;
            Skill skill = new Skill(id, name);

            if (id > 0 && dao.getById(id) != null) {
                dao.update(skill);
            } else {
                dao.insert(skill);
            }

            resp.sendRedirect("skill");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500);
        }
    }
}
