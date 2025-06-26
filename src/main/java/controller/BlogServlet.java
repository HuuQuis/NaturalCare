package controller;

import dal.BlogCategoryDAO;
import dal.BlogDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Blog;
import model.BlogCategory;

import java.io.IOException;
import java.util.List;

@WebServlet("/blogs")
public class BlogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int page = 1;
        int size = 10;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException ignored) {}
        }

        String blogCategoryParam = request.getParameter("blogCategory");

        BlogDAO blogDAO = new BlogDAO();
        BlogCategoryDAO cateDAO = new BlogCategoryDAO();

        List<Blog> blogList;
        int totalBlogs;
        BlogCategory selectedCategory = null;

        if (blogCategoryParam != null) {
            try {
                int categoryId = Integer.parseInt(blogCategoryParam);
                blogList = blogDAO.getBlogsByCategoryIdAndPage(categoryId, page, size);
                totalBlogs = blogDAO.getBlogCountByCategory(categoryId);
                selectedCategory = cateDAO.getCategoryById(categoryId);
                request.setAttribute("selectedCategory", selectedCategory);
            } catch (NumberFormatException e) {
                blogList = blogDAO.getBlogsByPage(page, size);
                totalBlogs = blogDAO.getTotalBlogCount();
            }
        } else {
            blogList = blogDAO.getBlogsByPage(page, size);
            totalBlogs = blogDAO.getTotalBlogCount();
        }

        int totalPages = (int) Math.ceil((double) totalBlogs / size);

        request.setAttribute("blogList", blogList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("blogCategories", cateDAO.getAllBlogCategories());

        request.getRequestDispatcher("view/blog/blog.jsp").forward(request, response);
    }
}

