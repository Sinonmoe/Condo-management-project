package ktpm.condo.model.dao.facility_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ktpm.condo.model.dao.DBConnection;
import ktpm.condo.model.entity.FacilityBooking;

/**
 * DAO cho phép thao tác với bảng facility_booking và facility.
 */
public class FacilityBookingDAO extends DBConnection {

    /**
     * Lấy toàn bộ lịch sử đặt tiện ích.
     *
     * @return danh sách lịch sử đặt tiện ích
     */
    public List<FacilityBooking> getAll() {
        List<FacilityBooking> list = new ArrayList<>();
        String sql = """
            SELECT fb.id, f.name, fb.household_id, fb.booking_time
            FROM facility_booking fb
            JOIN facility f ON fb.facility_id = f.id
            ORDER BY fb.booking_time DESC
        """;
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new FacilityBooking(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("household_id"),
                        rs.getTimestamp("booking_time").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lọc lịch sử đặt tiện ích theo tiêu chí cụ thể.
     *
     * @param type    loại lọc: name, household, date, month
     * @param keyword từ khóa tương ứng
     * @return danh sách lịch sử phù hợp
     */
    public List<FacilityBooking> filter(String type, String keyword) {
        List<FacilityBooking> list = new ArrayList<>();
        String sql = """
            SELECT fb.id, f.name, fb.household_id, fb.booking_time
            FROM facility_booking fb
            JOIN facility f ON fb.facility_id = f.id
            WHERE 
        """;

        switch (type) {
            case "name" -> sql += "f.name LIKE ?";
            case "household" -> sql += "fb.household_id = ?";
            case "date" -> sql += "DATE(fb.booking_time) = ?";
            case "month" -> sql += "MONTH(fb.booking_time) = ?";
            default -> throw new IllegalArgumentException("Invalid filter type");
        }

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (type.equals("month")) {
                stmt.setInt(1, Integer.parseInt(keyword));
            } else {
                stmt.setString(1, "%" + keyword + "%");
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new FacilityBooking(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("household_id"),
                        rs.getTimestamp("booking_time").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Thêm một lượt đặt tiện ích mới vào CSDL.
     *
     * @param booking đối tượng chứa thông tin đặt tiện ích
     * @return true nếu thêm thành công, ngược lại false
     */
    public boolean addBooking(FacilityBooking booking) {
    String sql = "INSERT INTO facility_booking (facility_id, household_id, booking_time) VALUES (?, ?, ?)";
    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, booking.getFacilityId());  // Truyền facilityId trực tiếp
        stmt.setInt(2, booking.getHouseholdId());
        stmt.setTimestamp(3, Timestamp.valueOf(booking.getBookingTime()));
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


    /**
     * Xóa một lượt đặt tiện ích theo ID.
     *
     * @param id ID của lượt đặt tiện ích cần xóa
     * @return true nếu xóa thành công, ngược lại false
     */
    public boolean deleteBooking(int id) {
        String sql = "DELETE FROM facility_booking WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}