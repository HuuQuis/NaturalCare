package dal;
import model.Province;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProvinceDAO extends DBContext {
    public List<Province> getAllProvinces() throws SQLException {
        List<Province> list = new ArrayList<>();
        sql = "SELECT * FROM province ORDER BY full_name";
        stm = connection.prepareStatement(sql);
        rs = stm.executeQuery();
        while (rs.next()) {
            Province p = new Province(
                    rs.getString("code"),
                    rs.getString("name"),
                    rs.getString("full_name")
            );
            list.add(p);
        }
        return list;
    }
}
