package dal;

import model.Address;
import model.District;
import model.Province;
import model.Ward;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO extends DBContext {

    private static final double FPT_LAT = 21.0137142;
    private static final double FPT_LNG = 105.5259391;

    public List<Address> getAddressesByUserId(int userId) {
        List<Address> list = new ArrayList<>();

        sql = "SELECT a.*, ua.is_default, " +
                "p.name AS province_name, " +
                "d.name AS district_name, " +
                "w.name AS ward_name " +
                "FROM userAddress ua " +
                "JOIN address a ON ua.address_id = a.address_id " +
                "LEFT JOIN province p ON a.province_code = p.code " +
                "LEFT JOIN district d ON a.district_code = d.code " +
                "LEFT JOIN ward w ON a.ward_code = w.code " +
                "WHERE ua.user_id = ? " +
                "ORDER BY ua.is_default DESC, a.address_id DESC";

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            rs = stm.executeQuery();

            while (rs.next()) {
                Address address = new Address();
                address.setAddressId(rs.getInt("address_id"));
                address.setProvinceCode(rs.getString("province_code"));
                address.setDistrictCode(rs.getString("district_code"));
                address.setWardCode(rs.getString("ward_code"));
                address.setDetail(rs.getString("detail"));
                address.setDistanceKm(rs.getDouble("distance_km"));
                address.setDefaultAddress(rs.getBoolean("is_default"));

                // Gán tên tỉnh/huyện/xã nếu dùng object
                Province province = new Province();
                province.setCode(rs.getString("province_code"));
                province.setName(rs.getString("province_name"));
                address.setProvince(province);

                District district = new District();
                district.setCode(rs.getString("district_code"));
                district.setName(rs.getString("district_name"));
                address.setDistrict(district);

                Ward ward = new Ward();
                ward.setCode(rs.getString("ward_code"));
                ward.setName(rs.getString("ward_name"));
                address.setWard(ward);

                list.add(address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Address getAddressById(int addressId, int userId) {
        sql = "SELECT a.*, " +
                "p.code AS province_code, p.name AS province_name, " +
                "d.code AS district_code, d.name AS district_name, " +
                "w.code AS ward_code, w.name AS ward_name " +
                "FROM address a " +
                "JOIN userAddress ua ON a.address_id = ua.address_id " +
                "JOIN province p ON a.province_code = p.code " +
                "JOIN district d ON a.district_code = d.code " +
                "JOIN ward w ON a.ward_code = w.code " +
                "WHERE a.address_id = ? AND ua.user_id = ?";

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, addressId);
            stm.setInt(2, userId);
            rs = stm.executeQuery();

            if (rs.next()) {
                Address a = new Address();
                a.setAddressId(rs.getInt("address_id"));
                a.setDetail(rs.getString("detail"));
                a.setDistanceKm(rs.getDouble("distance_km"));

                Province province = new Province();
                province.setCode(rs.getString("province_code"));
                province.setName(rs.getString("province_name"));

                District district = new District();
                district.setCode(rs.getString("district_code"));
                district.setName(rs.getString("district_name"));

                Ward ward = new Ward();
                ward.setCode(rs.getString("ward_code"));
                ward.setName(rs.getString("ward_name"));

                a.setProvince(province);
                a.setDistrict(district);
                a.setWard(ward);

                a.setProvinceCode(province.getCode());
                a.setDistrictCode(district.getCode());
                a.setWardCode(ward.getCode());

                return a;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return null;
    }

    public boolean addAddress(Address address, int userId) {
        String checkUserSQL = "SELECT user_id FROM user WHERE user_id = ?";
        String countUserAddressesSQL = "SELECT COUNT(*) FROM userAddress WHERE user_id = ?";
        String insertAddressSQL = "INSERT INTO address (province_code, district_code, ward_code, detail, distance_km) VALUES (?, ?, ?, ?, ?)";
        String insertUserAddressSQL = "INSERT INTO userAddress (user_id, address_id, is_default) VALUES (?, ?, ?)";

        try {
            connection.setAutoCommit(false);

            // Check user existence
            stm = connection.prepareStatement(checkUserSQL);
            stm.setInt(1, userId);
            rs = stm.executeQuery();
            if (!rs.next()) {
                connection.rollback();
                return false;
            }

            // Calculate distance
            double distanceKm = 0;
            Ward ward = new WardDAO().getWardByCode(address.getWardCode());
            if (ward != null && ward.getLatitude() != null && ward.getLongitude() != null
                    && ward.getLatitude() != 0 && ward.getLongitude() != 0) {
                distanceKm = calculateDistance(FPT_LAT, FPT_LNG, ward.getLatitude(), ward.getLongitude());
            }

            // Insert into address table
            PreparedStatement insertAddress = connection.prepareStatement(insertAddressSQL, Statement.RETURN_GENERATED_KEYS);
            insertAddress.setString(1, address.getProvinceCode());
            insertAddress.setString(2, address.getDistrictCode());
            insertAddress.setString(3, address.getWardCode());
            insertAddress.setString(4, address.getDetail());
            insertAddress.setDouble(5, distanceKm);
            int affectedRows = insertAddress.executeUpdate();
            if (affectedRows == 0) {
                connection.rollback();
                return false;
            }

            // Get generated address ID
            ResultSet generatedKeys = insertAddress.getGeneratedKeys();
            if (!generatedKeys.next()) {
                connection.rollback();
                return false;
            }
            int addressId = generatedKeys.getInt(1);

            // Check if this is the user's first address
            boolean isDefault = false;
            PreparedStatement countStmt = connection.prepareStatement(countUserAddressesSQL);
            countStmt.setInt(1, userId);
            ResultSet countRs = countStmt.executeQuery();
            if (countRs.next() && countRs.getInt(1) == 0) {
                isDefault = true;
            }

            // Insert into userAddress
            PreparedStatement insertUserAddress = connection.prepareStatement(insertUserAddressSQL);
            insertUserAddress.setInt(1, userId);
            insertUserAddress.setInt(2, addressId);
            insertUserAddress.setBoolean(3, isDefault);
            int uaRows = insertUserAddress.executeUpdate();
            if (uaRows == 0) {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            close();
        }
    }

    public boolean updateAddress(Address address, int userId) {
        String checkOwnershipSQL = "SELECT * FROM userAddress WHERE user_id = ? AND address_id = ?";
        String updateSQL = "UPDATE address SET province_code = ?, district_code = ?, ward_code = ?, detail = ?, distance_km = ? WHERE address_id = ?";

        try {
            connection.setAutoCommit(false);

            // Check ownership
            PreparedStatement checkStmt = connection.prepareStatement(checkOwnershipSQL);
            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, address.getAddressId());
            ResultSet rsCheck = checkStmt.executeQuery();

            if (!rsCheck.next()) {
                connection.rollback();
                return false;
            }

            // Calculate distance
            double distanceKm = 0;
            Ward ward = new WardDAO().getWardByCode(address.getWardCode());

            if (ward == null) {
                System.out.println("Ward not found for code: " + address.getWardCode());
            } else {
                Double lat = ward.getLatitude();
                Double lng = ward.getLongitude();

                if (lat == null || lng == null || lat == 0.0 || lng == 0.0) {
                    System.out.println("Invalid lat/lng for ward code: " + address.getWardCode());
                    distanceKm = 0;
                } else {
                    distanceKm = calculateDistance(FPT_LAT, FPT_LNG, lat, lng);
                }
            }

            PreparedStatement updateStmt = connection.prepareStatement(updateSQL);
            updateStmt.setString(1, address.getProvinceCode());
            updateStmt.setString(2, address.getDistrictCode());
            updateStmt.setString(3, address.getWardCode());
            updateStmt.setString(4, address.getDetail());
            updateStmt.setDouble(5, distanceKm);
            updateStmt.setInt(6, address.getAddressId());

            int updatedRows = updateStmt.executeUpdate();

            if (updatedRows == 0) {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            close();
        }
    }

    public boolean deleteAddress(int addressId, int userId) {
        String deleteLinkSQL = "DELETE FROM userAddress WHERE user_id = ? AND address_id = ?";
        String deleteAddressSQL = "DELETE FROM address WHERE address_id = ?";

        try {
            connection.setAutoCommit(false);

            PreparedStatement deleteLink = connection.prepareStatement(deleteLinkSQL);
            deleteLink.setInt(1, userId);
            deleteLink.setInt(2, addressId);
            deleteLink.executeUpdate();

            PreparedStatement deleteAddress = connection.prepareStatement(deleteAddressSQL);
            deleteAddress.setInt(1, addressId);
            deleteAddress.executeUpdate();

            connection.commit();
            return true;
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            close();
        }

        return false;
    }

    public boolean setDefaultAddress(int userId, int addressId) {
        String unsetSQL = "UPDATE userAddress SET is_default = FALSE WHERE user_id = ?";
        String setSQL = "UPDATE userAddress SET is_default = TRUE WHERE user_id = ? AND address_id = ?";

        try (Connection conn = connection) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps1 = conn.prepareStatement(unsetSQL);
                 PreparedStatement ps2 = conn.prepareStatement(setSQL)) {

                ps1.setInt(1, userId);
                ps1.executeUpdate();

                ps2.setInt(1, userId);
                ps2.setInt(2, addressId);
                int rows = ps2.executeUpdate();

                conn.commit();
                return rows > 0;

            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Address> getAllAddresses() {
        List<Address> list = new ArrayList<>();

        String sql = "SELECT a.*, " +
                "p.name AS province_name, " +
                "d.name AS district_name, " +
                "w.name AS ward_name " +
                "FROM address a " +
                "LEFT JOIN province p ON a.province_code = p.code " +
                "LEFT JOIN district d ON a.district_code = d.code " +
                "LEFT JOIN ward w ON a.ward_code = w.code " +
                "ORDER BY a.address_id DESC";

        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                Address address = new Address();
                address.setAddressId(rs.getInt("address_id"));
                address.setProvinceCode(rs.getString("province_code"));
                address.setDistrictCode(rs.getString("district_code"));
                address.setWardCode(rs.getString("ward_code"));
                address.setDetail(rs.getString("detail"));
                address.setDistanceKm(rs.getDouble("distance_km"));

                Province province = new Province();
                province.setCode(rs.getString("province_code"));
                province.setName(rs.getString("province_name"));
                address.setProvince(province);

                District district = new District();
                district.setCode(rs.getString("district_code"));
                district.setName(rs.getString("district_name"));
                address.setDistrict(district);

                Ward ward = new Ward();
                ward.setCode(rs.getString("ward_code"));
                ward.setName(rs.getString("ward_name"));
                address.setWard(ward);

                list.add(address);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return list;
    }



    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        final int R = 6371; // Earth radius in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    private void close() {
        try { if (rs != null) rs.close(); } catch (Exception ignored) {}
        try { if (stm != null) stm.close(); } catch (Exception ignored) {}
        try { if (connection != null) connection.close(); } catch (Exception ignored) {}
    }
}
