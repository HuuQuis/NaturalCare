package dal;

import model.ProductCategory;
import java.sql.*;
import java.util.*;

public class ProductCategoryDAO extends DBContext {
    public List<ProductCategory> getAllProductCategories() {
        List<ProductCategory> list = new ArrayList<>();
        sql = "SELECT * FROM product_category";

        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                ProductCategory category = new ProductCategory(
                        rs.getInt("product_category_id"),
                        rs.getString("product_category_name")
                );
                list.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
