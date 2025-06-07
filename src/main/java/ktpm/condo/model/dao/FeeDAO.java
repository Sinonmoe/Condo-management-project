package ktpm.condo.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ktpm.condo.model.entity.Fee;

/**
 * DAO quản lý thao tác dữ liệu với bảng `fee` trong CSDL.
 */
public class FeeDAO extends DBConnection {

    /**
     * Lấy danh sách tất cả các khoản phí.
     */
    public List<Fee> getAll() {
        List<Fee> list = new ArrayList<>();
        String sql = "SELECT * FROM fee";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(extractFee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * Lấy danh sách phí theo trạng thái (Đã hoặc Chưa thanh toán).
     *
     * @param status trạng thái phí
     * @return danh sách phí theo trạng thái
     */
    public List<Fee> getByStatus(String status) {
        List<Fee> list = new ArrayList<>();
        String sql = "SELECT * FROM fee WHERE status = ?";
        if ("Chưa thanh toán".equals(status)) {
            sql += " AND due_date >= CURRENT_DATE";
        }
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(extractFee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Fee> getOverdueUnpaidFees(LocalDate today) {
        List<Fee> list = new ArrayList<>();
        String sql = "SELECT * FROM fee WHERE status = 'Chưa thanh toán' AND due_date < ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(today));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(extractFee(rs)); // dùng lại hàm đã có
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lọc phí theo ID hộ khẩu và/hoặc loại phí (type).
     */
    public List<Fee> filter(Integer householdId, String typeLike, String status) {
        List<Fee> list = new ArrayList<>();
        String sql = "SELECT * FROM fee WHERE status = ?"
                   + (householdId != null ? " AND household_id = ?" : "")
                   + (typeLike != null && !typeLike.isEmpty() ? " AND type LIKE ?" : "");

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            int index = 2;
            if (householdId != null) {
                stmt.setInt(index++, householdId);
            }
            if (typeLike != null && !typeLike.isEmpty()) {
                stmt.setString(index, "%" + typeLike + "%");
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(extractFee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Cập nhật trạng thái phí.
     *
     * @param id ID phí
     * @param newStatus trạng thái mới
     */
    public boolean updateStatus(int id, String newStatus) {
        String sql = "UPDATE fee SET status = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean add(Fee fee) {
        String sql = "INSERT INTO fee (household_id, type, amount, due_date, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, fee.getHouseholdId());
            stmt.setString(2, fee.getType());
            stmt.setDouble(3, fee.getAmount());
            stmt.setDate(4, Date.valueOf(fee.getDueDate()));
            stmt.setString(5, fee.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM fee WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Fee extractFee(ResultSet rs) throws SQLException {
        return new Fee(
            rs.getInt("id"),
            rs.getInt("household_id"),
            rs.getString("type"),
            rs.getDouble("amount"),
            rs.getDate("due_date").toLocalDate(),
            rs.getString("status")
        );
    }
}
