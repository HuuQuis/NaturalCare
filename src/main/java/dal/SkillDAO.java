package dal;

import model.Skill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillDAO extends DBContext {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/natural_care";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "Tanamson260904";
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public List<Skill> getAllSkills() {
        List<Skill> skills = new ArrayList<>();
        String sql = "SELECT * FROM skill";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Skill skill = new Skill(rs.getInt("skill_id"), rs.getString("skill_name"));
                skills.add(skill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return skills;
    }

    public Skill getSkillById(int id) {
        String sql = "SELECT * FROM skill WHERE skill_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Skill(rs.getInt("skill_id"), rs.getString("skill_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertSkill(Skill skill) {
        String sql = "INSERT INTO skill (skill_name) VALUES (?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, skill.getSkillName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSkill(Skill skill) {
        String sql = "UPDATE skill SET skill_name = ? WHERE skill_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, skill.getSkillName());
            ps.setInt(2, skill.getSkillId());
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
