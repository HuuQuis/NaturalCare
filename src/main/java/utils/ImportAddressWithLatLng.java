package utils;

import dal.DBContext;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ImportAddressWithLatLng {

    public static void main(String[] args) {
        DBContext dbContext = new DBContext();
        if (!dbContext.testConnection()) {
            System.out.println("Không thể kết nối CSDL!");
            return;
        }

        try {
            Connection conn = dbContext.connection;
            importAddressDataAndLatLng(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void importAddressDataAndLatLng(Connection conn) throws Exception {
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
            int provinceCount = 0;
            int districtCount = 0;
            int wardCount = 0;

            for (int i = 0; i < provinces.length(); i++) {
                JSONObject province = provinces.getJSONObject(i);
                String provinceCode = String.valueOf(province.get("code"));
                String provinceName = province.getString("name");
                String provinceFullName = province.optString("name_with_type", provinceName);

                psProvince.setString(1, provinceCode);
                psProvince.setString(2, provinceName);
                psProvince.setString(3, provinceFullName);
                psProvince.executeUpdate();
                provinceCount++;

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
                    districtCount++;

                    JSONArray wards = district.getJSONArray("wards");
                    for (int k = 0; k < wards.length(); k++) {
                        JSONObject ward = wards.getJSONObject(k);
                        String wardCode = String.valueOf(ward.get("code"));
                        String wardName = ward.getString("name");
                        String wardFullName = ward.optString("name_with_type", wardName);

                        // Lấy lat/lng từ Nominatim
                        double[] latlng = getLatLngFromNominatim(wardFullName, districtFullName, provinceFullName);
                        double latitude = latlng[0];
                        double longitude = latlng[1];

                        psWard.setString(1, wardCode);
                        psWard.setString(2, wardName);
                        psWard.setString(3, wardFullName);
                        psWard.setString(4, districtCode);
                        psWard.setDouble(5, latitude);
                        psWard.setDouble(6, longitude);
                        psWard.executeUpdate();
                        wardCount++;

                        System.out.printf("%s: %s - %.6f, %.6f\n", wardCode, wardFullName, latitude, longitude);
                        Thread.sleep(1000); // tránh bị block IP
                    }
                }
            }

            conn.commit();
            System.out.printf("\nHoàn tất: %d tỉnh, %d huyện, %d xã/phường đã được import kèm lat/lng.\n",
                    provinceCount, districtCount, wardCount);

        } catch (Exception e) {
            conn.rollback();
            throw e;
        }
    }

    private static double[] getLatLngFromNominatim(String ward, String district, String province) {
        try {
            String fullAddress = String.format("%s, %s, %s, Việt Nam", ward, district, province);
            String encoded = URLEncoder.encode(fullAddress, "UTF-8");
            String url = "https://nominatim.openstreetmap.org/search?q=" + encoded + "&format=json&limit=1";

            String json = getJson(url);
            JSONArray arr = new JSONArray(json);
            if (arr.length() > 0) {
                JSONObject obj = arr.getJSONObject(0);
                double lat = Double.parseDouble(obj.getString("lat"));
                double lon = Double.parseDouble(obj.getString("lon"));
                return new double[]{lat, lon};
            }
        } catch (Exception e) {
            System.err.println("Không thể lấy tọa độ cho: " + ward + ", " + district + ", " + province);
        }
        return new double[]{0.0, 0.0};
    }

    private static String getJson(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Java Setup Script)");

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
