package ktpm.condo.controller;

import java.util.List;

import ktpm.condo.model.entity.Household;
import ktpm.condo.model.service.HouseholdService;

/**
 * Controller điều phối giữa HouseholdPanel và tầng Service.
 */
public class HouseholdController {
    private final HouseholdService service = new HouseholdService();

    /**
     * Lấy danh sách toàn bộ hộ khẩu.
     *
     * @return danh sách hộ khẩu
     */
    public List<Household> getAllHouseholds() {
        return service.getAll();
    }

    /**
     * Thêm một hộ khẩu mới.
     *
     * @param apartmentNumber số căn hộ
     * @param householdCode mã hộ khẩu
     * @param numberOfMembers số thành viên
     * @return true nếu thêm thành công
     */
    public boolean addHousehold(String apartmentNumber, String householdCode, String numberOfMembers) {
        try {
            int members = Integer.parseInt(numberOfMembers);
            Household h = new Household();
            h.setApartmentNumber(apartmentNumber);
            h.setHouseholdCode(householdCode);
            h.setNumberOfMembers(members);
            return service.add(h);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Xoá một hộ khẩu theo ID.
     *
     * @param id ID hộ khẩu
     * @return true nếu xoá thành công
     */
    public boolean deleteHousehold(int id) {
        return service.delete(id);
    }

    /**
     * Lấy thông tin hộ khẩu theo ID.
     *
     * @param id ID hộ khẩu
     * @return đối tượng Household nếu tìm thấy
     */
    public Household getHouseholdById(int id) {
        return service.getById(id);
    }
}