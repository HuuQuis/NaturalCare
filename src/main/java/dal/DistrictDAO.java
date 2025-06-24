package dal;

import model.District;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DistrictDAO extends DBContext{
    public List<District> getDistrictsByProvinceCode(String provinceCode) throws SQLException {
        List<District> list = new ArrayList<>();
        sql = "SELECT * FROM district WHERE province_code = ? ORDER BY full_name";
        stm = connection.prepareStatement(sql);
        stm.setString(1, provinceCode);
         rs = stm.executeQuery();
        while (rs.next()) {
            District d = new District(
                    rs.getString("code"),
                    rs.getString("name"),
                    rs.getString("full_name"),
                    rs.getString("province_code")
            );
            list.add(d);
        }
        return list;
    }
}

