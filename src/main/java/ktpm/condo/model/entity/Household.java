package ktpm.condo.model.entity;

import java.util.List;

import ktpm.condo.model.dao.CitizenDAO;

/**
 * Lớp đại diện cho một Hộ khẩu trong hệ thống quản lý chung cư.
 * Mỗi hộ khẩu có thể liên kết với nhiều nhân khẩu (Citizen).
 */
public class Household {
    private int id;
    private String apartmentNumber;
    private String householdCode;
    private int numberOfMembers;

    /**
     * Constructor mặc định.
     */
    public Household() {}

    /**
     * Constructor đầy đủ thông tin.
     *
     * @param id              ID hộ khẩu
     * @param apartmentNumber Số căn hộ
     * @param householdCode   Mã số hộ khẩu
     * @param numberOfMembers Số lượng thành viên
     */
    public Household(int id, String apartmentNumber, String householdCode, int numberOfMembers) {
        this.id = id;
        this.apartmentNumber = apartmentNumber;
        this.householdCode = householdCode;
        this.numberOfMembers = numberOfMembers;
    }

    /**
     * @return ID của hộ khẩu
     */
    public int getId() {
        return id;
    }

    /**
     * @param id đặt ID cho hộ khẩu
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return Số căn hộ
     */
    public String getApartmentNumber() {
        return apartmentNumber;
    }

    /**
     * @param apartmentNumber đặt số căn hộ
     */
    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    /**
     * @return Mã số hộ khẩu
     */
    public String getHouseholdCode() {
        return householdCode;
    }

    /**
     * @param householdCode đặt mã số hộ khẩu
     */
    public void setHouseholdCode(String householdCode) {
        this.householdCode = householdCode;
    }

    /**
     * @return Số lượng thành viên
     */
    public int getNumberOfMembers() {
        return numberOfMembers;
    }

    /**
     * @param numberOfMembers đặt số lượng thành viên
     */
    public void setNumberOfMembers(int numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
    }

    /**
     * Lấy danh sách nhân khẩu của hộ khẩu này từ CSDL.
     *
     * @return danh sách đối tượng Citizen thuộc hộ khẩu
     */
    public List<Citizen> getMembersFromDB() {
        CitizenDAO dao = new CitizenDAO();
        return dao.getCitizensByHouseholdId(this.id);
    }

    /**
     * Trả về chuỗi đại diện hộ khẩu (ví dụ trong bảng)
     */
    @Override
    public String toString() {
        return "Hộ " + householdCode + " - Căn hộ " + apartmentNumber;
    }
}
