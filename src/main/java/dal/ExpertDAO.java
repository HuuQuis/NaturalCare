package dal;

import model.*;
import model.ProductOrder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpertDAO extends DBContext {
    public ExpertDAO() {
        super();
    }

    public int countExpert(String search, String skill) {
        int count = 0;
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) FROM expertSkill es " +
                        "LEFT JOIN user u ON es.user_id = u.user_id " +
                        "LEFT JOIN skill s ON s.skill_id = es.skill_id " +
                        "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (search != null && !search.isEmpty()) {
            sql.append("AND u.userName LIKE ? ");
            params.add("%" + search + "%");
        }

        if (skill != null && !skill.isEmpty()) {
            sql.append("AND s.skill_name LIKE ? ");
            params.add("%" + skill + "%");
        }

            try {
                stm = connection.prepareStatement(sql.toString());

                for (int i = 0; i < params.size(); i++) {
                    Object param = params.get(i);
                    if (param instanceof String) {
                        stm.setString(i + 1, (String) param);
                    } else {
                        stm.setInt(i + 1, (Integer) param);
                    }
                }

                rs = stm.executeQuery();
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        return count;
    }

    public List<ExpertSkill> getExpertsWithPagination(String search, String skill, int page, int pageSize) {
        List<ExpertSkill> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT es.skill_id, es.user_id, u.username, s.skill_name " +
                        "FROM expertSkill es " +
                        "LEFT JOIN user u ON es.user_id = u.user_id " +
                        "LEFT JOIN skill s ON s.skill_id = es.skill_id " +
                        "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (search != null && !search.isEmpty()) {
            sql.append("AND u.userName LIKE ? ");
            params.add("%" + search + "%");
        }

        if (skill != null && !skill.isEmpty()) {
            sql.append("AND s.skill_name LIKE ? ");
            params.add("%" + skill + "%");
        }

        sql.append("LIMIT ? OFFSET ?");
        int offset = (page - 1) * pageSize;
        params.add(pageSize);
        params.add(offset);

        try {
            stm = connection.prepareStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof String) {
                    stm.setString(i + 1, (String) param);
                } else {
                    stm.setInt(i + 1, (Integer) param);
                }
            }

            rs = stm.executeQuery();
            while (rs.next()) {
                ExpertSkill expertSkill = new ExpertSkill(
                        rs.getInt("es.user_id"),
                        rs.getInt("es.skill_id"),
                        rs.getString("u.username"),
                        rs.getString("s.skill_name")
                );
                list.add(expertSkill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ExpertSkill getExpertDetailByUserId(int userId) {
        String sql = "SELECT es.user_id, u.username, es.skill_id, s.skill_name " +
                "FROM expertSkill es " +
                "JOIN user u ON es.user_id = u.user_id " +
                "JOIN skill s ON es.skill_id = s.skill_id " +
                "WHERE es.user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ExpertSkill expertSkill = new ExpertSkill();
                expertSkill.setUser_id(rs.getInt("user_id"));
                expertSkill.setUser_name(rs.getString("username"));
                expertSkill.setSkill_id(rs.getInt("skill_id"));
                expertSkill.setSkill_name(rs.getString("skill_name"));
                return expertSkill;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy danh sách tất cả kỹ năng để hiển thị dropdown
    public List<Skill> getAllSkills() {
        List<Skill> skills = new ArrayList<>();
        String sql = "SELECT skill_id, skill_name FROM skill";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Skill skill = new Skill();
                skill.setSkillId(rs.getInt("skill_id"));
                skill.setSkillName(rs.getString("skill_name"));
                skills.add(skill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skills;
    }

    // Cập nhật skill cho expert (user_id)
    public void updateExpertSkill(int userId, int skillId) {
        String sql = "UPDATE expertSkill SET skill_id = ? WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, skillId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addExpert(String username, String password, String firstName, String lastName,
                          String email, String phoneNumber, int skillId) {
        String insertUserSql = "INSERT INTO user (username, password, first_name, last_name, email, phone_number, role_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            connection.setAutoCommit(false);

            int userId;
            try (PreparedStatement psUser = connection.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS)) {
                psUser.setString(1, username);
                psUser.setString(2, password); // Lưu password: nên hash trước khi lưu để bảo mật
                psUser.setString(3, firstName);
                psUser.setString(4, lastName);
                psUser.setString(5, email);
                psUser.setString(6, phoneNumber);
                psUser.setInt(7, 6); // role_id = 6 (Expert)
                psUser.executeUpdate();

                ResultSet rs = psUser.getGeneratedKeys();
                if (rs.next()) {
                    userId = rs.getInt(1);
                } else {
                    connection.rollback();
                    throw new SQLException("Failed to insert user, no ID obtained.");
                }
            }

            // Thêm kỹ năng cho chuyên gia
            String insertExpertSkillSql = "INSERT INTO expertSkill (user_id, skill_id) VALUES (?, ?)";
            try (PreparedStatement psExpertSkill = connection.prepareStatement(insertExpertSkillSql)) {
                psExpertSkill.setInt(1, userId);
                psExpertSkill.setInt(2, skillId);
                psExpertSkill.executeUpdate();
            }

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
