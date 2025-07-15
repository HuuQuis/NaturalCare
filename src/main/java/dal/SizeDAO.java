package dal;

import model.Size;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SizeDAO extends DBContext{
    public List<Size> getAllSizes() {
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

    public List<Integer> getIdsByNames(String[] names) {
        List<Integer> ids = new ArrayList<>();
        if (names != null) {
            sql = "SELECT size_id FROM size WHERE size_name = ?";
            try {
                stm = connection.prepareStatement(sql);
                for (String name : names) {
                    stm.setString(1, name);
                    rs = stm.executeQuery();
                    if (rs.next()) {
                        ids.add(rs.getInt("size_id"));
                    }
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ids;
    }

}
