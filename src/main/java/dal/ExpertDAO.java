package dal;

import model.ExpertSkill;
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

}
