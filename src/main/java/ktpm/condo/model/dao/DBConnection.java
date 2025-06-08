package ktpm.condo.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/condo_management";
    private static final String DB_USER = "root"; // đổi thành user bạn dùng
    private static final String DB_PASSWORD = "Tgdd0848"; // nếu có mật khẩu thì thêm vào đây

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
