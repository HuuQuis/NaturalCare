package dal;

import model.Address;
import model.Ward;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO extends DBContext {

    private static final double FPT_LAT = 21.0137142;
    private static final double FPT_LNG = 105.5259391;

    public List<Address> getAddressesByUserId(int userId) {
        List<Address> list = new ArrayList<>();
        sql = "SELECT a.* FROM address a " +
                "JOIN userAddress ua ON a.address_id = ua.address_id " +
                "WHERE ua.user_id = ?";

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            rs = stm.executeQuery();

            while (rs.next()) {
                Address a = new Address();
                a.setAddressId(rs.getInt("address_id"));
                a.setProvinceCode(rs.getString("province_code"));
                a.setDistrictCode(rs.getString("district_code"));
                a.setWardCode(rs.getString("ward_code"));
                a.setDetail(rs.getString("detail"));
                a.setDistanceKm(rs.getDouble("distance_km"));
                list.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return list;
    }

    public boolean addAddress(Address address, int userId) {
        String insertAddressSQL = "INSERT INTO address (province_code, district_code, ward_code, detail, distance_km) VALUES (?, ?, ?, ?, ?)";
        String insertUserAddressSQL = "INSERT INTO userAddress (user_id, address_id) VALUES (?, ?)";

        double distanceKm = 0;
        try {
            Ward ward = new WardDAO().getWardByCode(address.getWardCode());
            if (ward != null && ward.getLatitude() != null && ward.getLongitude() != null) {
                distanceKm = calculateDistance(FPT_LAT, FPT_LNG, ward.getLatitude(), ward.getLongitude());
            }

            connection.setAutoCommit(false);

            // 1. Insert into address
            PreparedStatement insertAddress = connection.prepareStatement(insertAddressSQL, Statement.RETURN_GENERATED_KEYS);
            insertAddress.setString(1, address.getProvinceCode());
            insertAddress.setString(2, address.getDistrictCode());
            insertAddress.setString(3, address.getWardCode());
            insertAddress.setString(4, address.getDetail());
            insertAddress.setDouble(5, distanceKm);
            insertAddress.executeUpdate();

            ResultSet generatedKeys = insertAddress.getGeneratedKeys();
            if (generatedKeys.next()) {
                int addressId = generatedKeys.getInt(1);

                // 2. Insert into userAddress
                PreparedStatement insertUserAddress = connection.prepareStatement(insertUserAddressSQL);
                insertUserAddress.setInt(1, userId);
                insertUserAddress.setInt(2, addressId);
                insertUserAddress.executeUpdate();

                connection.commit();
                return true;
            }

            connection.rollback();
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
