package dal;

import model.Product;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends DBContext {

    public List<Product> getProductsByCategoryId(int categoryId) {
        String sql = "SELECT p.* FROM product p " +
                "INNER JOIN sub_product_category s ON p.sub_product_category_id = s.sub_product_category_id " +
                "WHERE s.product_category_id = ?";
        return getProductsByQuery(sql, categoryId);
    }

    public List<Product> getProductsBySubCategoryId(int subCategoryId) {
        String sql = "SELECT * FROM product WHERE sub_product_category_id = ?";
        return getProductsByQuery(sql, subCategoryId);
    }

    // This method retrieves all products without any filtering.
    private List<Product> getProductsByQuery(String sql, int param) {
        List<Product> products = new ArrayList<>();
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, param);
            rs = stm.executeQuery();
            products = extractProductsFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // This method extracts products from the ResultSet and returns a list of Product objects.
    private List<Product> extractProductsFromResultSet(java.sql.ResultSet rs) throws SQLException {
        List<Product> products = new ArrayList<>();
        while (rs.next()) {
            products.add(new Product(
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getString("product_short_description"),
                    rs.getString("product_information"),
                    rs.getString("product_guideline"),
                    rs.getInt("sub_product_category_id")
            ));
        }
        return products;
    }

    public List<Product> searchProductsByText(String keyword) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE product_name LIKE ?";

        try {
            stm = connection.prepareStatement(sql);
            String searchPattern = "%" + keyword + "%";
            stm.setString(1, searchPattern);
            rs = stm.executeQuery();
            products = extractProductsFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}

