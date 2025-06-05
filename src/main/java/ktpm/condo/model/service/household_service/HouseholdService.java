package ktpm.condo.model.service.household_service;

import java.util.List;

import ktpm.condo.model.dao.household_dao.HouseholdDAO;
import ktpm.condo.model.entity.Household;

/**
 * Service xử lý logic nghiệp vụ hộ khẩu.
 */
public class HouseholdService implements IHouseholdService {
    private final HouseholdDAO dao = new HouseholdDAO();

    @Override
    public List<Household> getAll() {
        return dao.getAllHouseholds();
    }

    @Override
    public boolean add(Household h) {
        return dao.addHousehold(h);
    }

    @Override
    public boolean delete(int id) {
        return dao.deleteHousehold(id);
    }

    @Override
    public Household getById(int id) {
        return dao.getHouseholdById(id);
    }
}
