package ktpm.condo.controller.household_controller;

import java.time.LocalDate;
import java.util.List;

import ktpm.condo.model.entity.Citizen;
import ktpm.condo.model.service.household_service.CitizenService;
import ktpm.condo.model.service.household_service.ICitizenService;

/**
 * Controller xử lý logic giữa giao diện CitizenPanel và tầng service.
 */
public class CitizenController {
    private final ICitizenService service;

    /**
     * Constructor khởi tạo với implementation CitizenService mặc định.
     */
    public CitizenController() {
        this.service = new CitizenService();
    }

    /**
     * Cho phép tiêm service khác (ví dụ mock trong test).
     */
    public CitizenController(ICitizenService service) {
        this.service = service;
    }

    /**
     * Lấy danh sách nhân khẩu của hộ khẩu.
     */
    public List<Citizen> getByHousehold(int householdId) {
        return service.getByHousehold(householdId);
    }

    /**
     * Thêm một nhân khẩu mới.
     */
    public boolean addCitizen(int householdId, String name, String dobStr, String gender, String job, String relation) {
        try {
            LocalDate dob = LocalDate.parse(dobStr);
            Citizen c = new Citizen();
            c.setHouseholdId(householdId);
            c.setName(name);
            c.setDateOfBirth(dob);
            c.setGender(gender);
            c.setJob(job);
            c.setRelationshipToHead(relation);
            return service.add(c);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Xoá nhân khẩu theo ID.
     */
    public boolean deleteCitizen(int id) {
        return service.delete(id);
    }
}
