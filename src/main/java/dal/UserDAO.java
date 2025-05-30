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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return users;
    }

    // This method checks if the user is an admin
    public boolean checkAdmin(String username, String password) {
        try {
            sql = "SELECT * FROM natural_care.user WHERE username = ? AND password = ? AND role_id = 3";
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            rs = stm.executeQuery();
            return rs.next(); // Returns true if user is admin
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // User is not admin
    }


    public boolean checkEmail(String email) {
        try {
            sql = "SELECT * FROM natural_care.user WHERE email = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            rs = stm.executeQuery();
            return rs.next(); // Returns true if email exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Email does not exist
    }

    //Register a new user
    public void registerUser(String username, String password, String email, String firstName, String lastName, String phone) throws SQLException {
        try {
            sql = "INSERT INTO natural_care.user (username, password, email, first_name, last_name, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            stm.setString(3, email);
            stm.setString(4, firstName);
            stm.setString(5, lastName);
            stm.setString(6, phone);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
