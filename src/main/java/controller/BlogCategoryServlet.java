package controller;

import dal.BlogCategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.BlogCategory;

import java.io.IOException;
import java.util.List;

@WebServlet("/blog-category")
public class BlogCategoryServlet extends HttpServlet {
    private final BlogCategoryDAO dao = new BlogCategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        int id = parseIntOrDefault(req.getParameter("id"), -1);
        int page = parseIntOrDefault(req.getParameter("page"), 1);
        int pageSize = 10;

        if ("edit".equals(action) && id != -1) {
            BlogCategory cat = dao.getCategoryById(id);
            req.setAttribute("editCategory", cat);
        }

        List<BlogCategory> list = dao.getCategoriesByPage(page, pageSize);
        int totalPage = (int) Math.ceil((double) dao.getTotalCategoryCount() / pageSize);
        int startIndex = (page - 1) * pageSize;

        req.setAttribute("list", list);
        req.setAttribute("page", page);
        req.setAttribute("totalPage", totalPage);
        req.setAttribute("startIndex", startIndex);
        req.setAttribute("view", "blog-category");
        req.getRequestDispatcher("/view/home/manager.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        String name = req.getParameter("name") != null ? req.getParameter("name").trim() : null;
        int id = parseIntOrDefault(req.getParameter("id"), -1);
        int currentPage = parseIntOrDefault(req.getParameter("page"), 1);

        switch (action) {
            case "add":
                if (dao.isCategoryNameExists(name)) {
                    req.getSession().setAttribute("message", "Blog category name already exists.");
                    req.getSession().setAttribute("messageType", "danger");
                } else {
                    dao.addCategory(name);
                    req.getSession().setAttribute("message", "Blog category added.");
                    req.getSession().setAttribute("messageType", "success");
                }
                resp.sendRedirect("blog-category?page=1");
                return;
            case "update":
                if (dao.isCategoryNameExistsForOtherId(name, id)) {
                    req.getSession().setAttribute("message", "Duplicate blog category name.");
                    req.getSession().setAttribute("messageType", "danger");
                } else {
                    dao.updateCategory(id, name);
                    req.getSession().setAttribute("message", "Blog category updated.");
                    req.getSession().setAttribute("messageType", "success");
                    req.getSession().setAttribute("updatedCategoryId", id);
                }
                resp.sendRedirect("blog-category?page=" + currentPage);
                return;
            case "delete":
                if (dao.hasBlogDependency(id)) {
                    req.getSession().setAttribute("message", "Cannot delete: This category is used by existing blog(s).");
                    req.getSession().setAttribute("messageType", "danger");
                } else {
                    dao.deleteCategory(id);
                    req.getSession().setAttribute("message", "Blog category deleted successfully.");
                    req.getSession().setAttribute("messageType", "success");
                }
                resp.sendRedirect("blog-category?page=" + currentPage);
                return;
        }
        resp.sendRedirect("blog-category");
    }

    private int parseIntOrDefault(String raw, int defaultVal) {
        try {
            return Integer.parseInt(raw);
        } catch (Exception e) {
            return defaultVal;
        }
    }
}
