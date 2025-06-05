package ktpm.condo.model.service.facility_service;

import java.util.List;

import ktpm.condo.model.dao.facility_dao.FacilityDAO;
import ktpm.condo.model.entity.Facility;

/**
 * Service xử lý nghiệp vụ quản lý tiện ích.
 */
public class FacilityService implements IFacilityService {
    private final FacilityDAO dao = new FacilityDAO();

    @Override
    public List<Facility> getAll() {
        return dao.getAll();
    }

    @Override
    public boolean add(String name) {
        return dao.add(name);
    }

    @Override
    public boolean delete(int id) {
        return dao.delete(id);
    }
}
