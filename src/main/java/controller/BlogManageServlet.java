package controller;

import dal.BlogCategoryDAO;
import dal.BlogDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Blog;
import model.BlogCategory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@WebServlet("/blog-manage")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)
public class BlogManageServlet extends HttpServlet {
    private final BlogDAO blogDAO = new BlogDAO();
    private final BlogCategoryDAO categoryDAO = new BlogCategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        int id = parseIntOrDefault(req.getParameter("id"), -1);
        int page = parseIntOrDefault(req.getParameter("page"), 1);
        String rawCategoryId = req.getParameter("categoryId");
        int categoryId = (rawCategoryId == null || rawCategoryId.trim().isEmpty()) ? -1 : parseIntOrDefault(rawCategoryId, -1);
        String keyword = req.getParameter("keyword");
        if (keyword == null) keyword = "";
        String sort = req.getParameter("sort");
        String statusFilter = req.getParameter("status");

        int pageSize = 10;

        if ("form".equals(action)) {
            if (id != -1) {
                Blog blog = blogDAO.getBlogById(id);
                if (blog != null) {
                    blog.setImageUrl(blogDAO.getImagePathByBlogId(id));
                    req.setAttribute("editBlog", blog);
                }
            }

            List<BlogCategory> categories = categoryDAO.getAllBlogCategories();
            req.setAttribute("categories", categories);
            req.setAttribute("page", page);
            req.setAttribute("view", "blog-form");
            req.getRequestDispatcher("/view/home/marketer.jsp").forward(req, resp);
            return;
        }

        List<BlogCategory> categories = categoryDAO.getAllBlogCategories();
        req.setAttribute("categories", categories);
        req.setAttribute("page", page);
        req.setAttribute("selectedCategory", categoryId);
        req.setAttribute("keyword", keyword);
        req.setAttribute("sort", sort);
        req.setAttribute("statusFilter", statusFilter);

        List<Blog> list = blogDAO.searchBlogsAdvanced(categoryId, keyword, sort, statusFilter, page, pageSize);
        int totalCount = blogDAO.countSearchBlogsAdvanced(categoryId, keyword, statusFilter);

        int totalPage = (int) Math.ceil((double) totalCount / pageSize);
        int startIndex = (page - 1) * pageSize;

        req.setAttribute("list", list);
        req.setAttribute("totalPage", totalPage);
        req.setAttribute("startIndex", startIndex);
        req.setAttribute("view", "blog-manage");
        req.getRequestDispatcher("/view/home/marketer.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        int id = parseIntOrDefault(req.getParameter("id"), -1);
        String rawCategoryId = req.getParameter("categoryId");
        int categoryId = (rawCategoryId == null || rawCategoryId.trim().isEmpty()) ? -1 : parseIntOrDefault(rawCategoryId, -1);
        int page = parseIntOrDefault(req.getParameter("page"), 1);

        BlogCategory category = categoryDAO.getCategoryById(categoryId);
        String imagePath = null;

        if ("add".equals(action) || "update".equals(action)) {
            try {
                Part filePart = req.getPart("image");
                String oldImagePath = blogDAO.getImagePathByBlogId(id);
                if (filePart != null && filePart.getSize() > 0) {
                    String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
                    String uploadPath = getServletContext().getRealPath("/image/blog");
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) uploadDir.mkdirs();

                    if ("update".equals(action)) {
                        if (oldImagePath != null && !oldImagePath.isEmpty()) {
                            File oldFile = new File(getServletContext().getRealPath("/" + oldImagePath));
                            if (oldFile.exists()) Files.delete(oldFile.toPath());
                        }
                    }

                    filePart.write(uploadPath + File.separator + fileName);
                    imagePath = "image/blog/" + fileName;
                } else {
                    if ("update".equals(action)) {
                        imagePath = oldImagePath;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                req.getSession().setAttribute("message", "Image upload failed.");
                req.getSession().setAttribute("messageType", "danger");
                resp.sendRedirect("blog-manage?page=" + page);
                return;
            }
        }

        switch (action) {
            case "add":
                int newId = blogDAO.addBlog(new Blog(0, title, description, null, category));
                if (imagePath != null) blogDAO.addBlogImage(newId, imagePath);
                req.getSession().setAttribute("message", "Blog added successfully.");
                req.getSession().setAttribute("messageType", "success");
                resp.sendRedirect("blog-manage?page=1");
                return;

            case "update":
                blogDAO.updateBlog(new Blog(id, title, description, null, category));
                if (imagePath != null) blogDAO.updateBlogImage(id, imagePath);
                req.getSession().setAttribute("message", "Blog updated successfully.");
                req.getSession().setAttribute("messageType", "success");
                resp.sendRedirect("blog-manage?page=" + page);
                return;

            case "delete":
                blogDAO.deleteBlog(id);
                req.getSession().setAttribute("message", "Blog deleted successfully.");
                req.getSession().setAttribute("messageType", "success");
                resp.sendRedirect("blog-manage?page=" + page);
                return;
        }

        resp.sendRedirect("blog-manage");
    }

    private int parseIntOrDefault(String raw, int defaultVal) {
        try {
            return Integer.parseInt(raw);
        } catch (Exception e) {
            return defaultVal;
        }
    }
}
