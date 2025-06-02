package ktpm.condo.model.service;

import java.util.List;

import ktpm.condo.model.dao.CitizenDAO;
import ktpm.condo.model.entity.Citizen;

/**
 * Service xử lý logic nghiệp vụ nhân khẩu.
 */
public class CitizenService {
    private final CitizenDAO dao = new CitizenDAO();

    public List<Citizen> getByHousehold(int householdId) {
        return dao.getCitizensByHouseholdId(householdId);
    }

    public boolean add(Citizen c) {
        return dao.addCitizen(c);
    }

    public boolean update(Citizen c) {
        return dao.updateCitizen(c);
    }

    public boolean delete(int id) {
        return dao.deleteCitizen(id);
    }
}
