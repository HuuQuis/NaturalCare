package dal;

import model.ProductCategory;
import java.sql.*;
import java.util.*;

public class ProductCategoryDAO extends DBContext {

    public List<ProductCategory> getAllProductCategories() {
        List<ProductCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM product_category WHERE status = TRUE";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new ProductCategory(
                        rs.getInt("product_category_id"),
                        rs.getString("product_category_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void addProductCategory(String name) {
        String sql = "INSERT INTO product_category (product_category_name, status) VALUES (?, TRUE)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProductCategory(int id, String name) {
        String sql = "UPDATE product_category SET product_category_name = ? WHERE product_category_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProductCategory(int id) {
        String sql = "DELETE FROM product_category WHERE product_category_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ProductCategory getById(int id) {
        String sql = "SELECT * FROM product_category WHERE product_category_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ProductCategory(
                            rs.getInt("product_category_id"),
                            rs.getString("product_category_name")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ProductCategory> getCategoriesByPage(int pageIndex, int pageSize) {
        List<ProductCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM product_category WHERE status = TRUE ORDER BY product_category_id DESC LIMIT ? OFFSET ?";
        int offset = (pageIndex - 1) * pageSize;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, pageSize);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ProductCategory(
                            rs.getInt("product_category_id"),
                            rs.getString("product_category_name")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public int countTotalCategories() {
        String sql = "SELECT COUNT(*) FROM product_category WHERE status = TRUE";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isCategoryNameExists(String name) {
        String sql = "SELECT 1 FROM product_category WHERE LOWER(product_category_name) = LOWER(?) AND status = TRUE";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isCategoryNameExistsForOtherId(String name, int excludeId) {
        String sql = "SELECT 1 FROM product_category WHERE LOWER(product_category_name) = LOWER(?) AND product_category_id != ? AND status = TRUE";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, excludeId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean hasDependency(int categoryId) {
        String subQuery = "SELECT COUNT(*) FROM sub_product_category WHERE product_category_id = ?";
        String prodQuery = "SELECT COUNT(*) FROM product p JOIN sub_product_category s ON p.sub_product_category_id = s.sub_product_category_id WHERE s.product_category_id = ?";

        try (PreparedStatement ps1 = connection.prepareStatement(subQuery)) {
            ps1.setInt(1, categoryId);
            try (ResultSet rs1 = ps1.executeQuery()) {
                if (rs1.next() && rs1.getInt(1) > 0) return true;
            }

            try (PreparedStatement ps2 = connection.prepareStatement(prodQuery)) {
                ps2.setInt(1, categoryId);
                try (ResultSet rs2 = ps2.executeQuery()) {
                    if (rs2.next() && rs2.getInt(1) > 0) return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void hideCategory(int categoryId) {
        String sql = "UPDATE product_category SET status = FALSE WHERE product_category_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
