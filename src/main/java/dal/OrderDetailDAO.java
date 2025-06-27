package dal;

import model.OrderDetailItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO extends DBContext {
    public List<OrderDetailItem> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetailItem> list = new ArrayList<>();
        String sql = "SELECT od.total_quantity, od.total_price, " +
                     "pv.product_image, pv.price, pv.sell_price, " +
                     "p.product_name, c.color_name, s.size_name " +
                     "FROM order_detail od " +
                     "JOIN product_variation pv ON od.variation_id = pv.variation_id " +
                     "JOIN product p ON pv.product_id = p.product_id " +
                     "LEFT JOIN color c ON pv.color_id = c.color_id " +
                     "LEFT JOIN size s ON pv.size_id = s.size_id " +
                     "WHERE od.order_id = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, orderId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                OrderDetailItem item = new OrderDetailItem();
                item.setQuantity(rs.getInt("total_quantity"));
                item.setTotalPrice(rs.getDouble("total_price"));
                item.setProductImage(rs.getString("product_image"));
                item.setPrice(rs.getDouble("price"));
                item.setSellPrice(rs.getDouble("sell_price"));
                item.setProductName(rs.getString("product_name"));
                item.setColorName(rs.getString("color_name"));
                item.setSizeName(rs.getString("size_name"));
                list.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}