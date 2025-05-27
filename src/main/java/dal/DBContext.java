package dal;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBContext {

    protected Connection connection;
    protected PreparedStatement stm;
    protected ResultSet rs;
    protected String sql;

    public DBContext() {
        try {
            Properties props = new Properties();
            InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties");
            if (input == null) {
                throw new RuntimeException("Unable to find db.properties");
            }
            props.load(input);

            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.password");
            String url = props.getProperty("db.url");
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException | RuntimeException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

    public static void main(String[] args) {
        DBContext db = new DBContext();
        if (db.testConnection()) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Connection failed!");
        }
    }
}