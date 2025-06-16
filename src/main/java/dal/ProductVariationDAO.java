package dal;

import model.ProductVariation;

import java.sql.SQLException;

public class ProductVariationDAO extends DBContext{
    public void addProductVariation(ProductVariation variation, int productId) {
        sql = "INSERT INTO product_variation (product_id, product_image, color_id, size_id, price, qty_in_stock) VALUES (?, ?, ?, ?, ?, ?)";
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
            stm.setInt(6, variation.getQtyInStock());
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProductVariation(ProductVariation variation) {
        sql = "UPDATE product_variation SET product_image = ?, color_id = ?, size_id = ?, price = ?, qty_in_stock = ? WHERE variation_id = ?";
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
            stm.setInt(5, variation.getQtyInStock());
            stm.setInt(6, variation.getVariationId());

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
        sql = "SELECT * FROM product_variation WHERE variation_id = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, variationId);
            rs = stm.executeQuery();
            if (rs.next()) {
                return new ProductVariation(
                        rs.getInt("variation_id"),
                        rs.getInt("product_id"),
                        rs.getString("product_image"),
                        rs.getInt("color_id"),
                        rs.getInt("size_id"),
                        rs.getInt("price"),
                        rs.getInt("qty_in_stock")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
