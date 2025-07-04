package dal;

import model.Order;
import model.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends DBContext{
    public int insertOrder(int userId, String note, int addressId) {
        sql = "INSERT INTO product_order (user_id, order_note, address_id) VALUES (?, ?, ?)";
        try {
            stm = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stm.setInt(1, userId);
            stm.setString(2, note);
            stm.setInt(3, addressId);
            stm.executeUpdate();

            rs = stm.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        }
    }

    public Order getOrderById(int orderId) {
        sql = "SELECT o.*, s.status_name FROM product_order o JOIN order_status s ON o.status_id = s.status_id WHERE o.order_id = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, orderId);
            rs = stm.executeQuery();
            if (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setNote(rs.getString("order_note"));
                order.setStatusId(rs.getInt("status_id"));
                order.setCreateAt(rs.getTimestamp("create_at"));
                order.setAddressId(rs.getInt("address_id"));
                order.setStatusName(rs.getString("status_name")); // <-- thêm dòng này
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<OrderDetail> getOrderDetails(int orderId) {
        List<OrderDetail> list = new ArrayList<>();
        sql = "SELECT * FROM order_detail WHERE order_id = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, orderId);
            rs = stm.executeQuery();
                while (rs.next()) {
                    OrderDetail detail = new OrderDetail();
                    detail.setOrderDetailId(rs.getInt("order_detail_id"));
                    detail.setOrderId(rs.getInt("order_id"));
                    detail.setVariationId(rs.getInt("variation_id"));
                    detail.setQuantity(rs.getInt("quantity"));
                    detail.setPrice(rs.getLong("price"));
                    list.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
