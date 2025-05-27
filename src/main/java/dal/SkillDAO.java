package dal;

import model.Skill;
import java.sql.*;
import java.util.*;

public class SkillDAO extends DBContext{
    private final String jdbcURL = "jdbc:mysql://localhost:3306/natural_care";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "your_mysql_password"; // thay bằng mật khẩu thật

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public List<Skill> getAllSkills(String keyword, String sortOrder) {
        List<Skill> list = new ArrayList<>();
        String sql = "SELECT * FROM skill";

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += " WHERE skill_name LIKE ?";
        }

        if (sortOrder != null && sortOrder.equalsIgnoreCase("desc")) {
            sql += " ORDER BY skill_name DESC";
        } else {
            sql += " ORDER BY skill_name ASC";
        }

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(1, "%" + keyword + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Skill(rs.getInt("skill_id"), rs.getString("skill_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }
}
