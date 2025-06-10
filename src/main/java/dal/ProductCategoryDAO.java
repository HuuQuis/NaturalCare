package dal;

import model.ProductCategory;
import java.sql.*;
import java.util.*;

public class ProductCategoryDAO extends DBContext {

    public List<ProductCategory> getAllProductCategories() {
        List<ProductCategory> list = new ArrayList<>();
        sql = "SELECT * FROM product_category WHERE status = TRUE";

        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                ProductCategory category = new ProductCategory(
                        rs.getInt("product_category_id"),
                        rs.getString("product_category_name")
                );
                list.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addProductCategory(String name) {
        sql = "INSERT INTO product_category (product_category_name, status) VALUES (?, TRUE)";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, name);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProductCategory(int id, String name) {
        sql = "UPDATE product_category SET product_category_name = ? WHERE product_category_id = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, name);
            stm.setInt(2, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProductCategory(int id) {
        sql = "DELETE FROM product_category WHERE product_category_id = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ProductCategory getById(int id) {
        sql = "SELECT * FROM product_category WHERE product_category_id = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            if (rs.next()) {
                return new ProductCategory(
                        rs.getInt("product_category_id"),
                        rs.getString("product_category_name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy theo trang (chỉ lấy category có status = TRUE)
    public List<ProductCategory> getCategoriesByPage(int pageIndex, int pageSize) {
        List<ProductCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM product_category WHERE status = TRUE ORDER BY product_category_id LIMIT ? OFFSET ?";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            int offset = (pageIndex - 1) * pageSize;
            stm.setInt(1, pageSize);
            stm.setInt(2, offset);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                ProductCategory c = new ProductCategory();
                c.setId(rs.getInt("product_category_id"));
                c.setName(rs.getString("product_category_name"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Đếm tổng số category có status = TRUE
    public int countTotalCategories() {
        String sql = "SELECT COUNT(*) FROM product_category WHERE status = TRUE";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isCategoryNameExists(String name) {
        String sql = "SELECT 1 FROM product_category WHERE LOWER(product_category_name) = LOWER(?) AND status = TRUE";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isCategoryNameExistsForOtherId(String name, int excludeId) {
        String sql = "SELECT 1 FROM product_category WHERE LOWER(product_category_name) = LOWER(?) AND product_category_id != ? AND status = TRUE";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, name);
            stm.setInt(2, excludeId);
            ResultSet rs = stm.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasDependency(int categoryId) throws SQLException {
        // Check subcategories
        String subQuery = "SELECT COUNT(*) FROM sub_product_category WHERE product_category_id = ?";
        PreparedStatement ps1 = connection.prepareStatement(subQuery);
        ps1.setInt(1, categoryId);
        ResultSet rs1 = ps1.executeQuery();
        if (rs1.next() && rs1.getInt(1) > 0) return true;

        // Check products via subcategories
        String prodQuery = "SELECT COUNT(*) FROM product p " +
                "JOIN sub_product_category s ON p.sub_product_category_id = s.sub_product_category_id " +
                "WHERE s.product_category_id = ?";
        PreparedStatement ps2 = connection.prepareStatement(prodQuery);
        ps2.setInt(1, categoryId);
        ResultSet rs2 = ps2.executeQuery();
        if (rs2.next() && rs2.getInt(1) > 0) return true;

        return false;
    }

    public void hideCategory(int categoryId) throws SQLException {
        String sql = "UPDATE product_category SET status = FALSE WHERE product_category_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, categoryId);
        ps.executeUpdate();
    }
}
