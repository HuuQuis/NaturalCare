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
        String keyword = req.getParameter("keyword");
        String statusFilter = req.getParameter("status");
        String sort = req.getParameter("sort");

        if ("edit".equals(action) && id != -1) {
            BlogCategory cat = dao.getCategoryById(id);
            req.setAttribute("editCategory", cat);
        }

        List<BlogCategory> list = dao.getCategoriesByPage(page, pageSize, keyword, sort, statusFilter);
        int totalCount = dao.countTotalFilteredBlogCategories(keyword, statusFilter);
        int totalPage = (int) Math.ceil((double) totalCount / pageSize);
        int startIndex = (page - 1) * pageSize;

        req.setAttribute("list", list);
        req.setAttribute("page", page);
        req.setAttribute("totalPage", totalPage);
        req.setAttribute("startIndex", startIndex);
        req.setAttribute("keyword", keyword);
        req.setAttribute("statusFilter", statusFilter);
        req.setAttribute("sort", sort);
        req.setAttribute("view", "blog-category");

        req.getRequestDispatcher("/view/home/manager.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        String name = req.getParameter("name") != null ? req.getParameter("name").trim() : null;
        int id = parseIntOrDefault(req.getParameter("id"), -1);
        int currentPage = parseIntOrDefault(req.getParameter("page"), 1);
        String keyword = req.getParameter("keyword");
        String statusFilter = req.getParameter("statusFilter");
        String sort = req.getParameter("sort");
        boolean status = true;
        if ("update".equals(action)) {
            status = "true".equalsIgnoreCase(req.getParameter("status"));
        }

        switch (action) {
            case "add":
                if (dao.isCategoryNameExists(name)) {
                    setMessage(req, "Blog category name already exists.", "danger");
                } else {
                    dao.addCategory(name, status);
                    setMessage(req, "Blog category added.", "success");
                }
                resp.sendRedirect("blog-category?page=1");
                return;

            case "update":
                if (dao.isCategoryNameExistsForOtherId(name, id)) {
                    setMessage(req, "Duplicate blog category name.", "danger");
                } else {
                    dao.updateCategory(id, name, status);
                    setMessage(req, "Blog category updated.", "success");
                    req.getSession().setAttribute("updatedCategoryId", id);
                }
                resp.sendRedirect(buildRedirectUrl(currentPage, keyword, statusFilter, sort));
                return;

            case "delete":
                if (dao.hasBlogDependency(id)) {
                    setMessage(req, "Cannot delete: This category is used by existing blog(s).", "danger");
                } else {
                    dao.deleteCategory(id);
                    setMessage(req, "Blog category deleted successfully.", "success");
                }
                resp.sendRedirect(buildRedirectUrl(currentPage, keyword, statusFilter, sort));
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

    private String encode(String value) {
        return value == null ? "" : java.net.URLEncoder.encode(value, java.nio.charset.StandardCharsets.UTF_8);
    }

    private String buildRedirectUrl(int page, String keyword, String status, String sort) {
        return "blog-category?page=" + page +
                "&keyword=" + encode(keyword) +
                "&status=" + encode(status) +
                "&sort=" + encode(sort);
    }

    private void setMessage(HttpServletRequest req, String msg, String type) {
        req.getSession().setAttribute("message", msg);
        req.getSession().setAttribute("messageType", type);
    }
}
