package dal;

import model.ProductOrder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductOrderDAO extends DBContext {
    public ProductOrderDAO() {
        super();
    }

    public int countOrders(String search, String status, String fromDate, String toDate) {
        int count = 0;
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) FROM product_order po " +
                        "LEFT JOIN user u ON po.user_id = u.user_id " +
                        "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (search != null && !search.isEmpty()) {
            sql.append("AND CONCAT(po.order_id, ' ', IFNULL(po.order_note, ''), ' ', u.username) LIKE ? ");
            params.add("%" + search + "%");
        }
        if (status != null && !status.isEmpty()) {
            sql.append("AND po.status_id = ? ");
            params.add(Integer.parseInt(status));
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append("AND DATE(po.create_at) >= ? ");
            params.add(fromDate);
        }
        if (toDate != null && !toDate.isEmpty()) {
            sql.append("AND DATE(po.create_at) <= ? ");
            params.add(toDate);
        }

        try {
            stm = connection.prepareStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof String) {
                    stm.setString(i + 1, (String) param);
                } else {
                    stm.setInt(i + 1, (Integer) param);
                }
            }

            rs = stm.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


    public List<ProductOrder> getOrdersWithPagination(String search, String status, String fromDate, String toDate, int page, int pageSize) {
        List<ProductOrder> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT po.order_id, po.user_id, po.order_note, po.status_id, " +
                        "po.create_at, po.shipper_id, po.address_id, po.coupon_id " +
                        "FROM product_order po " +
                        "LEFT JOIN user u ON po.user_id = u.user_id " +
                        "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (search != null && !search.isEmpty()) {
            sql.append("AND CONCAT(po.order_id, ' ', IFNULL(po.order_note, ''), ' ', u.username) LIKE ? ");
            params.add("%" + search + "%");
        }
        if (status != null && !status.isEmpty()) {
            sql.append("AND po.status_id = ? ");
            params.add(Integer.parseInt(status));
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append("AND DATE(po.create_at) >= ? ");
            params.add(fromDate);
        }
        if (toDate != null && !toDate.isEmpty()) {
            sql.append("AND DATE(po.create_at) <= ? ");
            params.add(toDate);
        }

        sql.append("ORDER BY po.create_at DESC LIMIT ? OFFSET ?");
        int offset = (page - 1) * pageSize;
        params.add(pageSize);
        params.add(offset);

        try {
            stm = connection.prepareStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof String) {
                    stm.setString(i + 1, (String) param);
                } else {
                    stm.setInt(i + 1, (Integer) param);
                }
            }

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
    
    public void updateOrder(int orderId, String note, int statusId, Integer shipperId, int addressId, Integer couponId) {
        String sql = "UPDATE product_order SET order_note = ?, status_id = ?, shipper_id = ?, address_id = ?, coupon_id = ? WHERE order_id = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, note);
            stm.setInt(2, statusId);

            if (shipperId != null) stm.setInt(3, shipperId);
            else stm.setNull(3, Types.INTEGER);

            stm.setInt(4, addressId);

            if (couponId != null) stm.setInt(5, couponId);
            else stm.setNull(5, Types.INTEGER);

            stm.setInt(6, orderId);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<ProductOrder> getOrdersByUserId(int userId) {
        List<ProductOrder> list = new ArrayList<>();
        String sql = "SELECT * FROM product_order WHERE user_id = ? ORDER BY create_at DESC";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
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
                        rs.getObject("coupon_id") != null ? rs.getInt("coupon_id") : null);
                list.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return list;
    }
    
    public int countOrdersByUser(int userId, String search, String status, String fromDate, String toDate) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM product_order WHERE user_id = ?");
        List<Object> params = new ArrayList<>();
        params.add(userId);

        if (search != null && !search.isEmpty()) {
            sql.append(" AND order_note LIKE ?");
            params.add("%" + search + "%");
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND status_id = ?");
            params.add(Integer.parseInt(status));
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append(" AND create_at >= ?");
            params.add(Date.valueOf(fromDate));
        }
        if (toDate != null && !toDate.isEmpty()) {
            sql.append(" AND create_at <= ?");
            params.add(Date.valueOf(toDate));
        }

        try {
            PreparedStatement stm = connection.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                stm.setObject(i + 1, params.get(i));
            }
            ResultSet rs = stm.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<ProductOrder> getOrdersByUserWithPagination(
            int userId, String search, String status, String fromDate, String toDate, int page, int pageSize) {

        List<ProductOrder> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT po.order_id, po.user_id, po.order_note, po.status_id, " +
                        "po.create_at, po.shipper_id, po.address_id, po.coupon_id " +
                        "FROM product_order po " +
                        "LEFT JOIN user u ON po.user_id = u.user_id " +
                        "WHERE po.user_id = ? "
        );

        List<Object> params = new ArrayList<>();
        params.add(userId); // Bắt buộc có điều kiện user

        if (search != null && !search.isEmpty()) {
            sql.append("AND CONCAT(po.order_id, ' ', IFNULL(po.order_note, ''), ' ', u.username) LIKE ? ");
            params.add("%" + search + "%");
        }
        if (status != null && !status.isEmpty()) {
            sql.append("AND po.status_id = ? ");
            params.add(Integer.parseInt(status));
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append("AND DATE(po.create_at) >= ? ");
            params.add(fromDate);
        }
        if (toDate != null && !toDate.isEmpty()) {
            sql.append("AND DATE(po.create_at) <= ? ");
            params.add(toDate);
        }

        sql.append("ORDER BY po.create_at ASC LIMIT ? OFFSET ?");
        int offset = (page - 1) * pageSize;
        params.add(pageSize);
        params.add(offset);

        try {
            stm = connection.prepareStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof String) {
                    stm.setString(i + 1, (String) param);
                } else {
                    stm.setInt(i + 1, (Integer) param);
                }
            }

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

    public void markOrderAsPaid(String txnRef, String method, String transactionNo) {
        sql = "UPDATE product_order " +
                "SET payment_status_id = 2, payment_method = ?, payment_gateway_transaction_no = ?, payment_time = NOW() " +
                "WHERE payment_gateway_txn_ref = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, method);                  // vnpay | momo | zalopay
            stm.setString(2, transactionNo);           // mã giao dịch trả về từ cổng
            stm.setString(3, txnRef);                  // mã đơn hàng bạn đã gửi sang cổng
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int createOrderWithPayment(ProductOrder order) {
        sql = "INSERT INTO product_order (user_id, order_note, status_id, create_at, shipper_id, address_id, coupon_id, " +
                "payment_method, payment_gateway_txn_ref, payment_status_id) " +
                "VALUES (?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?)";

        try {
            stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, order.getUserId());
            stm.setString(2, order.getNote());
            stm.setInt(3, order.getStatusId());

            if (order.getShipperId() != null) stm.setInt(4, order.getShipperId());
            else stm.setNull(4, Types.INTEGER);

            stm.setInt(5, order.getAddressId());

            if (order.getCouponId() != null) stm.setInt(6, order.getCouponId());
            else stm.setNull(6, Types.INTEGER);

            stm.setString(7, order.getPaymentMethod());
            stm.setString(8, order.getPaymentGatewayTxnRef());
            stm.setInt(9, 1); // payment_status_id = 1 (chờ thanh toán)

            int affectedRows = stm.executeUpdate();
            if (affectedRows > 0) {
                rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // Trả về order_id vừa tạo
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
