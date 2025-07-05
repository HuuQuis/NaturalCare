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
        int count;
        String indexPage = request.getParameter("index");
        if (indexPage == null || indexPage.isEmpty()) {
            indexPage = "1";
        }
        int index = Integer.parseInt(indexPage);

        String categoryId = request.getParameter("category");
        String subCategoryId = request.getParameter("subcategory");
        String sort = request.getParameter("sort");

        List<Product> products = new ArrayList<>(); // Initialize with empty list

        boolean hasCategory = categoryId != null && !categoryId.isEmpty();
        boolean hasSubCategory = subCategoryId != null && !subCategoryId.isEmpty();

        // Handle different scenarios
        if (hasSubCategory) {
            int subId = Integer.parseInt(subCategoryId);
            products = productDAO.getProductsBySubCategoryIdSorted(subId, index, pageSize, sort);
            request.setAttribute("selectedSubCategoryId", subCategoryId);

            // ✅ Tìm categoryId từ subcategory
            SubProductCategory sub = subProductCategoryDAO.getSubCategoryById(subId);
            if (sub != null) {
                request.setAttribute("selectedCategoryId", sub.getProductCategoryId());
            }
        }
        else if (hasCategory) {
            // Only category is selected
            products = productDAO.getProductsByCategoryIdSorted(Integer.parseInt(categoryId), index, pageSize, sort);
            request.setAttribute("selectedCategoryId", categoryId);
        }else {
            // No category or subcategory is selected, show all products
            products = productDAO.getProductsByPage(index, pageSize);
        }

        // Always set products attribute (even if empty)
        request.setAttribute("products", products);

        // Load categories and subcategories for sidebar
        List<ProductCategory> categories = categoryDAO.getAllProductCategories();
        List<BlogCategory> blogCategories = blogCategoryDAO.getAllBlogCategories();
        List<SubProductCategory> subCategories = new ArrayList<>();
        for (ProductCategory category : categories) {
            subCategories.addAll(subProductCategoryDAO.getSubCategoriesByCategoryId(category.getId()));
        }

        // Calculate pagination
        count = 0;
        if (hasSubCategory) {
            count = productDAO.getTotalProductsCountBySubCategory(Integer.parseInt(subCategoryId));
        } else if (hasCategory) {
            count = productDAO.getTotalProductsCountByCategory(Integer.parseInt(categoryId));
        }else {
            // If no category or subcategory is selected, count all products
            count = productDAO.countTotalProducts();
        }

        int endPage = count / 6;
        if (count % 6 != 0) {
            endPage++;
        }

        // Set all attributes
        request.setAttribute("endPage", endPage);
        request.setAttribute("categories", categories);
        request.setAttribute("blogCategories", blogCategories);
        request.setAttribute("subCategories", subCategories);
        request.setAttribute("sort", sort);
        request.getRequestDispatcher("/view/product/product.jsp").forward(request, response);
    }
}