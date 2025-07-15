package filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dal.BlogCategoryDAO;
import dal.ProductCategoryDAO;
import dal.SubProductCategoryDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import model.BlogCategory;
import model.ProductCategory;
import model.SubProductCategory;

@WebFilter("/*")
public class NavigationDataFilter implements Filter {
    private ProductCategoryDAO categoryDAO;
    private BlogCategoryDAO blogCategoryDAO;
    private SubProductCategoryDAO subProductCategoryDAO;

    @Override
    public void init(FilterConfig filterConfig) {
        categoryDAO = new ProductCategoryDAO();
        blogCategoryDAO = new BlogCategoryDAO();
        subProductCategoryDAO = new SubProductCategoryDAO();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Skip static resources
        String uri = httpRequest.getRequestURI();
        if (uri.endsWith(".css") || uri.endsWith(".js") ||
                uri.endsWith(".png") || uri.endsWith(".jpg") ||
                uri.endsWith(".jpeg") || uri.endsWith(".gif") ||
                uri.endsWith(".ico")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            // --- 1. Load navigation data ---
            List<ProductCategory> categories = categoryDAO.getAllProductCategories();
            List<BlogCategory> blogCategories = blogCategoryDAO.getAllBlogCategories();
            List<SubProductCategory> subCategories = new ArrayList<>();

            for (ProductCategory category : categories) {
                subCategories.addAll(subProductCategoryDAO.getSubCategoriesByCategoryId(category.getId()));
            }

            request.setAttribute("categories", categories);
            request.setAttribute("blogCategories", blogCategories);
            request.setAttribute("subCategories", subCategories);

            // --- 2. Load cart quantity from cookie ---
            int totalQuantity = getCartQuantityFromCookie(httpRequest);
            request.setAttribute("totalQuantity", totalQuantity);

        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi nếu có
        }

        chain.doFilter(request, response);
    }

    private int getCartQuantityFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        int total = 0;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("cart".equals(cookie.getName())) {
                    String[] items = cookie.getValue().split("\\|");
                    for (String item : items) {
                        String[] parts = item.split(":");
                        if (parts.length == 2) {
                            try {
                                int qty = Integer.parseInt(parts[1]);
                                total += qty;
                            } catch (NumberFormatException ignored) {}
                        }
                    }
                    break;
                }
            }
        }

        return total;
    }
}
