package dal;

import model.Product;
import model.ProductVariation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ProductDAO extends DBContext {

    public Product getProductById(int productId) {
        sql = "SELECT p.*, pv.variation_id, pv.product_image, pv.color_id, pv.size_id, pv.price, pv.sell_price, pv.qty_in_stock, pv.sold, " +
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
                            rs.getInt("sell_price"),
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
        String sql = "SELECT pv.*, c.color_name, s.size_name " +
                     "FROM product_variation pv " +
                     "LEFT JOIN color c ON pv.color_id = c.color_id " +
                     "LEFT JOIN size s ON pv.size_id = s.size_id " +
                     "WHERE pv.product_id = ?";

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
                        rs.getInt("sell_price"),
                        rs.getInt("qty_in_stock"),
                        rs.getInt("sold")
                );
                variation.setColorName(rs.getString("color_name"));
                variation.setSizeName(rs.getString("size_name"));
                variations.add(variation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return variations;
    }

    // get paginated variations of a product by product ID
    public List<ProductVariation> getProductVariationsByProductIdPaged(int productId, int page, int pageSize) {
        String sql = "SELECT pv.*, c.color_name, s.size_name " +
                     "FROM product_variation pv " +
                     "LEFT JOIN color c ON pv.color_id = c.color_id " +
                     "LEFT JOIN size s ON pv.size_id = s.size_id " +
                     "WHERE pv.product_id = ? " +
                     "ORDER BY pv.variation_id " +
                     "LIMIT ? OFFSET ?";
        List<ProductVariation> variations = new ArrayList<>();
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, productId);
            stm.setInt(2, pageSize);
            stm.setInt(3, (page - 1) * pageSize);
            rs = stm.executeQuery();

            while (rs.next()) {
                ProductVariation variation = new ProductVariation(
                        rs.getInt("variation_id"),
                        rs.getString("product_image"),
                        rs.getInt("color_id"),
                        rs.getInt("size_id"),
                        rs.getInt("price"),
                        rs.getInt("sell_price"),
                        rs.getInt("qty_in_stock"),
                        rs.getInt("sold")
                );
                variation.setColorName(rs.getString("color_name"));
                variation.setSizeName(rs.getString("size_name"));
                variations.add(variation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return variations;
    }

    // get total number of variants for a product
    public int countProductVariants(int productId) {
        String sql = "SELECT COUNT(*) FROM product_variation WHERE product_id = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, productId);
            rs = stm.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
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

    public List<Product> getProductsByCategoryIdSorted(int categoryId, int pageIndex, int pageSize, String sort) {
        int offset = (pageIndex - 1) * pageSize;
        String orderBy = getOrderByClause(sort);

        sql = "SELECT p.*, " +
                "       (SELECT MIN(pv1.product_image) FROM product_variation pv1 WHERE pv1.product_id = p.product_id) AS product_image, " +
                "       COALESCE((SELECT MIN(pv2.sell_price) FROM product_variation pv2 WHERE pv2.product_id = p.product_id), 0) AS min_price " +
                "FROM product p " +
                "INNER JOIN sub_product_category s ON p.sub_product_category_id = s.sub_product_category_id " +
                "INNER JOIN product_category pc ON s.product_category_id = pc.product_category_id " +
                "WHERE pc.product_category_id = ? " +
                orderBy + " LIMIT ?, ?";

        return fetchProductsByQuery(sql, categoryId, offset, pageSize);
    }

    public List<Product> getProductsBySubCategoryIdSorted(int subCategoryId, int pageIndex, int pageSize, String sort) {
        int offset = (pageIndex - 1) * pageSize;
        String orderBy = getOrderByClause(sort);

        sql = "SELECT p.*, " +
                "       (SELECT MIN(pv1.product_image) FROM product_variation pv1 WHERE pv1.product_id = p.product_id) AS product_image, " +
                "       COALESCE((SELECT MIN(pv2.sell_price) FROM product_variation pv2 WHERE pv2.product_id = p.product_id), 0) AS min_price " +
                "FROM product p " +
                "WHERE p.sub_product_category_id = ? " +
                orderBy + " LIMIT ?, ?";

        return fetchProductsByQuery(sql, subCategoryId, offset, pageSize);
    }

    private String getOrderByClause(String sort) {
        if (sort == null) return "ORDER BY p.product_id";

        switch (sort) {
            case "name-asc":
                return "ORDER BY p.product_name ASC";
            case "name-desc":
                return "ORDER BY p.product_name DESC";
            case "price-asc":
                return "ORDER BY min_price ASC";
            case "price-desc":
                return "ORDER BY min_price DESC";
            default:
                return "ORDER BY p.product_id";
        }
    }

    private List<Product> fetchProductsByQuery(String sql, int id, int offset, int limit) {
        List<Product> productList = new ArrayList<>();
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.setInt(2, offset);
            stm.setInt(3, limit);
            rs = stm.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String imageUrl = rs.getString("product_image");
                int minPrice = rs.getInt("min_price");

                Product product = new Product(
                        productId,
                        rs.getString("product_name"),
                        rs.getString("product_short_description"),
                        rs.getString("product_information"),
                        rs.getString("product_guideline"),
                        null,
                        rs.getInt("sub_product_category_id"),
                        minPrice
                );

                if (imageUrl != null) {
                    product.addImageUrl(imageUrl);
                }

                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<Product> getTop10BestSellingProducts() {
        List<Product> list = new ArrayList<>();
        sql = "SELECT p.product_id, p.product_name, " +
                "       SUM(pv.sold) AS total_sold, " +
                "       MIN(pv.product_image) AS product_image, " +
                "       MIN(pv.sell_price) AS min_price " +
                "FROM product p " +
                "JOIN product_variation pv ON p.product_id = pv.product_id " +
                "GROUP BY p.product_id, p.product_name " +
                "ORDER BY total_sold DESC " +
                "LIMIT 10";

        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("product_id"));
                p.setName(rs.getString("product_name"));
                p.setTotalSold(rs.getInt("total_sold"));

                String imageUrl = rs.getString("product_image");
                if (imageUrl != null) {
                    p.addImageUrl(imageUrl);
                    p.setImageUrl(imageUrl);
                }

                int minPrice = rs.getInt("min_price");
                p.setMinPrice(minPrice);

                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> getTop10NewArriveProducts() {
        List<Product> list = new ArrayList<>();
        sql = "SELECT p.product_id, p.product_name, p.created_at, " +
                "       MIN(pv.product_image) AS product_image, " +
                "       MIN(pv.sell_price) AS min_price " +
                "FROM product p " +
                "JOIN product_variation pv ON p.product_id = pv.product_id " +
                "GROUP BY p.product_id, p.product_name, p.created_at " +
                "ORDER BY p.created_at DESC " +
                "LIMIT 10";

        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("product_id"));
                p.setName(rs.getString("product_name"));
                p.setCreatedAt(rs.getTimestamp("created_at"));

                String imageUrl = rs.getString("product_image");
                if (imageUrl != null) {
                    p.addImageUrl(imageUrl);
                    p.setImageUrl(imageUrl);
                }

                int minPrice = rs.getInt("min_price");
                p.setMinPrice(minPrice);

                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
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

    public List<Product> searchProductsAdvanced(String keyword, Integer categoryId, Integer subCategoryId, int pageIndex, int pageSize, String sort) {
        List<Product> products = new ArrayList<>();
        int offset = (pageIndex - 1) * pageSize;
        String orderBy = getOrderByClause(sort);

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT p.*, ")
                .append("(SELECT MIN(pv.product_image) FROM product_variation pv WHERE pv.product_id = p.product_id) AS product_image, ")
                .append("COALESCE((SELECT MIN(pv.sell_price) FROM product_variation pv WHERE pv.product_id = p.product_id), 0) AS min_price ")
                .append("FROM product p ")
                .append("JOIN sub_product_category spc ON p.sub_product_category_id = spc.sub_product_category_id ")
                .append("JOIN product_category pc ON spc.product_category_id = pc.product_category_id ")
                .append("WHERE p.product_name LIKE ? ");

        List<Object> params = new ArrayList<>();
        params.add("%" + keyword + "%");

        if (subCategoryId != null) {
            sqlBuilder.append("AND p.sub_product_category_id = ? ");
            params.add(subCategoryId);
        } else if (categoryId != null) {
            sqlBuilder.append("AND pc.product_category_id = ? ");
            params.add(categoryId);
        }

        sqlBuilder.append(orderBy).append(" LIMIT ? OFFSET ?");
        params.add(pageSize);
        params.add(offset);

        String sql = sqlBuilder.toString();

        try {
            stm = connection.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                stm.setObject(i + 1, params.get(i));
            }

            rs = stm.executeQuery();
            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("product_short_description"),
                        rs.getString("product_information"),
                        rs.getString("product_guideline"),
                        null,
                        rs.getInt("sub_product_category_id"),
                        rs.getInt("min_price")
                );
                String imageUrl = rs.getString("product_image");
                if (imageUrl != null) p.addImageUrl(imageUrl);
                products.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public Map<String, List<Product>> getAllProductsGroupedByCategory() {
        Map<String, List<Product>> result = new LinkedHashMap<>();

        sql = "SELECT pc.product_category_name, p.product_id, p.product_name, " +
                "MIN(pv.product_image) AS product_image, " +
                "COALESCE(MIN(pv.sell_price), 0) AS min_price " +
                "FROM product_category pc " +
                "JOIN sub_product_category spc ON pc.product_category_id = spc.product_category_id " +
                "JOIN product p ON p.sub_product_category_id = spc.sub_product_category_id " +
                "LEFT JOIN product_variation pv ON p.product_id = pv.product_id " +
                "GROUP BY pc.product_category_name, p.product_id, p.product_name " +
                "ORDER BY pc.product_category_name, p.product_id";

        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                String categoryName = rs.getString("product_category_name");

                Product product = new Product();
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("product_name"));
                product.setMinPrice(rs.getInt("min_price"));

                String imageUrl = rs.getString("product_image");
                if (imageUrl != null) {
                    product.addImageUrl(imageUrl);
                }

                result.computeIfAbsent(categoryName, k -> new ArrayList<>()).add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    public int countSearchProductsAdvanced(String keyword, Integer categoryId, Integer subCategoryId) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT COUNT(*) FROM product p ")
                .append("JOIN sub_product_category spc ON p.sub_product_category_id = spc.sub_product_category_id ")
                .append("JOIN product_category pc ON spc.product_category_id = pc.product_category_id ")
                .append("WHERE p.product_name LIKE ? ");

        List<Object> params = new ArrayList<>();
        params.add("%" + keyword + "%");

        if (subCategoryId != null) {
            sqlBuilder.append("AND p.sub_product_category_id = ? ");
            params.add(subCategoryId);
        } else if (categoryId != null) {
            sqlBuilder.append("AND pc.product_category_id = ? ");
            params.add(categoryId);
        }

        try {
            stm = connection.prepareStatement(sqlBuilder.toString());
            for (int i = 0; i < params.size(); i++) {
                stm.setObject(i + 1, params.get(i));
            }

            rs = stm.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<Product> getProductsWithFilters(int pageIndex, int pageSize, String sort,
                                                Double minPrice, Double maxPrice,
                                                Integer categoryId, Integer subCategoryId,
                                                List<Integer> colorIds, List<Integer> sizeIds) {
        List<Product> products = new ArrayList<>();

        try {
            int offset = (pageIndex - 1) * pageSize;

            StringBuilder sql = new StringBuilder();
            List<Object> params = new ArrayList<>();

            sql.append("SELECT p.*, pv.* ");
            sql.append("FROM product p ");
            sql.append("LEFT JOIN product_variation pv ON p.product_id = pv.product_id ");
            sql.append("WHERE 1=1 ");

            if (subCategoryId != null) {
                sql.append("AND p.sub_product_category_id = ? ");
                params.add(subCategoryId);
            } else if (categoryId != null) {
                sql.append("AND p.sub_product_category_id IN ");
                sql.append("(SELECT sub_product_category_id FROM sub_product_category WHERE product_category_id = ?) ");
                params.add(categoryId);
            }

            if (minPrice != null) {
                sql.append("AND pv.sell_price >= ? ");
                params.add(minPrice);
            }

            if (maxPrice != null) {
                sql.append("AND pv.sell_price <= ? ");
                params.add(maxPrice);
            }

            if (colorIds != null && !colorIds.isEmpty()) {
                sql.append("AND pv.color_id IN (");
                sql.append("?,".repeat(colorIds.size()));
                sql.setLength(sql.length() - 1);
                sql.append(") ");
                params.addAll(colorIds);
            }

            if (sizeIds != null && !sizeIds.isEmpty()) {
                sql.append("AND pv.size_id IN (");
                sql.append("?,".repeat(sizeIds.size()));
                sql.setLength(sql.length() - 1);
                sql.append(") ");
                params.addAll(sizeIds);
            }

            // GROUP BY để gom sản phẩm lại
            sql.append("GROUP BY p.product_id, pv.variation_id ");

            // Sorting
            if (sort != null) {
                switch (sort) {
                    case "name-asc":
                        sql.append("ORDER BY p.product_name ASC ");
                        break;
                    case "name-desc":
                        sql.append("ORDER BY p.product_name DESC ");
                        break;
                    case "price-asc":
                        sql.append("ORDER BY MIN(pv.sell_price) ASC ");
                        break;
                    case "price-desc":
                        sql.append("ORDER BY MIN(pv.sell_price) DESC ");
                        break;
                    default:
                        sql.append("ORDER BY p.product_id ASC ");
                }
            } else {
                sql.append("ORDER BY p.product_id ASC ");
            }

            sql.append("LIMIT ? OFFSET ? ");
            params.add(pageSize);
            params.add(offset);

            PreparedStatement stm = connection.prepareStatement(sql.toString());

            // Set all parameters
            int idx = 1;
            for (Object param : params) {
                if (param instanceof Integer) {
                    stm.setInt(idx++, (Integer) param);
                } else if (param instanceof Double) {
                    stm.setDouble(idx++, (Double) param);
                }
            }

            ResultSet rs = stm.executeQuery();

            int lastProductId = -1;
            Product currentProduct = null;

            while (rs.next()) {
                int productId = rs.getInt("product_id");

                if (productId != lastProductId) {
                    currentProduct = new Product();
                    currentProduct.setId(productId);
                    currentProduct.setName(rs.getString("product_name"));
                    currentProduct.setDescription(rs.getString("product_short_description"));
                    currentProduct.setInformation(rs.getString("product_information"));
                    currentProduct.setGuideline(rs.getString("product_guideline"));
                    currentProduct.setSubProductCategoryId(rs.getInt("sub_product_category_id"));
                    currentProduct.setCreatedAt(rs.getTimestamp("created_at"));
                    currentProduct.setUpdatedAt(rs.getTimestamp("updated_at"));
                    currentProduct.setMinPrice(Integer.MAX_VALUE); // default

                    products.add(currentProduct);
                    lastProductId = productId;
                }

                // Add image
                String imageUrl = rs.getString("product_image");
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    currentProduct.addImageUrl(imageUrl);
                }

                // Add variation
                int variationId = rs.getInt("variation_id");
                if (!rs.wasNull()) {
                    ProductVariation variation = new ProductVariation();
                    variation.setVariationId(variationId);
                    variation.setProductId(productId);
                    variation.setColorId(rs.getInt("color_id"));
                    variation.setSizeId(rs.getInt("size_id"));
                    variation.setSell_price(rs.getInt("sell_price"));
                    variation.setQtyInStock(rs.getInt("qty_in_stock"));
                    currentProduct.addVariation(variation);

                    if (variation.getSell_price() < currentProduct.getMinPrice()) {
                        currentProduct.setMinPrice(variation.getSell_price());
                    }
                }
            }

            // Nếu không có giá nào, set minPrice = 0
            for (Product p : products) {
                if (p.getMinPrice() == Integer.MAX_VALUE) {
                    p.setMinPrice(0);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }


    public int countProductsWithFilters(Double minPrice, Double maxPrice,
                                        String categoryId, String subCategoryId,
                                        List<Integer> colorIds, List<Integer> sizeIds) {
        int count = 0;
        List<Object> params = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(DISTINCT p.product_id) as total ");
        sql.append("FROM product p ");
        sql.append("LEFT JOIN product_variation pv ON p.product_id = pv.product_id ");
        sql.append("WHERE 1=1 ");

        if (subCategoryId != null && !subCategoryId.isEmpty()) {
            sql.append("AND p.sub_product_category_id = ? ");
            params.add(Integer.parseInt(subCategoryId));
        } else if (categoryId != null && !categoryId.isEmpty()) {
            sql.append("AND p.sub_product_category_id IN (");
            sql.append("SELECT sub_product_category_id FROM sub_product_category WHERE product_category_id = ?) ");
            params.add(Integer.parseInt(categoryId));
        }

        if (minPrice != null) {
            sql.append("AND pv.sell_price >= ? ");
            params.add(minPrice);
        }
        if (maxPrice != null) {
            sql.append("AND pv.sell_price <= ? ");
            params.add(maxPrice);
        }

        if (colorIds != null && !colorIds.isEmpty()) {
            sql.append("AND pv.color_id IN (");
            sql.append("?,".repeat(colorIds.size()));
            sql.setLength(sql.length() - 1); // remove last comma
            sql.append(") ");
            params.addAll(colorIds);
        }

        if (sizeIds != null && !sizeIds.isEmpty()) {
            sql.append("AND pv.size_id IN (");
            sql.append("?,".repeat(sizeIds.size()));
            sql.setLength(sql.length() - 1);
            sql.append(") ");
            params.addAll(sizeIds);
        }

        try (PreparedStatement stm = connection.prepareStatement(sql.toString())) {
            int i = 1;
            for (Object param : params) {
                if (param instanceof Integer) {
                    stm.setInt(i++, (Integer) param);
                } else if (param instanceof Double) {
                    stm.setDouble(i++, (Double) param);
                }
            }

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public List<Product> getAllProducts(int page, int pageSize, String sort, Integer categoryId, Integer subCategoryId) {
        List<Product> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.*, MIN(pv.sell_price) as min_price ")
                .append("FROM product p ")
                .append("LEFT JOIN product_variation pv ON p.product_id = pv.product_id ")
                .append("WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        if (subCategoryId != null) {
            sql.append("AND p.sub_product_category_id = ? ");
            params.add(subCategoryId);
        } else if (categoryId != null) {
            sql.append("AND p.sub_product_category_id IN (")
                    .append("SELECT sub_product_category_id FROM sub_product_category WHERE product_category_id = ?")
                    .append(") ");
            params.add(categoryId);
        }

        sql.append("GROUP BY p.product_id ");

        if (sort != null) {
            switch (sort) {
                case "name-asc":
                    sql.append("ORDER BY p.product_name ASC ");
                    break;
                case "name-desc":
                    sql.append("ORDER BY p.product_name DESC ");
                    break;
                case "price-asc":
                    sql.append("ORDER BY MIN(pv.sell_price) ASC ");
                    break;
                case "price-desc":
                    sql.append("ORDER BY MIN(pv.sell_price) DESC ");
                    break;
                default:
                    sql.append("ORDER BY p.product_id ASC ");
            }
        } else {
            sql.append("ORDER BY p.product_id ASC ");
        }
        // --- END FIX ---

        sql.append("LIMIT ? OFFSET ? ");
        params.add(pageSize);
        params.add((page - 1) * pageSize);

        try (PreparedStatement stm = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stm.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("product_id"));
                    product.setName(rs.getString("product_name"));
                    product.setDescription(rs.getString("product_short_description"));
                    product.setInformation(rs.getString("product_information"));
                    product.setGuideline(rs.getString("product_guideline"));
                    product.setSubProductCategoryId(rs.getInt("sub_product_category_id"));
                    product.setCreatedAt(rs.getTimestamp("created_at"));
                    product.setUpdatedAt(rs.getTimestamp("updated_at"));
                    product.setMinPrice(rs.getObject("min_price") != null ? rs.getInt("min_price") : 0);

                    // Load image URLs
                    product.setImageUrls(getProductImages(product.getId()));

                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public int countAllProducts(Integer categoryId, Integer subCategoryId) {
        int count = 0;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM product p WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (subCategoryId != null) {
            sql.append("AND p.sub_product_category_id = ? ");
            params.add(subCategoryId);
        } else if (categoryId != null) {
            sql.append("AND p.sub_product_category_id IN (")
                    .append("SELECT sub_product_category_id FROM sub_product_category WHERE product_category_id = ?")
                    .append(") ");
            params.add(categoryId);
        }

        try (PreparedStatement stm = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stm.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    private List<String> getProductImages(int productId) {
        List<String> images = new ArrayList<>();
        String sql = "SELECT DISTINCT product_image FROM product_variation WHERE product_id = ? AND product_image IS NOT NULL";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, productId);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    images.add(rs.getString("product_image"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return images;
    }


}
