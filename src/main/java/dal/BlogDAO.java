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

    public Blog getBlogById(int id) {
        String sql = "SELECT b.*, i.blog_image FROM blog b LEFT JOIN blog_image i ON b.blog_id = i.blog_id WHERE b.blog_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                BlogCategory category = categoryDAO.getCategoryById(rs.getInt("blog_category_id"));
                Blog blog = new Blog(
                        rs.getInt("blog_id"),
                        rs.getString("blog_title"),
                        rs.getString("blog_description"),
                        rs.getTimestamp("date_published"),
                        category
                );
                blog.setImageUrl(rs.getString("blog_image"));
                return blog;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int addBlog(Blog blog) {
        String sql = "INSERT INTO blog (blog_title, blog_description, blog_category_id, date_published) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, blog.getBlogTitle());
            ps.setString(2, blog.getBlogDescription());
            ps.setInt(3, blog.getBlogCategory().getId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

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

    public void deleteBlog(int id) {
        String sql = "DELETE FROM blog WHERE blog_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBlogImage(int blogId, String imagePath) {
        String sql = "INSERT INTO blog_image (blog_id, blog_image) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, blogId);
            ps.setString(2, imagePath);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBlogImage(int blogId, String imagePath) {
        String sql = "REPLACE INTO blog_image (blog_id, blog_image) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, blogId);
            ps.setString(2, imagePath);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getImagePathByBlogId(int blogId) {
        String sql = "SELECT blog_image FROM blog_image WHERE blog_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, blogId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("blog_image");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Blog> searchBlogs(int categoryId, String keyword, int pageIndex, int pageSize) {
        List<Blog> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT b.*, i.blog_image FROM blog b LEFT JOIN blog_image i ON b.blog_id = i.blog_id WHERE 1=1"
        );

        boolean hasCategory = (categoryId != -1);
        boolean hasKeyword = (keyword != null && !keyword.trim().isEmpty());

        if (hasCategory) sql.append(" AND b.blog_category_id = ?");
        if (hasKeyword) sql.append(" AND LOWER(b.blog_title) LIKE ?");

        sql.append(" ORDER BY b.date_published DESC LIMIT ? OFFSET ?");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (hasCategory) ps.setInt(paramIndex++, categoryId);
            if (hasKeyword) ps.setString(paramIndex++, "%" + keyword.trim().toLowerCase() + "%");

            ps.setInt(paramIndex++, pageSize);
            ps.setInt(paramIndex, (pageIndex - 1) * pageSize);

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

    public int countSearchBlogs(int categoryId, String keyword) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM blog WHERE 1=1");

        boolean hasCategory = (categoryId != -1);
        boolean hasKeyword = (keyword != null && !keyword.trim().isEmpty());

        if (hasCategory) sql.append(" AND blog_category_id = ?");
        if (hasKeyword) sql.append(" AND LOWER(blog_title) LIKE ?");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (hasCategory) ps.setInt(paramIndex++, categoryId);
            if (hasKeyword) ps.setString(paramIndex++, "%" + keyword.trim().toLowerCase() + "%");

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
