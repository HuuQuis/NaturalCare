package dal;

import model.subProductCategory;
import java.sql.*;
import java.util.*;

public class SubProductCategoryDAO extends DBContext {
    public List<subProductCategory> getSubCategoriesByProductId(int productCategoryId) {
        List<subProductCategory> list = new ArrayList<>();
        sql = "SELECT * FROM sub_product_category WHERE product_category_id = ?";

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, productCategoryId);
            rs = stm.executeQuery();

            while (rs.next()) {
                subProductCategory subCategory = new subProductCategory(
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
}
