package controller;

import dal.BlogCategoryDAO;
import dal.ProductCategoryDAO;
import dal.SubProductCategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.BlogCategory;
import model.ProductCategory;
import model.SubProductCategory;
import model.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import dal.ProductDAO;


@WebServlet(name = "ProductDetailServlet", value = "/productDetail")
public class ProductDetailServlet extends HttpServlet {
    private ProductCategoryDAO categoryDAO;
    private BlogCategoryDAO blogCategoryDAO;
    private SubProductCategoryDAO subProductCategoryDAO;
    private ProductDAO productDAO;

    @Override
    public void init() {
        categoryDAO = new ProductCategoryDAO();
        blogCategoryDAO = new BlogCategoryDAO();
        subProductCategoryDAO = new SubProductCategoryDAO();
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdStr = request.getParameter("product_id");

        if (productIdStr != null && !productIdStr.isEmpty()) {
            try {
                int productId = Integer.parseInt(productIdStr);
                Product product = productDAO.getProductById(productId);

                if (product != null) {
                    request.setAttribute("product", product);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        List<ProductCategory> categories = categoryDAO.getAllProductCategories();
        List<BlogCategory> blogCategories = blogCategoryDAO.getAllBlogCategories();
        List<SubProductCategory> subCategories = new ArrayList<>();
        for (ProductCategory category : categories) {
            subCategories.addAll(subProductCategoryDAO.getSubCategoriesByProductId(category.getId()));
        }

        request.setAttribute("categories", categories);
        request.setAttribute("blogCategories", blogCategories);
        request.setAttribute("subCategories", subCategories);
        request.getRequestDispatcher("/view/product/product-detail.jsp").forward(request, response);
    }
}
