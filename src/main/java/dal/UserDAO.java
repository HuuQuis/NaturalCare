package dal;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DBContext {

    public User getUserById(int userId) {
        String sql = "SELECT * FROM user WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone_number"));
                user.setRole(rs.getInt("role_id"));
                user.setAvatar(rs.getString("user_image"));
                
                // Thêm assigned_staff_id
                int assignedStaffId = rs.getInt("assigned_staff_id");
                if (!rs.wasNull()) {
                    user.setAssignedStaffId(assignedStaffId);
                }
                
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUserProfile(User user) {
        String sql = "UPDATE user SET first_name = ?, last_name = ?, phone_number = ? WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPhone());
            ps.setInt(4, user.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
                user.setId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone_number"));
                user.setRole(rs.getInt("role_id"));
                
                // Thêm assigned_staff_id
                int assignedStaffId = rs.getInt("assigned_staff_id");
                if (!rs.wasNull()) {
                    user.setAssignedStaffId(assignedStaffId);
                }
                
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
            // assign to staff with least customers
            Integer assignedStaffId = getStaffWithLeastCustomers();
            
            sql = "INSERT INTO natural_care.user (username, password, email, first_name, last_name, phone_number, role_id, assigned_staff_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            stm.setString(3, email);
            stm.setString(4, firstName);
            stm.setString(5, lastName);
            stm.setString(6, phone);
            stm.setInt(7, 1); // customer role
            if (assignedStaffId != null) {
                stm.setInt(8, assignedStaffId);
            } else {
                stm.setNull(8, java.sql.Types.INTEGER);
            }
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // get staff with least customers
    private Integer getStaffWithLeastCustomers() {
        try {
            sql = "SELECT s.user_id, COUNT(c.user_id) as customer_count " +
                  "FROM natural_care.user s " +
                  "INNER JOIN ( " +
                    "SELECT user_id FROM natural_care.contract " +
                    "WHERE contract_status = 'active' " +
                    "GROUP BY user_id " +
                  ") ct ON s.user_id = ct.user_id " +
                    // left join to count staff even if no customers
                  "LEFT JOIN natural_care.user c ON s.user_id = c.assigned_staff_id " +
                  "WHERE s.role_id = 2 " +
                  "GROUP BY s.user_id " +
                  "ORDER BY customer_count ASC " +
                  "LIMIT 1";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    public List<User> getAllShippers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE role_id = 7";
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getInt("role_id"),
                        rs.getString("user_image")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getUserNameById(int userId) {
        String sql = "SELECT username FROM user WHERE user_id = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    // Get customers with pagination and search
    public List<User> getAllCustomersWithPagination(String search, int offset, int limit) {
        List<User> list = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM user WHERE role_id = 1");
        
        // Search conditions
        if (search != null && !search.trim().isEmpty()) {
            sqlBuilder.append(" AND (username LIKE ? OR email LIKE ? OR first_name LIKE ? OR last_name LIKE ? OR phone_number LIKE ?)");
        }
        
        sqlBuilder.append(" ORDER BY user_id DESC LIMIT ? OFFSET ?");
        String sql = sqlBuilder.toString();
        
        try {
            stm = connection.prepareStatement(sql);
            int paramIndex = 1;
            
            // Set search parameters
            if (search != null && !search.trim().isEmpty()) {
                String searchPattern = "%" + search.trim() + "%";
                stm.setString(paramIndex++, searchPattern); // username
                stm.setString(paramIndex++, searchPattern); // email
                stm.setString(paramIndex++, searchPattern); // first_name
                stm.setString(paramIndex++, searchPattern); // last_name
                stm.setString(paramIndex++, searchPattern); // phone_number
            }
            
            // Set pagination parameters
            stm.setInt(paramIndex++, limit);
            stm.setInt(paramIndex, offset);
            
            rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getInt("role_id"),
                        rs.getString("user_image")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Count total customers (for pagination)
    public int countCustomers(String search) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(*) FROM user WHERE role_id = 1");
        
        // Add search conditions
        if (search != null && !search.trim().isEmpty()) {
            sqlBuilder.append(" AND (username LIKE ? OR email LIKE ? OR first_name LIKE ? OR last_name LIKE ? OR phone_number LIKE ?)");
        }
        
        String sql = sqlBuilder.toString();
        
        try {
            stm = connection.prepareStatement(sql);
            
            // Set search parameters
            if (search != null && !search.trim().isEmpty()) {
                String searchPattern = "%" + search.trim() + "%";
                stm.setString(1, searchPattern); // username
                stm.setString(2, searchPattern); // email
                stm.setString(3, searchPattern); // first_name
                stm.setString(4, searchPattern); // last_name
                stm.setString(5, searchPattern); // phone_number
            }
            
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
