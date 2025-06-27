package dal;

import model.Order;
import model.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends DBContext{
    public int insertOrder(int userId, String note, int addressId) {
        String sql = "INSERT INTO product_order (user_id, order_note, address_id) VALUES (?, ?, ?)";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setString(2, note);
            stm.setInt(3, addressId);
            stm.executeUpdate();
            rs = stm.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(); // từ DBContext
        }
        return -1;
    }

    public void insertOrderDetail(int orderId, int variationId, int quantity, long price) {
        sql = "INSERT INTO order_detail (order_id, variation_id, quantity, price) VALUES (?, ?, ?, ?)";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, orderId);
            stm.setInt(2, variationId);
            stm.setInt(3, quantity);
            stm.setLong(4, price);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM product_order WHERE order_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("order_id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setNote(rs.getString("order_note"));
                    order.setStatusId(rs.getInt("status_id"));
                    order.setCreateAt(rs.getTimestamp("create_at"));
                    order.setAddressId(rs.getInt("address_id"));

                    int shipperId = rs.getInt("shipper_id");
                    order.setShipperId(rs.wasNull() ? null : shipperId);

                    int couponId = rs.getInt("coupon_id");
                    order.setCouponId(rs.wasNull() ? null : couponId);

                    return order;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<OrderDetail> getOrderDetails(int orderId) {
        List<OrderDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM order_detail WHERE order_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderDetail detail = new OrderDetail();
                    detail.setOrderDetailId(rs.getInt("order_detail_id"));
                    detail.setOrderId(rs.getInt("order_id"));
                    detail.setVariationId(rs.getInt("variation_id"));
                    detail.setQuantity(rs.getInt("quantity"));
                    detail.setPrice(rs.getLong("price"));
                    list.add(detail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public void close() {
        try {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
