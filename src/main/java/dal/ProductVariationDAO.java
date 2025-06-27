package dal;

import model.ProductVariation;

import java.sql.SQLException;

public class ProductVariationDAO extends DBContext{
    public void addProductVariation(ProductVariation variation, int productId) {
        sql = "INSERT INTO product_variation (product_id, product_image, color_id, size_id, price, sell_price, qty_in_stock) VALUES (?, ?, ?, ?, ?, ?,?)";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, productId);
            stm.setString(2, variation.getImageUrl());
            // Set color_id and size_id to null if 0 (not selected)
            if (variation.getColorId() > 0) {
                stm.setInt(3, variation.getColorId());
            } else {
                stm.setNull(3, java.sql.Types.INTEGER);
            }
            if (variation.getSizeId() > 0) {
                stm.setInt(4, variation.getSizeId());
            } else {
                stm.setNull(4, java.sql.Types.INTEGER);
            }
            stm.setInt(5, variation.getPrice());
            stm.setInt(6, variation.getSell_price());
            stm.setInt(7, variation.getQtyInStock());
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProductVariation(ProductVariation variation) {
        sql = "UPDATE product_variation SET product_image = ?, color_id = ?, size_id = ?, price = ?, sell_price = ?, qty_in_stock = ? WHERE variation_id = ?";
        try  {
            stm = connection.prepareStatement(sql);
            stm.setString(1, variation.getImageUrl());

            if (variation.getColorId() > 0) {
                stm.setInt(2, variation.getColorId());
            } else {
                stm.setNull(2, java.sql.Types.INTEGER);
            }

            if (variation.getSizeId() > 0) {
                stm.setInt(3, variation.getSizeId());
            } else {
                stm.setNull(3, java.sql.Types.INTEGER);
            }

            stm.setInt(4, variation.getPrice());
            stm.setInt(5, variation.getSell_price());
            stm.setInt(6, variation.getQtyInStock());
            stm.setInt(7, variation.getVariationId());

            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProductVariation(int variationId) {
        sql = "DELETE FROM product_variation WHERE variation_id = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, variationId);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ProductVariation getProductVariationById(int variationId) {
        sql = "SELECT pv.*, p.product_name, c.color_name, s.size_name " +
                "FROM product_variation pv " +
                "JOIN product p ON pv.product_id = p.product_id " +
                "LEFT JOIN color c ON pv.color_id = c.color_id " +
                "LEFT JOIN size s ON pv.size_id = s.size_id " +
                "WHERE pv.variation_id = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, variationId);
            rs = stm.executeQuery();
            if (rs.next()) {
                ProductVariation pv = new ProductVariation(
                        rs.getInt("variation_id"),
                        rs.getInt("product_id"),
                        rs.getString("product_image"),
                        rs.getInt("color_id"),
                        rs.getInt("size_id"),
                        rs.getInt("price"),
                        rs.getInt("sell_price"),
                        rs.getInt("qty_in_stock")
                );
                pv.setColorName(rs.getString("color_name"));
                pv.setSizeName(rs.getString("size_name"));
                pv.setProductName(rs.getString("product_name"));
                return pv;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean isDuplicateColor(int productId, int colorId, Integer excludeVariationId) {
        sql = "SELECT COUNT(*) FROM product_variation WHERE product_id = ? AND " +
                "((color_id = ?) OR (color_id IS NULL AND ? = 0))";

        if (excludeVariationId != null) {
            sql += " AND variation_id != ?";
        }

        try {
            stm = connection.prepareStatement(sql);
            int index = 1;
            stm.setInt(index++, productId);
            stm.setInt(index++, colorId);
            stm.setInt(index++, colorId);
            if (excludeVariationId != null) {
                stm.setInt(index++, excludeVariationId);
            }

            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean isDuplicateSize(int productId, int sizeId, Integer excludeVariationId) {
        sql = "SELECT COUNT(*) FROM product_variation WHERE product_id = ? AND " +
                "((size_id = ?) OR (size_id IS NULL AND ? = 0))";

        if (excludeVariationId != null) {
            sql += " AND variation_id != ?";
        }

        try {
            stm = connection.prepareStatement(sql);
            int index = 1;
            stm.setInt(index++, productId);
            stm.setInt(index++, sizeId);
            stm.setInt(index++, sizeId);
            if (excludeVariationId != null) {
                stm.setInt(index++, excludeVariationId);
            }

            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void updateStockAfterOrder(int variationId, int quantityOrdered) {
        sql = "UPDATE product_variation SET qty_in_stock = qty_in_stock - ?, sold = sold + ? WHERE variation_id = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, quantityOrdered);
            stm.setInt(2, quantityOrdered);
            stm.setInt(3, variationId);
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
