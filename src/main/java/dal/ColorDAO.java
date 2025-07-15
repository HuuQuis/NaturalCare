package dal;

import model.Color;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ColorDAO extends DBContext{
    public List<Color> getAllColors() {
        sql = "SELECT * FROM color";
        List<Color> colors = new ArrayList<>();
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Color color = new Color();
                color.setId(rs.getInt("color_id"));
                color.setName(rs.getString("color_name"));
                colors.add(color);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return colors;
    }

    public List<Integer> getIdsByNames(String[] names) {
        List<Integer> ids = new ArrayList<>();
        if (names != null) {
            sql = "SELECT color_id FROM color WHERE color_name = ?";
            try {
                stm = connection.prepareStatement(sql);
                for (String name : names) {
                    stm.setString(1, name);
                    rs = stm.executeQuery();
                    if (rs.next()) {
                        ids.add(rs.getInt("color_id"));
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
