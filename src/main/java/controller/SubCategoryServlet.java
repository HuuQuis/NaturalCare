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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/subcategory")
public class SubCategoryServlet extends HttpServlet {
    SubProductCategoryDAO subDao = new SubProductCategoryDAO();
    ProductCategoryDAO catDao = new ProductCategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        int subId = parseIntOrDefault(req.getParameter("id"), -1);
        if ("edit".equals(action) && subId != -1) {
            SubProductCategory sub = subDao.getById(subId);
            req.setAttribute("editSub", sub);
        }

        Integer categoryId = parseNullableInt(req.getParameter("productCategoryId"));
        String keyword = req.getParameter("search");
        int page = parseIntOrDefault(req.getParameter("page"), 1);
        int pageSize = 10;

        List<SubProductCategory> subList = subDao.getFilteredSubcategoriesByPage(keyword, categoryId, page, pageSize);
        int totalSub = subDao.countFilteredSubcategories(keyword, categoryId);
        int totalPage = (int) Math.ceil((double) totalSub / pageSize);

        req.setAttribute("subList", subList);
        req.setAttribute("categoryList", catDao.getAllProductCategories());
        req.setAttribute("page", page);
        req.setAttribute("pageSize", pageSize);
        req.setAttribute("totalPage", totalPage);
        req.setAttribute("totalSub", totalSub);
        req.setAttribute("startIndex", (page - 1) * pageSize);
        req.setAttribute("filterKeyword", keyword);
        req.setAttribute("filterCategoryId", categoryId);
        req.setAttribute("view", "subcategory");

        req.getRequestDispatcher("/view/home/manager.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        String name = req.getParameter("name") != null ? req.getParameter("name").trim() : "";
        int id = parseIntOrDefault(req.getParameter("id"), -1);
        int catId = parseIntOrDefault(req.getParameter("productCategoryId"), -1);
        int currentPage = parseIntOrDefault(req.getParameter("page"), 1);
        String search = req.getParameter("search") != null ? req.getParameter("search") : "";

        String redirectUrl = "subcategory?page=";
        boolean redirectWithFilter = true;

        switch (action) {
            case "add":
                if (subDao.isSubNameExists(name, catId) || catDao.isCategoryNameExists(name)) {
                    req.getSession().setAttribute("message", "SubCategory name already exists in category or subcategory.");
                    req.getSession().setAttribute("messageType", "danger");
                } else {
                    subDao.addSubCategory(name, catId);
                    req.getSession().setAttribute("message", "Subcategory added.");
                    req.getSession().setAttribute("messageType", "success");
                }
                redirectUrl += "1"; // Always redirect to page 1 after add
                break;
            case "update":
                if (subDao.isSubNameExistsForOtherId(name, catId, id) || catDao.isCategoryNameExists(name)) {
                    req.getSession().setAttribute("message", "SubCategory name already exists in category or subcategory.");
                    req.getSession().setAttribute("messageType", "danger");
                } else {
                    subDao.updateSubCategory(id, name);
                    req.getSession().setAttribute("message", "Subcategory updated.");
                    req.getSession().setAttribute("messageType", "success");
                    req.getSession().setAttribute("updatedSubCategoryId", id);
                }
                redirectUrl += currentPage;
                break;
            case "delete":
                if (subDao.hasProductDependency(id)) {
                    req.setAttribute("hasSubDependency", true);
                    req.setAttribute("subCategoryIdToHide", id);
                    doGet(req, resp);
                    return;
                } else {
                    subDao.deleteSubCategory(id);
                    req.getSession().setAttribute("message", "Subcategory deleted.");
                    req.getSession().setAttribute("messageType", "success");
                }
                redirectUrl += currentPage;
                break;
            case "hide":
                subDao.hideSubCategory(id);
                req.getSession().setAttribute("message", "Subcategory hidden.");
                req.getSession().setAttribute("messageType", "success");
                redirectUrl += currentPage;
                break;
        }

        if (redirectWithFilter) {
            if (catId != -1) {
                redirectUrl += "&productCategoryId=" + catId;
            }
            if (!search.isEmpty()) {
                redirectUrl += "&search=" + URLEncoder.encode(search, StandardCharsets.UTF_8);
            }
        }

        resp.sendRedirect(redirectUrl);
    }

    private Integer parseNullableInt(String s) {
        try {
            return (s == null || s.isEmpty()) ? null : Integer.parseInt(s);
        } catch (Exception e) {
            return null;
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
