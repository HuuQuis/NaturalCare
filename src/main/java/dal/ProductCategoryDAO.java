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
                        rs.getString("product_category_name"),
                        rs.getBoolean("status")
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

    public void updateProductCategory(int id, String name, boolean status) {
        String sql = "UPDATE product_category SET product_category_name = ?, status = ? WHERE product_category_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setBoolean(2, status);
            ps.setInt(3, id);
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
                            rs.getString("product_category_name"),
                            rs.getBoolean("status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ProductCategory> getCategoriesByPage(int pageIndex, int pageSize, String keyword, String sort, String statusFilter) {
        List<ProductCategory> list = new ArrayList<>();
        int offset = (pageIndex - 1) * pageSize;

        StringBuilder sqlBuilder = new StringBuilder(
                "SELECT * FROM product_category WHERE 1=1"
        );

        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sqlBuilder.append(" AND LOWER(product_category_name) LIKE ?");
            params.add("%" + keyword.toLowerCase() + "%");
        }

        if ("true".equalsIgnoreCase(statusFilter) || "false".equalsIgnoreCase(statusFilter)) {
            sqlBuilder.append(" AND status = ?");
            params.add(Boolean.parseBoolean(statusFilter));
        }

        // Sort
        if ("asc".equalsIgnoreCase(sort)) {
            sqlBuilder.append(" ORDER BY product_category_name ASC");
        } else if ("desc".equalsIgnoreCase(sort)) {
            sqlBuilder.append(" ORDER BY product_category_name DESC");
        } else {
            // Mặc định: mới nhất lên đầu
            sqlBuilder.append(" ORDER BY product_category_id DESC");
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
                    list.add(new ProductCategory(
                            rs.getInt("product_category_id"),
                            rs.getString("product_category_name"),
                            rs.getBoolean("status")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public int countTotalCategories(String keyword, String statusFilter) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM product_category WHERE LOWER(product_category_name) LIKE ?");
        if ("true".equalsIgnoreCase(statusFilter) || "false".equalsIgnoreCase(statusFilter)) {
            sql.append(" AND status = ").append(statusFilter);
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            ps.setString(1, "%" + (keyword != null ? keyword.toLowerCase() : "") + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isCategoryNameExists(String name) {
        String sql = "SELECT 1 FROM product_category WHERE LOWER(product_category_name) = LOWER(?)";
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