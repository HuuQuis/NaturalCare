package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

import java.io.IOException;

@WebFilter(urlPatterns = {"/staff/*", "/admin/*", "/manager/*"})
public class PermissionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // get user from session
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }

        // not logged in -> login page
        if (user == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String path = requestURI.substring(contextPath.length());

        // check permission
        boolean hasPermission = checkPermission(path, user.getRole());

        if (hasPermission) {
            // continue
            chain.doFilter(request, response);
        } else {
            // deny access
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Access denied. You don't have permission to access this resource.");
        }
    }

    private boolean checkPermission(String path, int userRole) {
        // Staff
        if (path.startsWith("/staff")) {
            return userRole == 2;
        }

        // Admin
        if (path.startsWith("/admin")) {
            return userRole == 3;
        }

        // Manager
        if (path.startsWith("/manager")) {
            return userRole == 4;
        }

        // Default deny
        return false;
    }

    @Override
    public void destroy() {
    }
} 