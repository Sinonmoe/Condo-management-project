package ktpm.condo.controller;

import java.util.List;

import ktpm.condo.model.entity.Fee;
import ktpm.condo.model.service.fee_service.FeeService;
import ktpm.condo.model.service.fee_service.IFeeService;

/**
 * Controller điều phối xử lý nghiệp vụ quản lý phí.
 */
public class FeeController {
    private final IFeeService service = new FeeService();

    public List<Fee> getAllFees() {
        return service.getAll();
    }

    public boolean addFee(Fee fee) {
        return service.add(fee);
    }

    public boolean deleteFee(int id) {
        return service.delete(id);
    }

    public boolean updateFeeStatus(int id, String newStatus) {
        return service.updateStatus(id, newStatus);
    }

    public List<Fee> getFeesByStatus(String status) {
        return service.getByStatus(status);
    }

    public List<Fee> filterFees(Integer householdId, String type, String status) {
        return service.filter(householdId, type, status);
    }
}
