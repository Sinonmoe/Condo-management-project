package ktpm.condo.controller;

import java.util.List;

import ktpm.condo.model.entity.Fee;
import ktpm.condo.model.service.FeeService;

/**
 * Controller điều phối xử lý nghiệp vụ cho module quản lý phí.
 */
public class FeeController {
    private final FeeService service = new FeeService();

    /**
     * Lấy tất cả các khoản phí.
     */
    public List<Fee> getAll() {
        return service.getAll();
    }

    /**
     * Lấy danh sách phí theo trạng thái.
     *
     * @param status trạng thái ("Đã thanh toán", "Chưa thanh toán")
     * @return danh sách phí theo trạng thái
     */
    public List<Fee> getByStatus(String status) {
        return service.getByStatus(status);
    }

    /**
     * Lọc phí theo ID hộ khẩu và loại phí.
     *
     * @param householdId ID hộ khẩu (có thể null)
     * @param type Loại phí (có thể null hoặc chuỗi rỗng)
     * @param status Trạng thái
     * @return danh sách phí phù hợp
     */
    public List<Fee> filter(Integer householdId, String type, String status) {
        return service.filter(householdId, type, status);
    }

    /**
     * Thêm khoản phí mới.
     *
     * @param fee đối tượng Fee cần thêm
     * @return true nếu thêm thành công
     */
    public boolean addFee(Fee fee) {
        return service.add(fee);
    }

    /**
     * Xóa khoản phí theo ID.
     *
     * @param id ID phí cần xóa
     * @return true nếu xóa thành công
     */
    public boolean deleteFee(int id) {
        return service.delete(id);
    }

    /**
     * Cập nhật trạng thái thanh toán phí.
     *
     * @param id ID phí cần cập nhật
     * @param newStatus trạng thái mới ("Đã thanh toán", "Chưa thanh toán")
     * @return true nếu cập nhật thành công
     */
    public boolean updateStatus(int id, String newStatus) {
        return service.updateStatus(id, newStatus);
    }
}
