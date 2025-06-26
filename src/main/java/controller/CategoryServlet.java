package controller;

import dal.ProductCategoryDAO;
import dal.SubProductCategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import model.ProductCategory;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category")
public class CategoryServlet extends HttpServlet {
    ProductCategoryDAO dao = new ProductCategoryDAO();
    SubProductCategoryDAO subDao = new SubProductCategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        int page = parseIntOrDefault(req.getParameter("page"), 1);
        int pageSize = 10;

        if ("edit".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            ProductCategory category = dao.getById(id);
            req.setAttribute("editCategory", category); // hiển thị lên form nếu cần
        }

        List<ProductCategory> list = dao.getCategoriesByPage(page, pageSize);
        for (ProductCategory c : list) {
            c.setSubList(new SubProductCategoryDAO().getSubCategoriesByCategoryId(c.getId()));
        }

        int totalPage = (int) Math.ceil((double) dao.countTotalCategories() / pageSize);
        int startIndex = (page - 1) * pageSize;

        req.setAttribute("list", list);
        req.setAttribute("page", page);
        req.setAttribute("totalPage", totalPage);
        req.setAttribute("startIndex", startIndex);
        req.setAttribute("view", "category");
        req.getRequestDispatcher("/view/home/manager.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        String name = req.getParameter("name") != null ? req.getParameter("name").trim() : null;
        int id = parseIntOrDefault(req.getParameter("id"), -1);
        int currentPage = parseIntOrDefault(req.getParameter("page"), 1);  // nhận page gửi từ form

        switch (action) {
            case "add":
                handleAddCategory(req, name);
                // Quay lại trang 1 để thấy dòng vừa thêm mới đầu bảng
                resp.sendRedirect("category?page=1");
                return;
            case "update":
                handleUpdateCategory(req, id, name);
                // Quay lại đúng trang đang sửa
                resp.sendRedirect("category?page=" + currentPage);
                return;
            case "delete":
                handleDeleteCategory(req, id, req, resp, currentPage);
                return;
            case "hide":
                dao.hideCategory(id);
                req.getSession().setAttribute("message", "Category hidden.");
                resp.sendRedirect("category?page=" + currentPage);
                return;
        }

        resp.sendRedirect("category");
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

    private void handleUpdateCategory(HttpServletRequest req, int id, String name) {
        if (dao.isCategoryNameExistsForOtherId(name, id) || subDao.isSubNameExistsInAnyCategory(name)) {
            req.getSession().setAttribute("message", "Category name already exists in category or subcategory.");
            req.getSession().setAttribute("messageType", "danger");
        } else {
            dao.updateProductCategory(id, name);
            req.getSession().setAttribute("message", "Category updated.");
            req.getSession().setAttribute("messageType", "success");
            req.getSession().setAttribute("updatedCategoryId", id);
        }
    }

    private void handleDeleteCategory(HttpServletRequest req, int id, HttpServletRequest request, HttpServletResponse response, int page) throws ServletException, IOException {
        if (dao.hasDependency(id)) {
            request.setAttribute("hasDependency", true);
            request.setAttribute("categoryIdToHide", id);
            doGet(request, response); // load lại giao diện
        } else {
            dao.deleteProductCategory(id);
            req.getSession().setAttribute("message", "Category deleted.");
            req.getSession().setAttribute("messageType", "success");
            response.sendRedirect("category?page=" + page);
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
