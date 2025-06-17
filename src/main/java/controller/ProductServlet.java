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
            String indexPage = request.getParameter("index");
            if (indexPage == null || indexPage.isEmpty()) {
                indexPage = "1"; // Default to the first page if not specified
            }
            int index = Integer.parseInt(indexPage);
            String categoryId = request.getParameter("category");
            String subCategoryId = request.getParameter("subcategory");
            String sort = request.getParameter("sort");
            List<Product> products;

        boolean hasCategory = categoryId != null && !categoryId.isEmpty();
        boolean hasSubCategory = subCategoryId != null && !subCategoryId.isEmpty();

        if (hasCategory) {
            if (hasSubCategory) {
                products = productDAO.getProductsBySubCategoryIdSorted(Integer.parseInt(subCategoryId), index, sort);
                request.setAttribute("selectedCategoryId", categoryId);
                request.setAttribute("selectedSubCategoryId", subCategoryId);
            } else {
                products = productDAO.getProductsByCategoryIdSorted(Integer.parseInt(categoryId), index, sort);
                request.setAttribute("selectedCategoryId", categoryId);
            }
            request.setAttribute("products", products);
        } else if (hasSubCategory) {
            products = productDAO.getProductsBySubCategoryIdSorted(Integer.parseInt(subCategoryId), index, sort);
            request.setAttribute("selectedSubCategoryId", subCategoryId);
            request.setAttribute("products", products);
        }


            List<ProductCategory> categories = categoryDAO.getAllProductCategories();
            List<BlogCategory> blogCategories = blogCategoryDAO.getAllBlogCategories();
            List<SubProductCategory> subCategories = new ArrayList<>();
            for (ProductCategory category : categories) {
                subCategories.addAll(subProductCategoryDAO.getSubCategoriesByCategoryId(category.getId()));
            }

        int count = hasCategory ? productDAO.getTotalProductsCountByCategory(Integer.parseInt(categoryId))
                : hasSubCategory ? productDAO.getTotalProductsCountBySubCategory(Integer.parseInt(subCategoryId))
                : 0;
        int endPage = count / 6;
        if( count % 6 != 0) {
            endPage++;
        }
        request.setAttribute("endPage", endPage);
        request.setAttribute("categories", categories);
        request.setAttribute("blogCategories", blogCategories);
        request.setAttribute("subCategories", subCategories);
        request.setAttribute("sort", sort);

        request.getRequestDispatcher("/view/product/product.jsp").forward(request, response);
    }
}