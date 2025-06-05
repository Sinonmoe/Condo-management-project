package ktpm.condo.model.dao.household_dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ktpm.condo.model.dao.DBConnection;
import ktpm.condo.model.entity.Citizen;

/**
 * Lớp DAO thao tác dữ liệu nhân khẩu trong DB.
 */
public class CitizenDAO extends DBConnection {

    /**
     * Thêm một nhân khẩu mới.
     */
    public boolean addCitizen(Citizen citizen) {
        String sql = "INSERT INTO citizen (household_id, name, date_of_birth, gender, job, relationship_to_head) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, citizen.getHouseholdId());
            stmt.setString(2, citizen.getName());
            stmt.setDate(3, Date.valueOf(citizen.getDateOfBirth()));
            stmt.setString(4, citizen.getGender());
            stmt.setString(5, citizen.getJob());
            stmt.setString(6, citizen.getRelationshipToHead());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lấy danh sách nhân khẩu theo ID hộ khẩu.
     */
    public List<Citizen> getCitizensByHouseholdId(int householdId) {
        List<Citizen> list = new ArrayList<>();
        String sql = "SELECT * FROM citizen WHERE household_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, householdId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Citizen c = new Citizen(
                    rs.getInt("id"),
                    rs.getInt("household_id"),
                    rs.getString("name"),
                    rs.getDate("date_of_birth").toLocalDate(),
                    rs.getString("gender"),
                    rs.getString("job"),
                    rs.getString("relationship_to_head")
                );
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Cập nhật thông tin nhân khẩu.
     */
    public boolean updateCitizen(Citizen citizen) {
        String sql = "UPDATE citizen SET name = ?, date_of_birth = ?, gender = ?, job = ?, relationship_to_head = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, citizen.getName());
            stmt.setDate(2, Date.valueOf(citizen.getDateOfBirth()));
            stmt.setString(3, citizen.getGender());
            stmt.setString(4, citizen.getJob());
            stmt.setString(5, citizen.getRelationshipToHead());
            stmt.setInt(6, citizen.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Xoá nhân khẩu theo ID.
     */
    public boolean deleteCitizen(int id) {
        String sql = "DELETE FROM citizen WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
