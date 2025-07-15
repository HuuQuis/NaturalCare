package controller;

import dal.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ProductServlet", value = "/products")
public class ProductServlet extends HttpServlet {
    private ProductDAO productDAO;
    private ProductCategoryDAO categoryDAO;
    private BlogCategoryDAO blogCategoryDAO;
    private SubProductCategoryDAO subProductCategoryDAO;
    private ColorDAO ColorDAO;
    private SizeDAO SizeDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
        categoryDAO = new ProductCategoryDAO();
        blogCategoryDAO = new BlogCategoryDAO();
        subProductCategoryDAO = new SubProductCategoryDAO();
        ColorDAO = new ColorDAO();
        SizeDAO = new SizeDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pageSize = 9;
        String indexPage = request.getParameter("index");
        int index = (indexPage == null || indexPage.isEmpty()) ? 1 : Integer.parseInt(indexPage);

        String categoryId = request.getParameter("category");
        String subCategoryId = request.getParameter("subcategory");
        String sort = request.getParameter("sort");

        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");
        Double minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Double.parseDouble(minPriceStr) : null;
        Double maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? Double.parseDouble(maxPriceStr) : null;

        // Lấy tên color và size từ URL
        String[] colorNames = request.getParameterValues("color");
        String[] sizeNames = request.getParameterValues("size");

        // Chuyển đổi name → id bằng DAO
        List<Integer> colorIds = ColorDAO.getIdsByNames(colorNames);
        List<Integer> sizeIds = SizeDAO.getIdsByNames(sizeNames);

        // Truyền lại để giữ trạng thái đã chọn
        request.setAttribute("minPrice", minPriceStr);
        request.setAttribute("maxPrice", maxPriceStr);
        request.setAttribute("selectedColors", colorNames);  // giữ name
        request.setAttribute("selectedSizes", sizeNames);    // giữ name

        boolean hasCategory = categoryId != null && !categoryId.isEmpty();
        boolean hasSubCategory = subCategoryId != null && !subCategoryId.isEmpty();

        boolean filteringVariation = isFilteringByVariation(minPrice, maxPrice, colorIds, sizeIds);
        List<Product> products;

        if (hasSubCategory) {
            int subId = Integer.parseInt(subCategoryId);
            if (filteringVariation) {
                products = productDAO.getProductsWithFilters(index, pageSize, sort, minPrice, maxPrice, null, subId, colorIds, sizeIds);
            } else {
                products = productDAO.getAllProducts(index, pageSize, sort, null, subId);
            }
            request.setAttribute("selectedSubCategoryId", subCategoryId);

            SubProductCategory sub = subProductCategoryDAO.getSubCategoryById(subId);
            if (sub != null) {
                request.setAttribute("selectedCategoryId", sub.getProductCategoryId());
            }

        } else if (hasCategory) {
            int catId = Integer.parseInt(categoryId);
            if (filteringVariation) {
                products = productDAO.getProductsWithFilters(index, pageSize, sort, minPrice, maxPrice, catId, null, colorIds, sizeIds);
            } else {
                products = productDAO.getAllProducts(index, pageSize, sort, catId, null);
            }
            request.setAttribute("selectedCategoryId", categoryId);

        } else {
            if (filteringVariation) {
                products = productDAO.getProductsWithFilters(index, pageSize, sort, minPrice, maxPrice, null, null, colorIds, sizeIds);
            } else {
                products = productDAO.getAllProducts(index, pageSize, sort, null, null);
            }
        }

        request.setAttribute("products", products);

        List<ProductCategory> categories = categoryDAO.getAllProductCategories();
        List<BlogCategory> blogCategories = blogCategoryDAO.getAllBlogCategories();
        List<SubProductCategory> subCategories = new ArrayList<>();
        for (ProductCategory category : categories) {
            subCategories.addAll(subProductCategoryDAO.getSubCategoriesByCategoryId(category.getId()));
        }

        Integer catId = (categoryId != null && !categoryId.isEmpty()) ? Integer.parseInt(categoryId) : null;
        Integer subCatId = (subCategoryId != null && !subCategoryId.isEmpty()) ? Integer.parseInt(subCategoryId) : null;

        int totalCount = filteringVariation
                ? productDAO.countProductsWithFilters(minPrice, maxPrice, categoryId, subCategoryId, colorIds, sizeIds)
                : productDAO.countAllProducts(catId, subCatId);
        int endPage = totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1);

        request.setAttribute("endPage", endPage);
        request.setAttribute("categories", categories);
        request.setAttribute("blogCategories", blogCategories);
        request.setAttribute("subCategories", subCategories);
        request.setAttribute("sort", sort);

        List<Color> colorList = ColorDAO.getAllColors();
        List<Size> sizeList = SizeDAO.getAllSizes();
        request.setAttribute("colorList", colorList);
        request.setAttribute("sizeList", sizeList);

        request.getRequestDispatcher("/view/product/product.jsp").forward(request, response);
    }


    private boolean isFilteringByVariation(Double minPrice, Double maxPrice,
                                           List<Integer> colorIds, List<Integer> sizeIds) {
        return (minPrice != null || maxPrice != null) ||
                (colorIds != null && !colorIds.isEmpty()) ||
                (sizeIds != null && !sizeIds.isEmpty());
    }
}
