package ktpm.condo.model.service.household_service;

import java.util.List;

import ktpm.condo.model.dao.household_dao.CitizenDAO;
import ktpm.condo.model.entity.Citizen;

/**
 * Service xử lý logic nghiệp vụ nhân khẩu.
 */
public class CitizenService implements ICitizenService {
    private final CitizenDAO dao = new CitizenDAO();

    @Override
    public List<Citizen> getByHousehold(int householdId) {
        return dao.getCitizensByHouseholdId(householdId);
    }

    @Override
    public boolean add(Citizen c) {
        return dao.addCitizen(c);
    }

    @Override
    public boolean update(Citizen c) {
        return dao.updateCitizen(c);
    }

    @Override
    public boolean delete(int id) {
        return dao.deleteCitizen(id);
    }
}
