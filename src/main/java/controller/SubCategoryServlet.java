package controller;

import dal.ProductCategoryDAO;
import dal.SubProductCategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.SubProductCategory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/subcategory")
public class SubCategoryServlet extends HttpServlet {
    SubProductCategoryDAO subDao = new SubProductCategoryDAO();
    ProductCategoryDAO catDao = new ProductCategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("edit".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            SubProductCategory sub = subDao.getById(id);
            req.setAttribute("sub", sub);
        }

        String categoryIdRaw = req.getParameter("productCategoryId");
        String keyword = req.getParameter("search");
        Integer categoryId = null;
        if (categoryIdRaw != null && !categoryIdRaw.isEmpty()) {
            categoryId = Integer.parseInt(categoryIdRaw);
        }

        List<SubProductCategory> subList = subDao.getFilteredSubcategories(keyword, categoryId);
        req.setAttribute("subList", subList);
        req.setAttribute("categoryList", catDao.getAllProductCategories());

        req.setAttribute("view", "subcategory");
        req.getRequestDispatcher("/view/home/manager.jsp").forward(req, resp);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        String name = req.getParameter("name");
        String idRaw = req.getParameter("id");
        String categoryIdRaw = req.getParameter("productCategoryId");

        try {
            int categoryId = Integer.parseInt(categoryIdRaw);

            if ("add".equals(action)) {
                if (subDao.isSubNameExists(name, categoryId)) {
                    req.getSession().setAttribute("message", "️ Subcategory name already exists in this category.");
                } else {
                    subDao.addSubCategory(name, categoryId);
                    req.getSession().setAttribute("message", " Subcategory added successfully.");
                }

            } else if ("update".equals(action)) {
                int id = Integer.parseInt(idRaw);
                if (subDao.isSubNameExistsForOtherId(name, categoryId, id)) {
                    req.getSession().setAttribute("message", "Subcategory name already exists in this category.");
                } else {
                    subDao.updateSubCategory(id, name);
                    req.getSession().setAttribute("message", "Subcategory updated.");
                }

            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(idRaw);
                if (subDao.hasProductDependency(id)) {
                    req.setAttribute("hasSubDependency", true);
                    req.setAttribute("subCategoryIdToHide", id);
                    req.setAttribute("view", "category");
                    req.getRequestDispatcher("/view/home/manager.jsp").forward(req, resp);
                    return;
                } else {
                    subDao.deleteSubCategory(id);
                    req.getSession().setAttribute("message", "️ Subcategory deleted.");
                }

            } else if ("hide".equals(action)) {
                int id = Integer.parseInt(idRaw);
                subDao.hideSubCategory(id);
                req.getSession().setAttribute("message", "️ Subcategory hidden.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid or missing parameter.");
            return;
        }

        resp.sendRedirect("category");
    }
}
