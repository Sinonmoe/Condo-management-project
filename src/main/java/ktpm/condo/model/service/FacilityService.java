package ktpm.condo.model.service;

import ktpm.condo.model.dao.FacilityDAO;

/**
 * Service xử lý nghiệp vụ liên quan đến tiện ích (thêm, xóa).
 */
public class FacilityService {
    private final FacilityDAO dao = new FacilityDAO();

    /**
     * Thêm tiện ích mới.
     */
    public boolean add(String name) {
        return dao.add(name);
    }

    /**
     * Xóa tiện ích theo ID.
     */
    public boolean delete(int id) {
        return dao.delete(id);
    }
}
