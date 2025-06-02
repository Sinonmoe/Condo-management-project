package ktpm.condo.model.dao;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class kiểm tra kết nối đến cơ sở dữ liệu MySQL.
 */
public class TestDBConnection {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Kết nối thành công tới cơ sở dữ liệu.");
            } else {
                System.out.println("❌ Không thể kết nối tới cơ sở dữ liệu.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi kết nối: " + e.getMessage());
        }
    }
}
