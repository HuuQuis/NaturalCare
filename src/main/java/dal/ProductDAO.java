package dal;

import model.Product;
import model.ProductVariation;

import java.sql.SQLException;
import java.util.*;

public class ProductDAO extends DBContext {

    public List<Product> getProductsByCategoryId(int categoryId, int pageIndex) {
        int offset = (pageIndex - 1) * 6;
        int limit = 6;
        sql = "SELECT p.*, MIN(pv.product_image) AS product_image, MIN(pv.price) AS min_price " +
                "FROM product p " +
                "INNER JOIN sub_product_category s ON p.sub_product_category_id = s.sub_product_category_id " +
                "INNER JOIN product_category pc ON s.product_category_id = pc.product_category_id " +
                "LEFT JOIN product_variation pv ON pv.product_id = p.product_id " +
                "WHERE pc.product_category_id = ? " +
                "GROUP BY p.product_id " +
                "ORDER BY p.product_id " +
                "LIMIT ?, ?";
        return fetchProductsByQuery(sql, categoryId, offset, limit);
    }

    public List<Product> getProductsBySubCategoryId(int subCategoryId, int pageIndex) {
        int offset = (pageIndex - 1) * 6;
        int limit = 6;
        sql = "SELECT p.*, MIN(pv.product_image) AS product_image, MIN(pv.price) AS min_price " +
                "FROM product p " +
                "INNER JOIN sub_product_category s ON p.sub_product_category_id = s.sub_product_category_id " +
                "INNER JOIN product_category pc ON s.product_category_id = pc.product_category_id " +
                "LEFT JOIN product_variation pv ON pv.product_id = p.product_id " +
                "WHERE p.sub_product_category_id = ? " +
                "GROUP BY p.product_id " +
                "ORDER BY p.product_id " +
                "LIMIT ?, ?";
        return fetchProductsByQuery(sql, subCategoryId, offset, limit);
    }

    public Product getProductById(int productId) {
        String sql = "SELECT p.*, pv.variation_id, pv.product_image, pv.color_id, pv.size_id, pv.price, pv.qty_in_stock, pv.sold, " +
                "c.color_name, s.size_name " +
                "FROM product p " +
                "LEFT JOIN product_variation pv ON pv.product_id = p.product_id " +
                "LEFT JOIN color c ON pv.color_id = c.color_id " +
                "LEFT JOIN size s ON pv.size_id = s.size_id " +
                "WHERE p.product_id = ?";

        Product product = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, productId);
            rs = stm.executeQuery();

            while (rs.next()) {
                if (product == null) {
                    product = new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getString("product_short_description"),
                            rs.getString("product_information"),
                            rs.getString("product_guideline"),
                            rs.getInt("sub_product_category_id")
                    );
                }

                // Handle variations
                int variationId = rs.getInt("variation_id");
                String imageUrl = rs.getString("product_image");
                int colorId = rs.getInt("color_id");
                int sizeId = rs.getInt("size_id");
                String colorName = rs.getString("color_name");
                String sizeName = rs.getString("size_name");

                if (variationId > 0) {
                    ProductVariation variation = new ProductVariation(
                            variationId,
                            imageUrl,
                            colorId,
                            sizeId,
                            rs.getInt("price"),
                            rs.getInt("qty_in_stock"),
                            rs.getInt("sold")
                    );
                    variation.setColorName(colorName);
                    variation.setSizeName(sizeName);
                    product.addVariation(variation);
                    product.addImageUrl(imageUrl);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }



    public List<Product> getAllProducts() {
        sql = "SELECT * FROM product";
        List<Product> products = new ArrayList<>();
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            products = extractProductsFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return products;
    }

    //get products by page
    public List<Product> getProductsByPage(int pageIndex, int pageSize) {
        List<Product> products = new ArrayList<>();
        sql = "SELECT * FROM product ORDER BY product_id LIMIT ? OFFSET ?";

        try {
            stm = connection.prepareStatement(sql);
            int offset = (pageIndex - 1) * pageSize;
            stm.setInt(1, pageSize);
            stm.setInt(2, offset);
            rs = stm.executeQuery();
            products = extractProductsFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return products;
    }

    //get all variations of a product by product ID
    public List<ProductVariation> getProductVariationsByProductId(int productId) {
        String sql = "SELECT * FROM product_variation WHERE product_id = ?";

        List<ProductVariation> variations = new ArrayList<>();
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, productId);
            rs = stm.executeQuery();

            while (rs.next()) {
                ProductVariation variation = new ProductVariation(
                        rs.getInt("variation_id"),
                        rs.getString("product_image"),
                        rs.getInt("color_id"),
                        rs.getInt("size_id"),
                        rs.getInt("price"),
                        rs.getInt("qty_in_stock"),
                        rs.getInt("sold")
                );
                variations.add(variation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return variations;
    }



    public void addProduct(Product product) {
        sql = "INSERT INTO product (product_name, product_short_description, product_information, product_guideline, sub_product_category_id) VALUES (?, ?, ?, ?, ?)";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, product.getName());
            stm.setString(2, product.getDescription());
            stm.setString(3, product.getInformation());
            stm.setString(4, product.getGuideline());
            stm.setInt(5, product.getSubProductCategoryId());
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) {
        sql = "DELETE FROM product WHERE product_id = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(Product product) {
        sql = "UPDATE product SET product_name = ?, product_short_description = ?, product_information = ?, product_guideline = ?, sub_product_category_id = ? WHERE product_id = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, product.getName());
            stm.setString(2, product.getDescription());
            stm.setString(3, product.getInformation());
            stm.setString(4, product.getGuideline());
            stm.setInt(5, product.getSubProductCategoryId());
            stm.setInt(6, product.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get total number of products by category and subcategory
    public int countTotalProducts() {
        sql = "SELECT COUNT(*) FROM product";
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int getTotalProductsCountByCategory(int categoryId) {
        sql = "SELECT COUNT(*) \n" +
                "        FROM product p\n" +
                "        JOIN sub_product_category spc ON p.sub_product_category_id = spc.sub_product_category_id\n" +
                "        WHERE spc.product_category_id = ?" ;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, categoryId);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int getTotalProductsCountBySubCategory(int subCategoryId) {
        sql = "SELECT COUNT(*) FROM product WHERE sub_product_category_id = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, subCategoryId);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

   public boolean isProductNameExists(String name, int excludeId) {
       sql = "SELECT COUNT(*) FROM product WHERE product_name = ? AND product_id != ?";
       try {
           stm = connection.prepareStatement(sql);
           stm.setString(1, name);
           stm.setInt(2, excludeId);
           rs = stm.executeQuery();
           if (rs.next()) {
               return rs.getInt(1) > 0;
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
         return false;
   }

    private List<Product> fetchProductsByQuery(String sql, int id, int offset, int limit) {
        Map<Integer, Product> productMap = new HashMap<>();
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.setInt(2, offset);
            stm.setInt(3, limit);
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
                            rs.getInt("sub_product_category_id"),
                            rs.getInt("min_price")
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
