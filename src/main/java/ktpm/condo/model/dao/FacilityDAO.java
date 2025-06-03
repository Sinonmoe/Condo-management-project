package ktpm.condo.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ktpm.condo.model.entity.Facility;

/**
 * DAO thao tác với bảng 'facility'.
 */
public class FacilityDAO extends DBConnection {

    /**
     * Lấy danh sách tất cả tiện ích.
     */
    public List<Facility> getAll() {
        List<Facility> list = new ArrayList<>();
        String sql = "SELECT * FROM facility";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Facility(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Thêm loại tiện ích mới.
     */
    public boolean add(String name) {
        String sql = "INSERT INTO facility (name) VALUES (?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Xóa tiện ích theo ID.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM facility WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
