package ktpm.condo.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * DAO phục vụ các truy vấn thống kê báo cáo.
 */
public class ReportDAO extends DBConnection {

    /**
     * Lấy tổng số cư dân theo từng tòa, tầng.
     * Giả sử apartment_number có dạng 'Tòa Tầng ...' hoặc tương tự.
     * Trả về map key là 'toa_tang', value là số cư dân.
     */
    public Map<String, Integer> getPopulationByBuildingFloor() {
        Map<String, Integer> result = new HashMap<>();
        String sql = """
            SELECT SUBSTRING_INDEX(apartment_number, '-', 1) AS building_floor, SUM(number_of_members) AS population
            FROM household
            GROUP BY building_floor
        """;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("building_floor"), rs.getInt("population"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Thống kê cơ cấu dân cư theo tuổi nhóm.
     * Trả về map key là nhóm tuổi, value là số lượng cư dân.
     */
    public Map<String, Integer> getPopulationByAgeGroup() {
        Map<String, Integer> result = new HashMap<>();
        String sql = """
            SELECT
                CASE
                    WHEN YEAR(CURDATE()) - YEAR(date_of_birth) < 18 THEN 'Dưới 18'
                    WHEN YEAR(CURDATE()) - YEAR(date_of_birth) BETWEEN 18 AND 30 THEN '18-30'
                    WHEN YEAR(CURDATE()) - YEAR(date_of_birth) BETWEEN 31 AND 50 THEN '31-50'
                    ELSE 'Trên 50'
                END AS age_group,
                COUNT(*) AS count
            FROM citizen
            GROUP BY age_group
        """;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("age_group"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Thống kê cơ cấu dân cư theo giới tính.
     */
    public Map<String, Integer> getPopulationByGender() {
        Map<String, Integer> result = new HashMap<>();
        String sql = "SELECT gender, COUNT(*) AS count FROM citizen GROUP BY gender";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("gender"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Thống kê cơ cấu dân cư theo nghề nghiệp.
     */
    public Map<String, Integer> getPopulationByJob() {
        Map<String, Integer> result = new HashMap<>();
        String sql = "SELECT job, COUNT(*) AS count FROM citizen GROUP BY job";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("job"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Tổng thu phí theo thời gian (theo tháng).
     * Trả về map key tháng (yyyy-MM), value tổng số tiền thu.
     */
    public Map<String, Double> getTotalFeeByMonth() {
        Map<String, Double> result = new HashMap<>();
        String sql = """
            SELECT DATE_FORMAT(due_date, '%Y-%m') AS month, SUM(amount) AS total
            FROM fee
            WHERE status = 'Đã thanh toán'
            GROUP BY month
        """;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("month"), rs.getDouble("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Thống kê lượt sử dụng tiện ích theo loại tiện ích.
     */
    public Map<String, Integer> getFacilityUsageByType() {
        Map<String, Integer> result = new HashMap<>();
        String sql = """
            SELECT f.name, COUNT(fb.id) AS usage_count
            FROM facility_booking fb
            JOIN facility f ON fb.facility_id = f.id
            GROUP BY f.name
        """;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("name"), rs.getInt("usage_count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
