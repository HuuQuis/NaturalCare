package dal;

import model.Product;
import java.sql.SQLException;
import java.util.*;

public class ProductDAO extends DBContext {

    public List<Product> getProductsByCategoryId(int categoryId) {
        String sql = "SELECT p.*, pv.product_image " +
                "FROM product p " +
                "INNER JOIN sub_product_category s ON p.sub_product_category_id = s.sub_product_category_id " +
                "INNER JOIN product_category pc ON s.product_category_id = pc.product_category_id " +
                "LEFT JOIN product_variation pv ON pv.product_id = p.product_id " +
                "WHERE pc.product_category_id = ?";
        return fetchProductsByQuery(sql, categoryId);
    }

    public List<Product> getProductsBySubCategoryId(int subCategoryId) {
        String sql = "SELECT p.*, pv.product_image " +
                "FROM product p " +
                "INNER JOIN sub_product_category s ON p.sub_product_category_id = s.sub_product_category_id " +
                "INNER JOIN product_category pc ON s.product_category_id = pc.product_category_id " +
                "LEFT JOIN product_variation pv ON pv.product_id = p.product_id " +
                "WHERE p.sub_product_category_id = ?";
        return fetchProductsByQuery(sql, subCategoryId);
    }

    private List<Product> fetchProductsByQuery(String sql, int id) {
        Map<Integer, Product> productMap = new HashMap<>();
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String imageUrl = rs.getString("product_image");

                if (!productMap.containsKey(productId)) {
                    Product product = new Product(
                            productId,
                            rs.getString("product_name"),
                            rs.getString("product_short_description"),
                            rs.getString("product_information"),
                            rs.getString("product_guideline"),
                            null,
                            rs.getInt("sub_product_category_id")
                    );
                    productMap.put(productId, product);
                }

                if (imageUrl != null) {
                    productMap.get(productId).addImageUrl(imageUrl);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(productMap.values());
    }


    public List<Product> searchProductsByText(String keyword) {
        List<Product> products = new ArrayList<>();
        sql = "SELECT * FROM product WHERE product_name LIKE ?";

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
}
