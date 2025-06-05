package ktpm.condo.model.dao.household_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ktpm.condo.model.dao.DBConnection;
import ktpm.condo.model.entity.Household;

public class HouseholdDAO extends DBConnection {

    // Thêm hộ khẩu
    public boolean addHousehold(Household household) {
        String sql = "INSERT INTO household (apartment_number, household_code, number_of_members) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, household.getApartmentNumber());
            stmt.setString(2, household.getHouseholdCode());
            stmt.setInt(3, household.getNumberOfMembers());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy danh sách toàn bộ hộ khẩu
    public List<Household> getAllHouseholds() {
        List<Household> households = new ArrayList<>();
        String sql = "SELECT * FROM household";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Household hh = new Household();
                hh.setId(rs.getInt("id"));
                hh.setApartmentNumber(rs.getString("apartment_number"));
                hh.setHouseholdCode(rs.getString("household_code"));
                hh.setNumberOfMembers(rs.getInt("number_of_members"));
                households.add(hh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return households;
    }

    // Cập nhật hộ khẩu
    public boolean updateHousehold(Household household) {
        String sql = "UPDATE household SET apartment_number = ?, household_code = ?, number_of_members = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, household.getApartmentNumber());
            stmt.setString(2, household.getHouseholdCode());
            stmt.setInt(3, household.getNumberOfMembers());
            stmt.setInt(4, household.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xoá hộ khẩu
    public boolean deleteHousehold(int id) {
        String sql = "DELETE FROM household WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm hộ khẩu theo ID
    public Household getHouseholdById(int id) {
        String sql = "SELECT * FROM household WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Household(
                    rs.getInt("id"),
                    rs.getString("apartment_number"),
                    rs.getString("household_code"),
                    rs.getInt("number_of_members")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
