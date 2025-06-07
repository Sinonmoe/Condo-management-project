package ktpm.condo.model.service.fee_service;

import java.time.LocalDate;
import java.util.List;

import ktpm.condo.model.entity.Fee;

/**
 * Interface định nghĩa các phương thức nghiệp vụ cho quản lý phí.
 */
public interface IFeeService {

    List<Fee> getAll();

    boolean add(Fee fee);
    List<Fee> getOverdueUnpaidFees(LocalDate today);

    boolean delete(int id);

    boolean updateStatus(int id, String newStatus);

    List<Fee> getByStatus(String status);

    List<Fee> filter(Integer householdId, String type, String status);
}
