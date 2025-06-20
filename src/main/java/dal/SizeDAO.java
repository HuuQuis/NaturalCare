package dal;

import model.Size;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SizeDAO extends DBContext{
    public List<Size> getAllSize() {
        sql = "SELECT * FROM size";
        List<Size> sizes = new ArrayList<>();
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Size size = new Size();
                size.setId(rs.getInt("size_id"));
                size.setName(rs.getString("size_name"));
                sizes.add(size);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return sizes;
    }
}
