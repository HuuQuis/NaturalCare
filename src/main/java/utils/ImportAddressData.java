package utils;

import dal.DBContext;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ImportAddressData {

    public static void main(String[] args) {
        DBContext dbContext = new DBContext();
        if (!dbContext.testConnection()) {
            System.out.println("Không thể kết nối CSDL!");
            return;
        }

        try {
            Connection conn = dbContext.connection;
            importAddressData(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void importAddressData(Connection conn) throws Exception {
        String json = getJson("https://provinces.open-api.vn/api/?depth=3");
        JSONArray provinces = new JSONArray(json);

        String sqlProvince = "INSERT INTO province (code, name, full_name) VALUES (?, ?, ?)";
        String sqlDistrict = "INSERT INTO district (code, name, full_name, province_code) VALUES (?, ?, ?, ?)";
        String sqlWard = "INSERT INTO ward (code, name, full_name, district_code, latitude, longitude) VALUES (?, ?, ?, ?, ?, ?)";

        try (
                PreparedStatement psProvince = conn.prepareStatement(sqlProvince);
                PreparedStatement psDistrict = conn.prepareStatement(sqlDistrict);
                PreparedStatement psWard = conn.prepareStatement(sqlWard)
        ) {
            conn.setAutoCommit(false);

            for (int i = 0; i < provinces.length(); i++) {
                JSONObject province = provinces.getJSONObject(i);
                String provinceCode = String.valueOf(province.get("code"));
                String provinceName = province.getString("name");
                String provinceFullName = province.optString("name_with_type", provinceName);

                psProvince.setString(1, provinceCode);
                psProvince.setString(2, provinceName);
                psProvince.setString(3, provinceFullName);
                psProvince.executeUpdate();

                JSONArray districts = province.getJSONArray("districts");
                for (int j = 0; j < districts.length(); j++) {
                    JSONObject district = districts.getJSONObject(j);
                    String districtCode = String.valueOf(district.get("code"));
                    String districtName = district.getString("name");
                    String districtFullName = district.optString("name_with_type", districtName);

                    psDistrict.setString(1, districtCode);
                    psDistrict.setString(2, districtName);
                    psDistrict.setString(3, districtFullName);
                    psDistrict.setString(4, provinceCode);
                    psDistrict.executeUpdate();

                    JSONArray wards = district.getJSONArray("wards");
                    for (int k = 0; k < wards.length(); k++) {
                        JSONObject ward = wards.getJSONObject(k);
                        String wardCode = String.valueOf(ward.get("code"));
                        String wardName = ward.getString("name");
                        String wardFullName = ward.optString("name_with_type", wardName);

                        double latitude = ward.has("latitude") && !ward.isNull("latitude")
                                ? ward.getDouble("latitude") : 0.0;
                        double longitude = ward.has("longitude") && !ward.isNull("longitude")
                                ? ward.getDouble("longitude") : 0.0;

                        psWard.setString(1, wardCode);
                        psWard.setString(2, wardName);
                        psWard.setString(3, wardFullName);
                        psWard.setString(4, districtCode);
                        psWard.setDouble(5, latitude);
                        psWard.setDouble(6, longitude);
                        psWard.executeUpdate();
                    }
                }
            }

            conn.commit();
            System.out.println("Import địa phương thành công!");
        } catch (Exception e) {
            conn.rollback();
            throw e;
        }
    }

    private static String getJson(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept", "application/json");

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        }
    }
}
