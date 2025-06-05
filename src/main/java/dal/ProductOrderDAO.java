package dal;

import model.ProductOrder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductOrderDAO extends DBContext {
    public ProductOrderDAO() {
        super();
    }

    // 1. Đếm số đơn hàng khớp từ khóa
    public int countOrders(String search) {
        String sql =
                "SELECT COUNT(*) " +
                        "FROM product_order po " +
                        "LEFT JOIN user u ON po.user_id = u.user_id " +
                        "WHERE CONCAT(po.order_id, ' ', IFNULL(po.order_note, ''), ' ', u.username) LIKE ?";

        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + (search == null ? "" : search) + "%");
            rs = stm.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 2. Lấy danh sách đơn hàng theo trang
    public List<ProductOrder> getOrdersWithPagination(String search, int page, int pageSize) {
        List<ProductOrder> list = new ArrayList<>();
        String sql =
                "SELECT po.order_id, po.user_id, po.order_note, po.status_id, " +
                        "po.create_at, po.shipper_id, po.address_id, po.coupon_id " +
                        "FROM product_order po " +
                        "LEFT JOIN user u ON po.user_id = u.user_id " +
                        "WHERE CONCAT(po.order_id, ' ', IFNULL(po.order_note, ''), ' ', u.username) LIKE ? " +
                        "ORDER BY po.create_at DESC " +
                        "LIMIT ? OFFSET ?";

        try {
            int offset = (page - 1) * pageSize;
            stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + (search == null ? "" : search) + "%");
            stm.setInt(2, pageSize);
            stm.setInt(3, offset);
            rs = stm.executeQuery();
            while (rs.next()) {
                ProductOrder order = new ProductOrder(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getString("order_note"),
                        rs.getInt("status_id"),
                        rs.getTimestamp("create_at"),
                        rs.getObject("shipper_id") != null ? rs.getInt("shipper_id") : null,
                        rs.getInt("address_id"),
                        rs.getObject("coupon_id") != null ? rs.getInt("coupon_id") : null
                );
                list.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. Lấy đơn hàng theo ID
    public ProductOrder getOrderById(int orderId) {
        String sql = "SELECT * FROM product_order WHERE order_id = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, orderId);
            rs = stm.executeQuery();
            if (rs.next()) {
                return new ProductOrder(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getString("order_note"),
                        rs.getInt("status_id"),
                        rs.getTimestamp("create_at"),
                        rs.getObject("shipper_id") != null ? rs.getInt("shipper_id") : null,
                        rs.getInt("address_id"),
                        rs.getObject("coupon_id") != null ? rs.getInt("coupon_id") : null
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 4. Xoá đơn hàng
    public void deleteOrder(int orderId) {
        String sql = "DELETE FROM product_order WHERE order_id = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, orderId);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
