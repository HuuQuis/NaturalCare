package filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dal.BlogCategoryDAO;
import dal.ProductCategoryDAO;
import dal.SubProductCategoryDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import model.BlogCategory;
import model.ProductCategory;
import model.SubProductCategory;

@WebFilter("/*") // Apply to all URLs
public class NavigationDataFilter implements Filter {
    private ProductCategoryDAO categoryDAO;
    private BlogCategoryDAO blogCategoryDAO;
    private SubProductCategoryDAO subProductCategoryDAO;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        categoryDAO = new ProductCategoryDAO();
        blogCategoryDAO = new BlogCategoryDAO();
        subProductCategoryDAO = new SubProductCategoryDAO();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Skip for static resources (CSS, JS, images)
        String requestURI = httpRequest.getRequestURI();
        if (requestURI.endsWith(".css") || requestURI.endsWith(".js") ||
                requestURI.endsWith(".png") || requestURI.endsWith(".jpg") ||
                requestURI.endsWith(".jpeg") || requestURI.endsWith(".gif") ||
                requestURI.endsWith(".ico")) {
            chain.doFilter(request, response);
            return;
        }

        // Load navigation data
        try {
            List<ProductCategory> categories = categoryDAO.getAllProductCategories();
            List<BlogCategory> blogCategories = blogCategoryDAO.getAllBlogCategories();
            List<SubProductCategory> subCategories = new ArrayList<>();

            for (ProductCategory category : categories) {
                subCategories.addAll(subProductCategoryDAO.getSubCategoriesByCategoryId(category.getId()));
            }

            // Set attributes for all requests
            request.setAttribute("categories", categories);
            request.setAttribute("blogCategories", blogCategories);
            request.setAttribute("subCategories", subCategories);

        } catch (Exception e) {
            // Log error but don't break the request
            e.printStackTrace();
        }

        // Continue with the request
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup if needed
    }
}