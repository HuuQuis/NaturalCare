package dal;

import model.SubProductCategory;
import java.sql.*;
import java.util.*;

public class SubProductCategoryDAO extends DBContext {

    public List<SubProductCategory> getSubCategoriesByCategoryId(int categoryId) {
        List<SubProductCategory> list = new ArrayList<>();
        sql = "SELECT * FROM sub_product_category WHERE product_category_id = ? AND status = TRUE";

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, categoryId);
            rs = stm.executeQuery();

            while (rs.next()) {
                SubProductCategory subCategory = new SubProductCategory(
                        rs.getInt("sub_product_category_id"),
                        rs.getString("sub_product_category_name"),
                        rs.getInt("product_category_id")
                );
                list.add(subCategory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<SubProductCategory> getAllSubProductCategories() {
        List<SubProductCategory> list = new ArrayList<>();
        sql = "SELECT * FROM sub_product_category WHERE status = TRUE";

        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                SubProductCategory subCategory = new SubProductCategory(
                        rs.getInt("sub_product_category_id"),
                        rs.getString("sub_product_category_name"),
                        rs.getInt("product_category_id")
                );
                list.add(subCategory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addSubCategory(String name, int categoryId) {
        String sql = "INSERT INTO sub_product_category (sub_product_category_name, product_category_id, status) VALUES (?, ?, TRUE)";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, name);
            stm.setInt(2, categoryId);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSubCategory(int id, String name) {
        String sql = "UPDATE sub_product_category SET sub_product_category_name = ? WHERE sub_product_category_id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, name);
            stm.setInt(2, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSubCategory(int id) {
        String sql = "DELETE FROM sub_product_category WHERE sub_product_category_id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void hideSubCategory(int id) throws SQLException {
        String sql = "UPDATE sub_product_category SET status = FALSE WHERE sub_product_category_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public boolean hasProductDependency(int subCategoryId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM product WHERE sub_product_category_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, subCategoryId);
        ResultSet rs = ps.executeQuery();
        return rs.next() && rs.getInt(1) > 0;
    }

    public SubProductCategory getById(int id) {
        String sql = "SELECT * FROM sub_product_category WHERE sub_product_category_id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return new SubProductCategory(
                        rs.getInt("sub_product_category_id"),
                        rs.getString("sub_product_category_name"),
                        rs.getInt("product_category_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isSubNameExists(String name, int categoryId) {
        String sql = "SELECT 1 FROM sub_product_category WHERE sub_product_category_name = ? AND product_category_id = ? AND status = TRUE";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, name);
            stm.setInt(2, categoryId);
            ResultSet rs = stm.executeQuery();
            return rs.next(); // tồn tại → true
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isSubNameExistsInAnyCategory(String name) {
        String sql = "SELECT 1 FROM sub_product_category WHERE LOWER(sub_product_category_name) = LOWER(?) AND status = TRUE";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isSubNameExistsForOtherId(String name, int categoryId, int excludeId) {
        String sql = "SELECT 1 FROM sub_product_category WHERE LOWER(sub_product_category_name) = LOWER(?) AND product_category_id = ? AND sub_product_category_id != ? AND status = TRUE";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, name);
            stm.setInt(2, categoryId);
            stm.setInt(3, excludeId);
            ResultSet rs = stm.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
