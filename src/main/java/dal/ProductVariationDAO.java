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
            stm.setInt(3, variation.getColorId());
            stm.setInt(4, variation.getSizeId());
            stm.setInt(5, variation.getPrice());
            stm.setInt(6, variation.getQtyInStock());
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProductVariation(ProductVariation variation, int variationId) {
        sql = "UPDATE product_variation SET product_image = ?, color_id = ?, size_id = ?, price = ?, qty_in_stock = ? WHERE variation_id = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, variation.getImageUrl());
            stm.setInt(2, variation.getColorId());
            stm.setInt(3, variation.getSizeId());
            stm.setInt(4, variation.getPrice());
            stm.setInt(5, variation.getQtyInStock());
            stm.setInt(6, variationId);
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
}
