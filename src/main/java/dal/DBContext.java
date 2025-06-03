package dal;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DBContext class: Quản lý kết nối đến CSDL thông qua file cấu hình db.properties.
 */
public class DBContext {

    protected Connection connection;        // Kết nối đến cơ sở dữ liệu
    protected PreparedStatement stm;        // Thực hiện câu lệnh SQL
    protected ResultSet rs;                 // Kết quả truy vấn SELECT
    protected String sql;                   // Câu lệnh SQL

    /**
     * Constructor: Khởi tạo kết nối CSDL từ file db.properties.
     */
    public DBContext() {
        try {
            Properties props = new Properties();
            InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties");

            if (input == null) {
                throw new RuntimeException("Không tìm thấy file cấu hình db.properties");
            }

            props.load(input);
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.password");
            String url = props.getProperty("db.url");

            Class.forName("com.mysql.cj.jdbc.Driver"); // Nạp driver MySQL
            connection = DriverManager.getConnection(url, user, pass); // Kết nối đến CSDL
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Kiểm tra trạng thái kết nối đến CSDL.
     * @return true nếu kết nối thành công và chưa bị đóng, ngược lại false.
     */
    public boolean testConnection() {
        if (connection != null) {
            try {
                return !connection.isClosed();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * Hàm main để test thử kết nối CSDL khi chạy độc lập.
     */
    public static void main(String[] args) {
        DBContext db = new DBContext();
        if (db.testConnection()) {
            System.out.println("Kết nối thành công!");
        } else {
            System.out.println("Kết nối thất bại!");
        }
    }
}
