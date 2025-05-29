package dal;

import model.SubProductCategory;
import java.sql.*;
import java.util.*;

public class SubProductCategoryDAO extends DBContext {
    public List<SubProductCategory> getSubCategoriesByProductId(int productCategoryId) {
        List<SubProductCategory> list = new ArrayList<>();
        sql = "SELECT * FROM sub_product_category WHERE product_category_id = ?";

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, productCategoryId);
            rs = stm.executeQuery();

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
}
