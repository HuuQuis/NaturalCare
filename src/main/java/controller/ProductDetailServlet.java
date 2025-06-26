package controller;

import dal.BlogCategoryDAO;
import dal.ProductCategoryDAO;
import dal.SubProductCategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.BlogCategory;
import model.ProductCategory;
import model.SubProductCategory;
import model.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

                    // 👉 Gửi thêm map cart quantities lên để xử lý "max stock"
                    Map<Integer, Integer> cartQuantities = readCartFromCookie(request);
                    request.setAttribute("cartQuantities", cartQuantities);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        List<ProductCategory> categories = categoryDAO.getAllProductCategories();
        List<BlogCategory> blogCategories = blogCategoryDAO.getAllBlogCategories();
        List<SubProductCategory> subCategories = new ArrayList<>();
        for (ProductCategory category : categories) {
            subCategories.addAll(subProductCategoryDAO.getSubCategoriesByCategoryId(category.getId()));
        }

        request.setAttribute("categories", categories);
        request.setAttribute("blogCategories", blogCategories);
        request.setAttribute("subCategories", subCategories);
        request.getRequestDispatcher("/view/product/product-detail.jsp").forward(request, response);
    }

    // ✅ Hàm đọc cookie "cart" → Map<variationId, quantity>
    private Map<Integer, Integer> readCartFromCookie(HttpServletRequest request) {
        Map<Integer, Integer> map = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("cart".equals(c.getName())) {
                    String[] items = c.getValue().split("\\|");
                    for (String item : items) {
                        String[] parts = item.split(":");
                        if (parts.length == 2) {
                            try {
                                int id = Integer.parseInt(parts[0]);
                                int qty = Integer.parseInt(parts[1]);
                                map.put(id, qty);
                            } catch (NumberFormatException ignored) {
                            }
                        }
                    }
                }
            }
        }
        return map;
    }
}
