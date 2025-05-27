package dao;

import model.Skill;
import java.sql.*;
import java.util.*;

public class SkillDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/natural_care";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "";

    private static final String SELECT_ALL = "SELECT * FROM skill";
    private static final String INSERT = "INSERT INTO skill (skill_name) VALUES (?)";
    private static final String UPDATE = "UPDATE skill SET skill_name=? WHERE skill_id=?";
    private static final String DELETE = "DELETE FROM skill WHERE skill_id=?";
    private static final String SELECT_BY_ID = "SELECT * FROM skill WHERE skill_id=?";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public List<Skill> getAllSkills(String keyword) {
        List<Skill> skills = new ArrayList<>();
        String sql = (keyword != null && !keyword.trim().isEmpty()) ?
                "SELECT * FROM skill WHERE skill_name LIKE ?" : SELECT_ALL;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (keyword != null && !keyword.trim().isEmpty()) {
                stmt.setString(1, "%" + keyword + "%");
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                skills.add(new Skill(rs.getInt("skill_id"), rs.getString("skill_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skills;
    }

    public void insertSkill(Skill skill) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT)) {
            stmt.setString(1, skill.getSkillName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSkill(Skill skill) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            stmt.setString(1, skill.getSkillName());
            stmt.setInt(2, skill.getSkillId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSkill(int id) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Skill getSkillById(int id) {
        Skill skill = null;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                skill = new Skill(rs.getInt("skill_id"), rs.getString("skill_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skill;
    }
}
