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
                        rs.getString("blog_category_name")
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
                BlogCategory cat = new BlogCategory();
                cat.setId(rs.getInt("blog_category_id"));
                cat.setName(rs.getString("blog_category_name"));
                return cat;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<BlogCategory> getCategoriesByPage(int pageIndex, int pageSize) {
        List<BlogCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM blog_category ORDER BY blog_category_id DESC LIMIT ? OFFSET ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, pageSize);
            ps.setInt(2, (pageIndex - 1) * pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new BlogCategory(rs.getInt("blog_category_id"), rs.getString("blog_category_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
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

    public void addCategory(String name) {
        String sql = "INSERT INTO blog_category (blog_category_name) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCategory(int id, String name) {
        String sql = "UPDATE blog_category SET blog_category_name = ? WHERE blog_category_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, id);
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

    public int getTotalCategoryCount() {
        String sql = "SELECT COUNT(*) FROM blog_category";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean hasBlogDependency(int categoryId) {
        String sql = "SELECT 1 FROM blog WHERE blog_category_id = ? LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Nếu có ít nhất 1 dòng -> trả về true
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
