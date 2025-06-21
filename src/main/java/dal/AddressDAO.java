package dal;

import dal.DBContext;
import model.Address;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO extends DBContext {

    // Lấy tất cả địa chỉ của user
    public List<Address> getAddressesByUserId(int userId) {
        List<Address> addresses = new ArrayList<>();
        sql = "SELECT a.address_id, a.province_code, a.province_name, " +
              "a.district_code, a.district_name, a.ward_code, " +
              "a.ward_name, a.detail " +
              "FROM address a " +
              "INNER JOIN userAddress ua ON a.address_id = ua.address_id " +
              "WHERE ua.user_id = ? " +
              "ORDER BY a.address_id ASC";

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            rs = stm.executeQuery();

            while (rs.next()) {
                Address address = new Address();
                address.setAddressId(rs.getInt("address_id"));
                address.setProvinceCode(rs.getInt("province_code"));
                address.setProvinceName(rs.getString("province_name"));
                address.setDistrictCode(rs.getInt("district_code"));
                address.setDistrictName(rs.getString("district_name"));
                address.setWardCode(rs.getInt("ward_code"));
                address.setWardName(rs.getString("ward_name"));
                address.setDetail(rs.getString("detail"));
                address.setAddressType("OTHER");
                addresses.add(address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return addresses;
    }

    // Lấy địa chỉ mặc định của user
    public Address getDefaultAddress(int userId) {
    sql = "SELECT a.address_id, a.province_code, a.province_name, " +
          "a.district_code, a.district_name, a.ward_code, " +
          "a.ward_name, a.detail " +
          "FROM address a " +
          "INNER JOIN user u ON a.address_id = u.address_id " +
          "WHERE u.user_id = ?";

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            rs = stm.executeQuery();

            if (rs.next()) {
                Address address = new Address();
                address.setAddressId(rs.getInt("address_id"));
                address.setProvinceCode(rs.getInt("province_code"));
                address.setProvinceName(rs.getString("province_name"));
                address.setDistrictCode(rs.getInt("district_code"));
                address.setDistrictName(rs.getString("district_name"));
                address.setWardCode(rs.getInt("ward_code"));
                address.setWardName(rs.getString("ward_name"));
                address.setDetail(rs.getString("detail"));
                address.setAddressType("HOME");
                return address;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return null;
    }

    // Thêm địa chỉ mới
    public boolean addAddress(Address address, int userId) {
        String insertAddressSql = "INSERT INTO address (province_code, province_name, district_code, " +
                "district_name, ward_code, ward_name, detail) VALUES (?, ?, ?, ?, ?, ?, ?)";

        String linkUserAddressSql = "INSERT INTO userAddress (user_id, address_id) VALUES (?, ?)";

        try {
            connection.setAutoCommit(false);

            // Insert address
            stm = connection.prepareStatement(insertAddressSql, Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, address.getProvinceCode());
            stm.setString(2, address.getProvinceName());
            stm.setInt(3, address.getDistrictCode());
            stm.setString(4, address.getDistrictName());
            stm.setInt(5, address.getWardCode());
            stm.setString(6, address.getWardName());
            stm.setString(7, address.getDetail());

            int affectedRows = stm.executeUpdate();

            if (affectedRows > 0) {
                rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    int addressId = rs.getInt(1);

                    // Link user with address
                    stm = connection.prepareStatement(linkUserAddressSql);
                    stm.setInt(1, userId);
                    stm.setInt(2, addressId);
                    stm.executeUpdate();

                    connection.commit();
                    return true;
                }
            }

            connection.rollback();
            return false;

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
                closeResources();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Cập nhật địa chỉ mặc định
    public boolean setDefaultAddress(int userId, int addressId) {
        sql = "UPDATE user SET address_id = ? WHERE user_id = ?";

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, addressId);
            stm.setInt(2, userId);

            int affectedRows = stm.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    // Xóa địa chỉ
    public boolean deleteAddress(int addressId, int userId) {
        String deleteUserAddressSql = "DELETE FROM userAddress WHERE user_id = ? AND address_id = ?";
        String deleteAddressSql = "DELETE FROM address WHERE address_id = ?";

        try {
            connection.setAutoCommit(false);

            // First delete from userAddress
            stm = connection.prepareStatement(deleteUserAddressSql);
            stm.setInt(1, userId);
            stm.setInt(2, addressId);
            stm.executeUpdate();

            // Then delete from address (if no other users are using this address)
            stm = connection.prepareStatement(deleteAddressSql);
            stm.setInt(1, addressId);
            stm.executeUpdate();

            connection.commit();
            return true;

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
                closeResources();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Lấy địa chỉ theo ID
    public Address getAddressById(int addressId) {
        sql = "SELECT address_id, province_code, province_name, " +
              "district_code, district_name, ward_code, " +
              "ward_name, detail " +
              "FROM address " +
              "WHERE address_id = ?";

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, addressId);
            rs = stm.executeQuery();

            if (rs.next()) {
                Address address = new Address();
                address.setAddressId(rs.getInt("address_id"));
                address.setProvinceCode(rs.getInt("province_code"));
                address.setProvinceName(rs.getString("province_name"));
                address.setDistrictCode(rs.getInt("district_code"));
                address.setDistrictName(rs.getString("district_name"));
                address.setWardCode(rs.getInt("ward_code"));
                address.setWardName(rs.getString("ward_name"));
                address.setDetail(rs.getString("detail"));
                return address;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return null;
    }

    // Đóng các resource
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
