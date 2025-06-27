package dal;

import model.Ward;

public class WardDAO extends DBContext {

    public Ward getWardByCode(String wardCode) {
        sql = "SELECT * FROM ward WHERE code = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, wardCode);
            rs = stm.executeQuery();
            if (rs.next()) {
                Ward ward = new Ward();
                ward.setCode(rs.getString("code"));
                ward.setName(rs.getString("name"));
                ward.setDistrictCode(rs.getString("district_code"));

                double lat = rs.getDouble("latitude");
                if (!rs.wasNull()) {
                    ward.setLatitude(lat);
                }

                double lng = rs.getDouble("longitude");
                if (!rs.wasNull()) {
                    ward.setLongitude(lng);
                }

                return ward;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return null;
    }

    private void close() {
        try { if (rs != null) rs.close(); } catch (Exception ignored) {}
        try { if (stm != null) stm.close(); } catch (Exception ignored) {}
        try { if (connection != null) connection.close(); } catch (Exception ignored) {}
    }
}
