package controller;

import dal.ProductCategoryDAO;
import dal.SubProductCategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ProductCategory;

import java.io.IOException;
import java.util.List;

@WebServlet("/productCategory")
public class CategoryServlet extends HttpServlet {
    ProductCategoryDAO dao = new ProductCategoryDAO();
    SubProductCategoryDAO subDao = new SubProductCategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        int page = parseIntOrDefault(req.getParameter("page"), 1);
        int pageSize = 10;
        String keyword = req.getParameter("search");
        String sort = req.getParameter("sort");
        String statusFilter = req.getParameter("status");

        if ("edit".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            ProductCategory productCategory = dao.getById(id);
            req.setAttribute("editCategory", productCategory);
        }

        List<ProductCategory> list = dao.getCategoriesByPage(page, pageSize, keyword, sort, statusFilter);
        for (ProductCategory c : list) {
            c.setSubList(subDao.getSubCategoriesByCategoryId(c.getId()));
        }

        int totalPage = (int) Math.ceil((double) dao.countTotalCategories(keyword, statusFilter) / pageSize);
        int startIndex = (page - 1) * pageSize;

        req.setAttribute("list", list);
        req.setAttribute("page", page);
        req.setAttribute("totalPage", totalPage);
        req.setAttribute("startIndex", startIndex);
        req.setAttribute("search", keyword);
        req.setAttribute("sort", sort);
        req.setAttribute("statusFilter", statusFilter);
        req.setAttribute("view", "productCategory");
        req.getRequestDispatcher("/view/home/manager.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        String name = req.getParameter("name") != null ? req.getParameter("name").trim() : null;
        int id = parseIntOrDefault(req.getParameter("id"), -1);
        int currentPage = parseIntOrDefault(req.getParameter("page"), 1);

        String statusParam = req.getParameter("status");
        boolean status = "true".equals(statusParam);

        switch (action) {
            case "add":
                handleAddCategory(req, name);
                resp.sendRedirect("productCategory?page=1");
                return;
            case "update":
                handleUpdateCategory(req, id, name, status);
                resp.sendRedirect("productCategory?page=" + currentPage);
                return;
            case "delete":
                handleDeleteCategory(req, id, resp, currentPage);
                return;
            case "hide":
                dao.hideCategory(id);
                req.getSession().setAttribute("message", "Category hidden.");
                resp.sendRedirect("productCategory?page=" + currentPage);
                return;
        }

        resp.sendRedirect("productCategory");
    }

    private void handleAddCategory(HttpServletRequest req, String name) {
        if (dao.isCategoryNameExists(name) || subDao.isSubNameExistsInAnyCategory(name)) {
            req.getSession().setAttribute("message", "Category name already exists in category or subcategory.");
            req.getSession().setAttribute("messageType", "danger");
        } else {
            dao.addProductCategory(name);
            req.getSession().setAttribute("message", "Category added successfully.");
            req.getSession().setAttribute("messageType", "success");
        }
    }

    private void handleUpdateCategory(HttpServletRequest req, int id, String name, boolean status) {
        if (dao.isCategoryNameExistsForOtherId(name, id) || subDao.isSubNameExistsInAnyCategory(name)) {
            req.getSession().setAttribute("message", "Category name already exists in category or subcategory.");
            req.getSession().setAttribute("messageType", "danger");
        } else {
            dao.updateProductCategory(id, name, status);
            req.getSession().setAttribute("message", "Category updated.");
            req.getSession().setAttribute("messageType", "success");
            req.getSession().setAttribute("updatedCategoryId", id);
        }
    }

    private void handleDeleteCategory(HttpServletRequest req, int id, HttpServletResponse resp, int page) throws ServletException, IOException {
        if (dao.hasDependency(id)) {
            req.getSession().setAttribute("message", "Cannot delete. This category has subcategories or products.");
            req.getSession().setAttribute("messageType", "danger");
            resp.sendRedirect("productCategory?page=" + page);
        } else {
            dao.deleteProductCategory(id);
            req.getSession().setAttribute("message", "Category deleted.");
            req.getSession().setAttribute("messageType", "success");
            resp.sendRedirect("productCategory?page=" + page);
        }
    }

    private int parseIntOrDefault(String raw, int defaultVal) {
        try {
            return Integer.parseInt(raw);
        } catch (Exception e) {
            return defaultVal;
        }
    }
}