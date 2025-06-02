package ktpm.condo.model.service;

import java.util.List;

import ktpm.condo.model.dao.HouseholdDAO;
import ktpm.condo.model.entity.Household;

/**
 * Service xử lý logic nghiệp vụ hộ khẩu.
 */
public class HouseholdService {
    private final HouseholdDAO dao = new HouseholdDAO();

    public List<Household> getAll() {
        return dao.getAllHouseholds();
    }

    public boolean add(Household h) {
        return dao.addHousehold(h);
    }

    public boolean delete(int id) {
        return dao.deleteHousehold(id);
    }

    public Household getById(int id) {
        return dao.getHouseholdById(id);
    }
}
