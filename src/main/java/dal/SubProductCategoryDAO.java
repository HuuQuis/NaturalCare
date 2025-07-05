package dal;

import model.SubProductCategory;
import java.sql.*;
import java.util.*;

public class SubProductCategoryDAO extends DBContext {

    public List<SubProductCategory> getSubCategoriesByCategoryId(int categoryId) {
        List<SubProductCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM sub_product_category WHERE product_category_id = ? AND status = TRUE";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, categoryId);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    SubProductCategory subCategory = new SubProductCategory(
                            rs.getInt("sub_product_category_id"),
                            rs.getString("sub_product_category_name"),
                            rs.getInt("product_category_id")
                    );
                    list.add(subCategory);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<SubProductCategory> getAllSubProductCategories() {
        List<SubProductCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM sub_product_category WHERE status = TRUE";

        try (PreparedStatement stm = connection.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {
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

    public void updateSubCategory(int id, String name, boolean status) {
        String sql = "UPDATE sub_product_category SET sub_product_category_name = ?, status = ? WHERE sub_product_category_id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, name);
            stm.setBoolean(2, status);
            stm.setInt(3, id);
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

    public void hideSubCategory(int id) {
        String sql = "UPDATE sub_product_category SET status = FALSE WHERE sub_product_category_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean hasProductDependency(int subCategoryId) {
        String sql = "SELECT COUNT(*) FROM product WHERE sub_product_category_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, subCategoryId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public SubProductCategory getById(int id) {
        String sql = "SELECT * FROM sub_product_category WHERE sub_product_category_id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return new SubProductCategory(
                            rs.getInt("sub_product_category_id"),
                            rs.getString("sub_product_category_name"),
                            rs.getInt("product_category_id")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isSubNameExists(String name, int categoryId) {
        String sql = "SELECT 1 FROM sub_product_category WHERE sub_product_category_name = ? AND product_category_id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, name);
            stm.setInt(2, categoryId);
            try (ResultSet rs = stm.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isSubNameExistsInAnyCategory(String name) {
        String sql = "SELECT 1 FROM sub_product_category WHERE LOWER(sub_product_category_name) = LOWER(?)";
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

    public boolean isSubNameExistsForOtherId(String name, int categoryId, int excludeId) {
        String sql = "SELECT 1 FROM sub_product_category WHERE LOWER(sub_product_category_name) = LOWER(?) AND product_category_id = ? AND sub_product_category_id != ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, name);
            stm.setInt(2, categoryId);
            stm.setInt(3, excludeId);
            try (ResultSet rs = stm.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<SubProductCategory> getFilteredSubcategoriesByPage(String keyword, Integer categoryId, int pageIndex, int pageSize, String sort, String statusFilter) {
        List<SubProductCategory> list = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder(
                "SELECT s.sub_product_category_id, s.sub_product_category_name, s.product_category_id, c.product_category_name, s.status " +
                        "FROM sub_product_category s " +
                        "JOIN product_category c ON s.product_category_id = c.product_category_id " +
                        "WHERE 1=1"
        );

        List<Object> params = new ArrayList<>();
        if (categoryId != null) {
            sqlBuilder.append(" AND s.product_category_id = ?");
            params.add(categoryId);
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            sqlBuilder.append(" AND LOWER(s.sub_product_category_name) LIKE ?");
            params.add("%" + keyword.toLowerCase() + "%");
        }
        if ("true".equalsIgnoreCase(statusFilter) || "false".equalsIgnoreCase(statusFilter)) {
            sqlBuilder.append(" AND s.status = ?");
            params.add(Boolean.parseBoolean(statusFilter));
        }

        if ("asc".equalsIgnoreCase(sort)) {
            sqlBuilder.append(" ORDER BY s.sub_product_category_name ASC ");
        } else if ("desc".equalsIgnoreCase(sort)) {
            sqlBuilder.append(" ORDER BY s.sub_product_category_name DESC ");
        } else {
            // Mặc định: sắp xếp theo ID mới nhất (hiển thị bản mới nhất lên đầu)
            sqlBuilder.append(" ORDER BY s.sub_product_category_id DESC ");
        }

        sqlBuilder.append(" LIMIT ? OFFSET ?");

        int offset = (pageIndex - 1) * pageSize;
        params.add(pageSize);
        params.add(offset);

        try (PreparedStatement stm = connection.prepareStatement(sqlBuilder.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stm.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    SubProductCategory s = new SubProductCategory();
                    s.setId(rs.getInt("sub_product_category_id"));
                    s.setName(rs.getString("sub_product_category_name"));
                    s.setProductCategoryId(rs.getInt("product_category_id"));
                    s.setCategoryName(rs.getString("product_category_name"));
                    s.setStatus(rs.getBoolean("status"));
                    list.add(s);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countFilteredSubcategories(String keyword, Integer categoryId, String statusFilter) {
        StringBuilder sqlBuilder = new StringBuilder(
                "SELECT COUNT(*) FROM sub_product_category s WHERE 1=1"
        );

        List<Object> params = new ArrayList<>();
        if (categoryId != null) {
            sqlBuilder.append(" AND s.product_category_id = ?");
            params.add(categoryId);
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            sqlBuilder.append(" AND LOWER(s.sub_product_category_name) LIKE ?");
            params.add("%" + keyword.toLowerCase() + "%");
        }
        if ("true".equalsIgnoreCase(statusFilter) || "false".equalsIgnoreCase(statusFilter)) {
            sqlBuilder.append(" AND s.status = ?");
            params.add(Boolean.parseBoolean(statusFilter));
        }

        try (PreparedStatement stm = connection.prepareStatement(sqlBuilder.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stm.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public SubProductCategory getSubCategoryById(int id) {
        sql = "SELECT spc.sub_product_category_id, spc.sub_product_category_name, " +
                "spc.product_category_id, pc.product_category_name, spc.status " +
                "FROM sub_product_category spc " +
                "JOIN product_category pc ON spc.product_category_id = pc.product_category_id " +
                "WHERE spc.sub_product_category_id = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            if (rs.next()) {
                return new SubProductCategory(
                        rs.getInt("sub_product_category_id"),
                        rs.getString("sub_product_category_name"),
                        rs.getInt("product_category_id"),
                        rs.getString("product_category_name"),
                        rs.getBoolean("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
