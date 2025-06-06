package dal;

import model.Skill;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillDAO {
    private final DBContext dbContext = new DBContext();

    public List<Skill> getAll(String search, String sort, int offset, int limit) {
        List<Skill> list = new ArrayList<>();
        String sql = "SELECT * FROM skill WHERE skill_name LIKE ? ORDER BY skill_name " + (sort.equalsIgnoreCase("desc") ? "DESC" : "ASC") + " LIMIT ?, ?";
        try (PreparedStatement ps = dbContext.connection.prepareStatement(sql)) {
            ps.setString(1, "%" + search + "%");
            ps.setInt(2, offset);
            ps.setInt(3, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Skill skill = new Skill();
                skill.setSkillId(rs.getInt("skill_id"));
                skill.setSkillName(rs.getString("skill_name"));
                list.add(skill);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving skill list: " + e.getMessage());
        }
        return list;
    }

    public int getTotal(String search) {
        String sql = "SELECT COUNT(*) FROM skill WHERE skill_name LIKE ?";
        try (PreparedStatement ps = dbContext.connection.prepareStatement(sql)) {
            ps.setString(1, "%" + search + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error counting skills: " + e.getMessage());
        }
        return 0;
    }

    public Skill getById(int id) {
        String sql = "SELECT * FROM skill WHERE skill_id = ?";
        try (PreparedStatement ps = dbContext.connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Skill skill = new Skill();
                skill.setSkillId(rs.getInt("skill_id"));
                skill.setSkillName(rs.getString("skill_name"));
                return skill;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving skill ID " + id + ": " + e.getMessage());
        }
        return null;
    }

    public int insert(Skill skill) throws SQLException {
        String sql = "INSERT INTO skill (skill_name) VALUES (?)";
        try (PreparedStatement ps = dbContext.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, skill.getSkillName());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Failed to insert skill: " + skill.getSkillName());
    }

    public void update(Skill skill) {
        String sql = "UPDATE skill SET skill_name = ? WHERE skill_id = ?";
        try (PreparedStatement ps = dbContext.connection.prepareStatement(sql)) {
            ps.setString(1, skill.getSkillName());
            ps.setInt(2, skill.getSkillId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating skill ID " + skill.getSkillId() + ": " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM skill WHERE skill_id = ?";
        try (PreparedStatement ps = dbContext.connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting skill ID " + id + ": " + e.getMessage());
        }
    }

    public boolean isSkillInUse(int id) {
        String sql = "SELECT COUNT(*) FROM expertSkill WHERE skill_id = ?";
        try (PreparedStatement ps = dbContext.connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking skill ID " + id + " usage: " + e.getMessage());
        }
        return false;
    }

    public boolean isDuplicateSkillName(String skillName, int skillId) {
        String sql = "SELECT COUNT(*) FROM skill WHERE LOWER(skill_name) = LOWER(?) AND skill_id != ?";
        try (PreparedStatement ps = dbContext.connection.prepareStatement(sql)) {
            ps.setString(1, skillName.trim());
            ps.setInt(2, skillId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking duplicate skill name: " + e.getMessage());
        }
        return false;
    }
}