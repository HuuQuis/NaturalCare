/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.sun.jdi.connect.spi.Connection;
import java.beans.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Order;

/**
 *
 * @author admin
 */
public class OrderDAO {
    private Connection conn;

    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT id, customer_name, created_date, status FROM orders";
//        try (Statement stmt = conn.createStatement(); 
//                ResultSet rs = stmt.executeQuery(sql)) {
//            while (rs.next()) {
//                Order o = new Order(
//                    rs.getString("id"),
//                    rs.getString("customer_name"),
//                    rs.getString("created_date"),
//                    rs.getString("status")
//                );
//                list.add(o);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return list;
    }
}
