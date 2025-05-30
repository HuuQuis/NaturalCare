package dal;

import model.BlogCategory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BlogCategoryDAO extends DBContext{
    public List<BlogCategory> getAllBlogCategories() {
        List<BlogCategory> list = new ArrayList<>();
        sql = "SELECT * FROM blog_category";

        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                BlogCategory category = new BlogCategory(
                        rs.getInt("blog_category_id"),
                        rs.getString("blog_category_name")
                );
                list.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
