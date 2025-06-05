package ktpm.condo.model.service.facility_service;

import java.util.List;

import ktpm.condo.model.dao.facility_dao.FacilityDAO;
import ktpm.condo.model.entity.Facility;

public class FacilityService {
    private final FacilityDAO dao = new FacilityDAO();

    public List<Facility> getAll() {
        return dao.getAll();
    }

    public boolean add(String name) {
        return dao.add(name);
    }

    public boolean delete(int id) {
        return dao.delete(id);
    }
}
