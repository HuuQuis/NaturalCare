package dal;

import model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DBContext {

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

    public User checkUser(String username, String password) {
        try {
            sql = "SELECT * FROM natural_care.user WHERE username = ? AND password = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            rs = stm.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getInt("role_id"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    public boolean checkManager(String username, String password) {
        try {
            sql = "SELECT * FROM natural_care.user WHERE username = ? AND password = ? AND role_id = 4";
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

    public boolean checkStaff(String username, String password) {
        try {
            sql = "SELECT * FROM natural_care.user WHERE username = ? AND password = ? AND role_id = 2";
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            rs = stm.executeQuery();
            return rs.next(); // Returns true if user is staff
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // User is not staff
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

    public boolean checkUsernameExists(String username) {
        try {
            sql = "SELECT * FROM natural_care.user WHERE username = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            rs = stm.executeQuery();
            return rs.next(); // Returns true if username exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Username does not exist
    }

    //Register a new user
    public void registerUser(String username, String password, String email, String firstName, String lastName, String phone) throws SQLException {
        try {
            sql = "INSERT INTO natural_care.user (username, password, email, first_name, last_name, phone_number, role_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            stm.setString(3, email);
            stm.setString(4, firstName);
            stm.setString(5, lastName);
            stm.setString(6, phone);
            stm.setInt(7, 1); // customer default role
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkEmailExists(String email) {
        // Check if the email exists in the database
        try {
            sql = "SELECT * FROM natural_care.user WHERE email = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            rs = stm.executeQuery();
            return rs.next(); // Returns true if email exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void saveResetToken(String email, String token) {
        try {
            // remove old token if exists
            sql = "UPDATE natural_care.user SET reset_token = NULL, reset_token_expiry = NULL WHERE email = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            stm.executeUpdate();

            // save new token with expiry time
            sql = "UPDATE natural_care.user SET reset_token = ?, reset_token_expiry = DATE_ADD(NOW(), INTERVAL 1 HOUR) WHERE email = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, token);
            stm.setString(2, email);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isValidResetToken(String token) {
        try {
            // check if the token exists and is not expired
            sql = "SELECT * FROM natural_care.user WHERE reset_token = ? AND reset_token_expiry > NOW()";
            stm = connection.prepareStatement(sql);
            stm.setString(1, token);
            rs = stm.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updatePassword(String token, String newPassword) {
        try {
            // update the password and clear the reset token
            sql = "UPDATE natural_care.user SET password = ?, reset_token = NULL, reset_token_expiry = NULL WHERE reset_token = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, newPassword);
            stm.setString(2, token);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
