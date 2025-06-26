package dal;

import model.Coupon;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CouponDAO extends DBContext {
    public List<Coupon> getAllCoupons() {
        List<Coupon> list = new ArrayList<>();
        String sql = "SELECT * FROM coupon WHERE is_active = 1";
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Coupon cp = new Coupon();
                cp.setCouponId(rs.getInt("coupon_id"));
                cp.setCode(rs.getString("code"));
                cp.setDiscountPercent(rs.getInt("discount_percent"));
                cp.setMinOrderAmount(rs.getInt("min_order_amount"));
                cp.setValidFrom(rs.getDate("valid_from"));
                cp.setValidTo(rs.getDate("valid_to"));
                list.add(cp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
