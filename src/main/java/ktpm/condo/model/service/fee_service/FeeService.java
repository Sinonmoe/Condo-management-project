package ktpm.condo.model.service.fee_service;

import java.util.List;
import java.time.LocalDate;
import ktpm.condo.model.dao.FeeDAO;
import ktpm.condo.model.entity.Fee;

/**
 * Service xử lý nghiệp vụ cho module quản lý phí.
 */
public class FeeService implements IFeeService {
    private final FeeDAO dao = new FeeDAO();

    @Override
    public List<Fee> getAll() {
        return dao.getAll();
    }
    @Override
    public List<Fee> getOverdueUnpaidFees(LocalDate today) {
        return dao.getOverdueUnpaidFees(LocalDate.now());
    }


    @Override
    public boolean add(Fee fee) {
        return dao.add(fee);
    }

    @Override
    public boolean delete(int id) {
        return dao.delete(id);
    }

    @Override
    public boolean updateStatus(int id, String newStatus) {
        return dao.updateStatus(id, newStatus);
    }

    @Override
    public List<Fee> getByStatus(String status) {
        return dao.getByStatus(status);
    }

    @Override
    public List<Fee> filter(Integer householdId, String type, String status) {
        return dao.filter(householdId, type, status);
    }
}
