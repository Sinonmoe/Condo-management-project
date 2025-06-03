package ktpm.condo.model.service;

import java.util.List;

import ktpm.condo.model.dao.FeeDAO;
import ktpm.condo.model.entity.Fee;

/**
 * Service trung gian xử lý logic nghiệp vụ phí.
 */
public class FeeService {
    private final FeeDAO dao = new FeeDAO();

    public List<Fee> getAll() {
        return dao.getAll();
    }

    public boolean add(Fee fee) {
        return dao.add(fee);
    }

    public boolean delete(int id) {
        return dao.delete(id);
    }

    public boolean updateStatus(int id, String newStatus) {
        return dao.updateStatus(id, newStatus);
    }

    public List<Fee> getByStatus(String status) {
        return dao.getByStatus(status);
    }

    public List<Fee> filter(Integer householdId, String type, String status) {
        return dao.filter(householdId, type, status);
    }
}