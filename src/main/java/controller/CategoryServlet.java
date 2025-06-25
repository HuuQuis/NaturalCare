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
import java.sql.SQLException;
import java.util.List;

@WebServlet("/category")
public class CategoryServlet extends HttpServlet {
    ProductCategoryDAO dao = new ProductCategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            ProductCategory category = dao.getById(id);
            request.setAttribute("category", category);
            //request.getRequestDispatcher("view/category/formSub.jsp").forward(request, response);
        } else {
            // --- Phân trang ---
            String pageRaw = request.getParameter("page");
            int page = (pageRaw == null || pageRaw.isEmpty()) ? 1 : Integer.parseInt(pageRaw);
            int pageSize = 10;

            List<ProductCategory> list = dao.getCategoriesByPage(page, pageSize);

            // Gán subcategory cho từng category
            SubProductCategoryDAO subDao = new SubProductCategoryDAO();
            for (ProductCategory c : list) {
                c.setSubList(subDao.getSubCategoriesByCategoryId(c.getId()));
            }

            int total = dao.countTotalCategories();
            int totalPage = (int) Math.ceil((double) total / pageSize);

            // Set dữ liệu cho JSP
            request.setAttribute("view", "category");
            request.setAttribute("list", list);
            request.setAttribute("page", page);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("totalPage", totalPage);

            request.getRequestDispatcher("/view/home/manager.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String name = request.getParameter("name");
        String idRaw = request.getParameter("id");

        SubProductCategoryDAO subDao = new SubProductCategoryDAO();

        if ("add".equals(action)) {
            if (dao.isCategoryNameExists(name) || subDao.isSubNameExistsInAnyCategory(name)) {
                request.getSession().setAttribute("message", "This name already exists in category or subcategory.");
            } else {
                dao.addProductCategory(name);
                request.getSession().setAttribute("message", "Category added successfully.");
            }

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(idRaw);
            if (dao.isCategoryNameExistsForOtherId(name, id) || subDao.isSubNameExistsInAnyCategory(name)) {
                request.getSession().setAttribute("message", "Name already exists in another category or subcategory.");
            } else {
                dao.updateProductCategory(id, name);
                request.getSession().setAttribute("message", "Category updated.");
            }

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(idRaw);
            if (dao.hasDependency(id)) {
                // Nếu còn sub/product → forward về trang và hiển thị confirm ẩn
                request.setAttribute("hasDependency", true);
                request.setAttribute("categoryIdToHide", id);

                // Load lại danh sách như trong doGet
                int page = 1;
                int pageSize = 10;
                List<ProductCategory> list = dao.getCategoriesByPage(page, pageSize);
                for (ProductCategory c : list) {
                    c.setSubList(subDao.getSubCategoriesByCategoryId(c.getId()));
                }
                int total = dao.countTotalCategories();
                int totalPage = (int) Math.ceil((double) total / pageSize);

                request.setAttribute("view", "category");
                request.setAttribute("list", list);
                request.setAttribute("page", page);
                request.setAttribute("pageSize", pageSize);
                request.setAttribute("totalPage", totalPage);

                request.getRequestDispatcher("/view/home/manager.jsp").forward(request, response);
                return;
            } else {
                dao.deleteProductCategory(id);
                request.getSession().setAttribute("message", "Category deleted.");
            }

        } else if ("hide".equals(action)) {
            int id = Integer.parseInt(idRaw);
            dao.hideCategory(id);
            request.getSession().setAttribute("message", "Category has been hidden.");
        }

        response.sendRedirect("subcategory");
    }
}
