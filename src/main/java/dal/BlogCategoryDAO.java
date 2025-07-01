package dal;

import model.BlogCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BlogCategoryDAO extends DBContext {

    public List<BlogCategory> getAllBlogCategories() {
        List<BlogCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM blog_category";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new BlogCategory(
                        rs.getInt("blog_category_id"),
                        rs.getString("blog_category_name"),
                        rs.getBoolean("status")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public BlogCategory getCategoryById(int id) {
        String sql = "SELECT * FROM blog_category WHERE blog_category_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new BlogCategory(
                        rs.getInt("blog_category_id"),
                        rs.getString("blog_category_name"),
                        rs.getBoolean("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<BlogCategory> getCategoriesByPage(int pageIndex, int pageSize, String keyword, String sort, String statusFilter) {
        List<BlogCategory> list = new ArrayList<>();
        int offset = (pageIndex - 1) * pageSize;

        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM blog_category WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sqlBuilder.append(" AND LOWER(blog_category_name) LIKE ?");
            params.add("%" + keyword.toLowerCase() + "%");
        }

        if ("true".equalsIgnoreCase(statusFilter) || "false".equalsIgnoreCase(statusFilter)) {
            sqlBuilder.append(" AND status = ?");
            params.add(Boolean.parseBoolean(statusFilter));
        }

        // Sort theo tên
        if ("asc".equalsIgnoreCase(sort)) {
            sqlBuilder.append(" ORDER BY blog_category_name ASC");
        } else if ("desc".equalsIgnoreCase(sort)) {
            sqlBuilder.append(" ORDER BY blog_category_name DESC");
        } else {
            sqlBuilder.append(" ORDER BY blog_category_id DESC"); // default sort
        }

        sqlBuilder.append(" LIMIT ? OFFSET ?");
        params.add(pageSize);
        params.add(offset);

        try (PreparedStatement ps = connection.prepareStatement(sqlBuilder.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BlogCategory cat = new BlogCategory(
                            rs.getInt("blog_category_id"),
                            rs.getString("blog_category_name"),
                            rs.getBoolean("status")
                    );
                    cat.setBlogCount(countBlogsInCategory(cat.getId()));
                    list.add(cat);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public int countTotalFilteredBlogCategories(String keyword, String statusFilter) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(*) FROM blog_category WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sqlBuilder.append(" AND LOWER(blog_category_name) LIKE ?");
            params.add("%" + keyword.toLowerCase() + "%");
        }

        if ("true".equalsIgnoreCase(statusFilter) || "false".equalsIgnoreCase(statusFilter)) {
            sqlBuilder.append(" AND status = ?");
            params.add(Boolean.parseBoolean(statusFilter));
        }

        try (PreparedStatement ps = connection.prepareStatement(sqlBuilder.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public boolean isCategoryNameExists(String name) {
        String sql = "SELECT 1 FROM blog_category WHERE blog_category_name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isCategoryNameExistsForOtherId(String name, int id) {
        String sql = "SELECT 1 FROM blog_category WHERE blog_category_name = ? AND blog_category_id <> ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addCategory(String name, boolean status) {
        String sql = "INSERT INTO blog_category (blog_category_name, status) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setBoolean(2, status);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCategory(int id, String name, boolean status) {
        String sql = "UPDATE blog_category SET blog_category_name = ?, status = ? WHERE blog_category_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setBoolean(2, status);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCategory(int id) {
        String sql = "DELETE FROM blog_category WHERE blog_category_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean hasBlogDependency(int categoryId) {
        String sql = "SELECT 1 FROM blog WHERE blog_category_id = ? LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int countBlogsInCategory(int categoryId) {
        String sql = "SELECT COUNT(*) FROM blog WHERE blog_category_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
