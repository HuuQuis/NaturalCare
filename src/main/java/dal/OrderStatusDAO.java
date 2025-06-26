package dal;

import model.OrderStatus;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderStatusDAO extends DBContext {
    public List<OrderStatus> getAllStatuses() {
        List<OrderStatus> list = new ArrayList<>();
        String sql = "SELECT * FROM order_status";
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new OrderStatus(
                    rs.getInt("status_id"),
                    rs.getString("status_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
