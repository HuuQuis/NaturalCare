package dal;

import model.SubProductCategory;
import java.sql.*;
import java.util.*;

public class SubProductCategoryDAO extends DBContext {
    public List<SubProductCategory> getSubCategoriesByCategoryId(int CategoryId) {
        List<SubProductCategory> list = new ArrayList<>();
        sql = "SELECT * FROM sub_product_category WHERE product_category_id = ?";

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, CategoryId);
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

    public List<SubProductCategory> getAllSubProductCategories() {
        List<SubProductCategory> list = new ArrayList<>();
        sql = "SELECT * FROM sub_product_category";

        try {
            stm = connection.prepareStatement(sql);
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
