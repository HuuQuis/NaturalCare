package dal;

import model.Order;
import model.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends DBContext{
    public int insertOrder(int userId, String note, int addressId, String paymentMethod) {
        sql = "INSERT INTO product_order (user_id, order_note, address_id, payment_method) VALUES (?, ?, ?, ?)";
        try {
            stm = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stm.setInt(1, userId);
            stm.setString(2, note);
            stm.setInt(3, addressId);
            stm.setString(4, paymentMethod);
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

    public int insertOrderVNPAY(int userId, String note, int addressId, String txnRef) {
        sql = "INSERT INTO product_order " +
                "(user_id, order_note, address_id, payment_method, payment_gateway_txn_ref, payment_status_id) " +
                "VALUES (?, ?, ?, 'vnpay', ?, 1)";  // 1 = pending

        try {
            stm = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stm.setInt(1, userId);
            stm.setString(2, note);
            stm.setInt(3, addressId);
            stm.setString(4, txnRef);

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

    public boolean updatePaymentStatus(String txnRef, String txnNo, boolean success) {
        String selectSql = "SELECT status_id FROM product_order WHERE payment_gateway_txn_ref = ?";
        String updateSql = "UPDATE product_order SET " +
                "payment_status_id = ?, " +
                "status_id = ?, " +
                "payment_gateway_transaction_no = ?, " +
                "payment_time = CURRENT_TIMESTAMP " +
                "WHERE payment_gateway_txn_ref = ?";
        try {
            // Kiểm tra trạng thái đơn hiện tại
            PreparedStatement selectStm = connection.prepareStatement(selectSql);
            selectStm.setString(1, txnRef);
            rs = selectStm.executeQuery();

            if (rs.next()) {
                int currentStatusId = rs.getInt("status_id");

                // Nếu đơn đã bị xử lý hoặc hủy bởi staff, không cập nhật nữa
                if (!success && currentStatusId != 1) {
                    return false;
                }

                int newStatusId = success ? 2 : 5; // processing : cancelled_by_user
                int newPaymentStatusId = success ? 2 : 3; // paid : failed

                PreparedStatement updateStm = connection.prepareStatement(updateSql);
                updateStm.setInt(1, newPaymentStatusId);
                updateStm.setInt(2, newStatusId);
                updateStm.setString(3, txnNo);
                updateStm.setString(4, txnRef);

                return updateStm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public Integer getOrderIdByTxnRef(String txnRef) {
        sql = "SELECT order_id FROM product_order WHERE payment_gateway_txn_ref = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, txnRef);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("order_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
