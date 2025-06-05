package ktpm.condo.controller.household_controller;

import java.util.List;

import ktpm.condo.model.entity.Household;
import ktpm.condo.model.service.household_service.HouseholdService;
import ktpm.condo.model.service.household_service.IHouseholdService;

/**
 * Controller điều phối giữa HouseholdPanel và tầng Service.
 */
public class HouseholdController {
    private final IHouseholdService service;

    /**
     * Constructor khởi tạo với implementation HouseholdService mặc định.
     */
    public HouseholdController() {
        this.service = new HouseholdService();
    }

    /**
     * Cho phép tiêm service khác (ví dụ mock trong test).
     */
    public HouseholdController(IHouseholdService service) {
        this.service = service;
    }

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
    public boolean addHousehold(String apartmentNumber, String householdCode) {
    Household h = new Household();
    h.setApartmentNumber(apartmentNumber);
    h.setHouseholdCode(householdCode);
    h.setNumberOfMembers(0);  // Mặc định 0, trigger DB sẽ cập nhật
    return service.add(h);
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
