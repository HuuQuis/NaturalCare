package dal;

import model.Blog;
import model.BlogCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BlogDAO extends DBContext {

    private final BlogCategoryDAO categoryDAO = new BlogCategoryDAO();

    public List<Blog> getBlogsByPage(int pageIndex, int pageSize) {
        List<Blog> list = new ArrayList<>();
        String sql = "SELECT b.*, i.blog_image " +
                "FROM blog b " +
                "LEFT JOIN blog_image i ON b.blog_id = i.blog_id " +
                "ORDER BY b.date_published DESC " +
                "LIMIT ? OFFSET ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, pageSize);
            ps.setInt(2, (pageIndex - 1) * pageSize);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BlogCategory category = categoryDAO.getCategoryById(rs.getInt("blog_category_id"));
                Blog blog = new Blog(
                        rs.getInt("blog_id"),
                        rs.getString("blog_title"),
                        rs.getString("blog_description"),
                        rs.getTimestamp("date_published"),
                        category
                );
                blog.setImageUrl(rs.getString("blog_image"));
                list.add(blog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Blog> getBlogsByCategoryIdAndPage(int categoryId, int pageIndex, int pageSize) {
        List<Blog> list = new ArrayList<>();
        String sql = "SELECT b.*, i.blog_image " +
                "FROM blog b " +
                "LEFT JOIN blog_image i ON b.blog_id = i.blog_id " +
                "WHERE b.blog_category_id = ? " +
                "ORDER BY b.date_published DESC " +
                "LIMIT ? OFFSET ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ps.setInt(2, pageSize);
            ps.setInt(3, (pageIndex - 1) * pageSize);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BlogCategory category = categoryDAO.getCategoryById(categoryId);
                Blog blog = new Blog(
                        rs.getInt("blog_id"),
                        rs.getString("blog_title"),
                        rs.getString("blog_description"),
                        rs.getTimestamp("date_published"),
                        category
                );
                blog.setImageUrl(rs.getString("blog_image"));
                list.add(blog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalBlogCount() {
        String sql = "SELECT COUNT(*) FROM blog";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getBlogCountByCategory(int categoryId) {
        String sql = "SELECT COUNT(*) FROM blog WHERE blog_category_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    // Thêm blog mới
    public void addBlog(Blog blog) {
        String sql = "INSERT INTO blog (blog_title, blog_description, blog_category_id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, blog.getBlogTitle());
            ps.setString(2, blog.getBlogDescription());
            ps.setInt(3, blog.getBlogCategory().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật blog
    public void updateBlog(Blog blog) {
        String sql = "UPDATE blog SET blog_title = ?, blog_description = ?, blog_category_id = ? WHERE blog_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, blog.getBlogTitle());
            ps.setString(2, blog.getBlogDescription());
            ps.setInt(3, blog.getBlogCategory().getId());
            ps.setInt(4, blog.getBlogId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xoá blog
    public void deleteBlog(int id) {
        String sql = "DELETE FROM blog WHERE blog_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
