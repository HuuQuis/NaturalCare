package controller;

import dal.BlogCategoryDAO;
import dal.ProductDAO;
import dal.ProductCategoryDAO;
import dal.SubProductCategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.BlogCategory;
import model.Product;
import model.ProductCategory;
import model.SubProductCategory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductServlet", value = "/products")
public class ProductServlet extends HttpServlet {
    private ProductDAO productDAO;
    private ProductCategoryDAO categoryDAO;
    private BlogCategoryDAO blogCategoryDAO;
    private SubProductCategoryDAO subProductCategoryDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
        categoryDAO = new ProductCategoryDAO();
        blogCategoryDAO = new BlogCategoryDAO();
        subProductCategoryDAO = new SubProductCategoryDAO();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pageSize = 6;
        String indexPage = request.getParameter("index");
        int index = (indexPage == null || indexPage.isEmpty()) ? 1 : Integer.parseInt(indexPage);

        String categoryId = request.getParameter("category");
        String subCategoryId = request.getParameter("subcategory");
        String sort = request.getParameter("sort");

        // ✅ Thêm lọc theo khoảng giá
        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");
        Double minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Double.parseDouble(minPriceStr) : null;
        Double maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? Double.parseDouble(maxPriceStr) : null;

        // Lưu để hiển thị lại slider
        request.setAttribute("minPrice", minPriceStr);
        request.setAttribute("maxPrice", maxPriceStr);

        List<Product> products = new ArrayList<>();
        boolean hasCategory = categoryId != null && !categoryId.isEmpty();
        boolean hasSubCategory = subCategoryId != null && !subCategoryId.isEmpty();

        // ✅ Gọi DAO hỗ trợ lọc giá
        if (hasSubCategory) {
            int subId = Integer.parseInt(subCategoryId);
            products = productDAO.getProductsBySubCategoryIdSorted(subId, index, pageSize, sort, minPrice, maxPrice);
            request.setAttribute("selectedSubCategoryId", subCategoryId);

            SubProductCategory sub = subProductCategoryDAO.getSubCategoryById(subId);
            if (sub != null) {
                request.setAttribute("selectedCategoryId", sub.getProductCategoryId());
            }
        } else if (hasCategory) {
            int catId = Integer.parseInt(categoryId);
            products = productDAO.getProductsByCategoryIdSorted(catId, index, pageSize, sort, minPrice, maxPrice);
            request.setAttribute("selectedCategoryId", categoryId);
        } else {
            products = productDAO.getProductsByPage(index, pageSize, sort, minPrice, maxPrice);
        }

        request.setAttribute("products", products);

        // Load categories/subcategories/blog categories
        List<ProductCategory> categories = categoryDAO.getAllProductCategories();
        List<BlogCategory> blogCategories = blogCategoryDAO.getAllBlogCategories();
        List<SubProductCategory> subCategories = new ArrayList<>();
        for (ProductCategory category : categories) {
            subCategories.addAll(subProductCategoryDAO.getSubCategoriesByCategoryId(category.getId()));
        }

        // ✅ Đếm tổng sản phẩm theo khoảng giá
        int count;
        if (hasSubCategory) {
            count = productDAO.getTotalProductsCountBySubCategoryAndPrice(Integer.parseInt(subCategoryId), minPrice, maxPrice);
        } else if (hasCategory) {
            count = productDAO.getTotalProductsCountByCategoryAndPrice(Integer.parseInt(categoryId), minPrice, maxPrice);
        } else {
            count = productDAO.countTotalProductsByPrice(minPrice, maxPrice);
        }

        int endPage = count / pageSize + (count % pageSize == 0 ? 0 : 1);

        // Set attribute
        request.setAttribute("endPage", endPage);
        request.setAttribute("categories", categories);
        request.setAttribute("blogCategories", blogCategories);
        request.setAttribute("subCategories", subCategories);
        request.setAttribute("sort", sort);

        request.getRequestDispatcher("/view/product/product.jsp").forward(request, response);
    }

}