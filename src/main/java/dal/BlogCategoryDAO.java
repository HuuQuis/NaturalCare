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

    // Lấy category theo ID
    public BlogCategory getCategoryById(int id) {
        String sql = "SELECT * FROM blog_category WHERE blog_category_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new BlogCategory(
                        rs.getInt("blog_category_id"),
                        rs.getString("blog_category_name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
