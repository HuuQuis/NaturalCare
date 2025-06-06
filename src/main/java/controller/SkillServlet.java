package controller;

import dal.SkillDAO;
import model.Skill;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "SkillServlet", value = "/skill")
public class SkillServlet extends HttpServlet {

    private void checkExpertAccess(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("login");
            return;
        }
        model.User user = (model.User) session.getAttribute("user");
        if (user.getRole() != 6) {
            req.getSession().setAttribute("error", "Access denied. Expert role required.");
            resp.sendRedirect("home");
        }
    }

    private String validateSkillName(String skillName, int skillId, SkillDAO dao) {
        if (skillName == null || skillName.trim().isEmpty()) {
            return "Skill name is required.";
        }
        skillName = skillName.trim();
        if (skillName.length() < 3) {
            return "Skill name must be at least 3 characters long.";
        }
        if (skillName.length() > 50) {
            return "Skill name must not exceed 50 characters.";
        }
        if (!skillName.matches("^[a-zA-Z\\s]+$")) {
            return "Skill name can only contain letters and spaces.";
        }
        if (dao.isDuplicateSkillName(skillName, skillId)) {
            return "Skill name already exists.";
        }
        return null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        checkExpertAccess(req, resp);
        if (resp.isCommitted()) return;

        SkillDAO dao = new SkillDAO();
        String action = req.getParameter("action");

        try {
            if ("check".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                boolean isInUse = dao.isSkillInUse(id);
                resp.setContentType("text/plain");
                resp.getWriter().write(isInUse ? "inUse" : "ok");
                return;
            }

            if ("form".equals(action)) {
                int id = req.getParameter("id") != null && !req.getParameter("id").isEmpty() ? Integer.parseInt(req.getParameter("id")) : 0;
                if (id > 0 && dao.getById(id) == null) {
                    req.setAttribute("error", "Skill not found.");
                    req.getRequestDispatcher("/view/error.jsp").forward(req, resp);
                    return;
                }
                Skill skill = id > 0 ? dao.getById(id) : new Skill();
                req.setAttribute("skill", skill);
                req.getRequestDispatcher("/view/skill/form.jsp").forward(req, resp);
                return;
            }

            if ("delete".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                if (dao.getById(id) == null) {
                    req.setAttribute("error", "Skill not found.");
                    req.getRequestDispatcher("/view/error.jsp").forward(req, resp);
                    return;
                }
                if (dao.isSkillInUse(id)) {
                    req.setAttribute("error", "Cannot delete skill because it is in use.");
                    String search = req.getParameter("search") != null ? req.getParameter("search") : "";
                    String sort = req.getParameter("sort") != null ? req.getParameter("sort") : "asc";
                    int page = req.getParameter("page") != null && !req.getParameter("page").isEmpty() ? Integer.parseInt(req.getParameter("page")) : 1;
                    if (page < 1) page = 1;
                    int pageSize = 5;

                    int total = dao.getTotal(search);
                    List<Skill> list = dao.getAll(search, sort, (page - 1) * pageSize, pageSize);

                    req.setAttribute("list", list);
                    req.setAttribute("search", search);
                    req.setAttribute("sort", sort);
                    req.setAttribute("page", page);
                    req.setAttribute("totalPages", (int) Math.ceil((double) total / pageSize));
                    req.getRequestDispatcher("/view/skill/list.jsp").forward(req, resp);
                    return;
                }
                dao.delete(id);
                resp.sendRedirect("skill");
                return;
            }

            String search = req.getParameter("search") != null ? req.getParameter("search").trim() : "";
            if (search.length() > 50) {
                req.setAttribute("error", "Search keyword must not exceed 50 characters.");
                req.getRequestDispatcher("/view/skill/list.jsp").forward(req, resp);
                return;
            }
            if (!search.matches("^[a-zA-Z\\s]*$") && !search.isEmpty()) {
                req.setAttribute("error", "Search keyword can only contain letters and spaces.");
                req.getRequestDispatcher("/view/skill/list.jsp").forward(req, resp);
                return;
            }
            String sort = req.getParameter("sort") != null && req.getParameter("sort").equalsIgnoreCase("desc") ? "desc" : "asc";
            int page = req.getParameter("page") != null && !req.getParameter("page").isEmpty() ? Integer.parseInt(req.getParameter("page")) : 1;
            if (page < 1) page = 1;
            int pageSize = 5;

            int total = dao.getTotal(search);
            List<Skill> list = dao.getAll(search, sort, (page - 1) * pageSize, pageSize);

            req.setAttribute("list", list);
            req.setAttribute("search", search);
            req.setAttribute("sort", sort);
            req.setAttribute("page", page);
            req.setAttribute("totalPages", (int) Math.ceil((double) total / pageSize));
            req.getRequestDispatcher("/view/skill/list.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Invalid ID or page number format.");
            req.getRequestDispatcher("/view/error.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "Error: " + e.getMessage());
            req.getRequestDispatcher("/view/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        checkExpertAccess(req, resp);
        if (resp.isCommitted()) return;

        SkillDAO dao = new SkillDAO();
        String action = req.getParameter("action");

        try {
            if ("checkDuplicate".equals(action)) {
                String skillName = req.getParameter("skill_name");
                int skillId = req.getParameter("skill_id") != null && !req.getParameter("skill_id").isEmpty() ? Integer.parseInt(req.getParameter("skill_id")) : 0;
                boolean isDuplicate = dao.isDuplicateSkillName(skillName, skillId);
                resp.setContentType("text/plain");
                resp.getWriter().write(isDuplicate ? "duplicate" : "ok");
                return;
            }

            int skillId = req.getParameter("skill_id") != null && !req.getParameter("skill_id").isEmpty() ? Integer.parseInt(req.getParameter("skill_id")) : 0;
            String skillName = req.getParameter("skill_name");

            String validationError = validateSkillName(skillName, skillId, dao);
            if (validationError != null) {
                Skill skill = new Skill();
                skill.setSkillId(skillId);
                skill.setSkillName(skillName != null ? skillName : "");
                req.setAttribute("skill", skill);
                req.setAttribute("error", validationError);
                req.getRequestDispatcher("/view/skill/form.jsp").forward(req, resp);
                return;
            }

            skillName = skillName.trim();
            Skill skill = new Skill();
            skill.setSkillId(skillId);
            skill.setSkillName(skillName);

            if (skillId > 0) {
                if (dao.getById(skillId) == null) {
                    req.setAttribute("error", "Skill not found.");
                    req.getRequestDispatcher("/view/error.jsp").forward(req, resp);
                    return;
                }
                dao.update(skill);
            } else {
                dao.insert(skill);
            }
            resp.sendRedirect("skill");
        } catch (SQLException e) {
            req.setAttribute("error", "Database error: " + e.getMessage());
            req.getRequestDispatcher("/view/error.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "Error: " + e.getMessage());
            req.getRequestDispatcher("/view/error.jsp").forward(req, resp);
        }
    }
}