package dal;

import model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DBContext{

    public User getUser(String username, String pass) throws SQLException {
        User user = null;
        try {
            sql = "SELECT * FROM natural_care.user WHERE email = ? AND password = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, pass);
            rs = stm.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
        }
        return user;
    }

    public List<User> checkUser(String username, String password) {
        List<User> users = new ArrayList<>();
        try {
            sql = "SELECT * FROM natural_care.user WHERE username = ? AND password = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            rs = stm.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public boolean checkAdmin(String username, String password) {
        try {
            sql = "SELECT * FROM natural_care.user WHERE username = ? AND password = ? AND role_id = 3";
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            rs = stm.executeQuery();
            return rs.next(); // If a record is found, the user is an admin
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
