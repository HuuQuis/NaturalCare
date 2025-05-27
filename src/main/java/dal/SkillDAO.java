package dal;

import model.Skill;
import java.sql.*;
import java.util.*;

public class SkillDAO extends DBContext {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/natural_care";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "your_mysql_password_here";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public List<Skill> getAllSkills(String keyword) {
        List<Skill> list = new ArrayList<>();
        String sql = "SELECT * FROM skill WHERE skill_name LIKE ? ORDER BY skill_id";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Skill skill = new Skill(
                        rs.getInt("skill_id"),
                        rs.getString("skill_name")
                );
                list.add(skill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Skill getSkillById(int id) {
        String sql = "SELECT * FROM skill WHERE skill_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Skill(rs.getInt("skill_id"), rs.getString("skill_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addSkill(String name) {
        String sql = "INSERT INTO skill (skill_name) VALUES (?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSkill(int id, String name) {
        String sql = "UPDATE skill SET skill_name = ? WHERE skill_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSkill(int id) {
        String sql = "DELETE FROM skill WHERE skill_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}