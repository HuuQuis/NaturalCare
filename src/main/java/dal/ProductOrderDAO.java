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
}
