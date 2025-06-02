package dal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Skill;

public class SkillDAO extends DBContext {

    public SkillDAO() {
        super(); // gọi constructor DBContext để mở connection
    }

    public List<Skill> getAll(String search, String sort, int offset, int fetch) throws SQLException {
        List<Skill> list = new ArrayList<>();
        sql = "SELECT * FROM skill WHERE skill_name LIKE ? ORDER BY skill_name "
                + (sort.equalsIgnoreCase("desc") ? "DESC" : "ASC")
                + " LIMIT ? OFFSET ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + search + "%");
            stm.setInt(2, fetch);
            stm.setInt(3, offset);
            rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new Skill(rs.getInt("skill_id"), rs.getString("skill_name")));
            }
        } finally {
            close();
        }
        return list;
    }

    public int getTotal(String search) throws SQLException {
        int count = 0;
        sql = "SELECT COUNT(*) FROM skill WHERE skill_name LIKE ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + search + "%");
            rs = stm.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } finally {
            close();
        }
        return count;
    }

    public Skill getById(int id) throws SQLException {
        Skill skill = null;
        sql = "SELECT * FROM skill WHERE skill_id = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            if (rs.next()) {
                skill = new Skill(rs.getInt("skill_id"), rs.getString("skill_name"));
            }
        } finally {
            close();
        }
        return skill;
    }

    public void insert(Skill skill) throws SQLException {
        sql = "INSERT INTO skill(skill_name) VALUES(?)";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, skill.getSkillName());
            stm.executeUpdate();
        } finally {
            close();
        }
    }

    public void update(Skill skill) throws SQLException {
        sql = "UPDATE skill SET skill_name = ? WHERE skill_id = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, skill.getSkillName());
            stm.setInt(2, skill.getSkillId());
            stm.executeUpdate();
        } finally {
            close();
        }
    }

    public void delete(int id) throws SQLException {
        sql = "DELETE FROM skill WHERE skill_id = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } finally {
            close();
        }
    }

    private void close() {
        try {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            // Note: Không đóng connection ở đây, connection dùng lại nhiều nơi trong DAO
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
