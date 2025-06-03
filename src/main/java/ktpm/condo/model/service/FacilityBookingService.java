package ktpm.condo.model.service;

import java.util.List;

import ktpm.condo.model.dao.FacilityBookingDAO;
import ktpm.condo.model.entity.FacilityBooking;

/**
 * Service quản lý các thao tác nghiệp vụ liên quan đến lịch sử tiện ích.
 */
public class FacilityBookingService {
    private final FacilityBookingDAO dao = new FacilityBookingDAO();

    /**
     * Lấy toàn bộ lịch sử đặt tiện ích.
     *
     * @return danh sách lịch sử đặt tiện ích
     */
    public List<FacilityBooking> getAll() {
        return dao.getAll();
    }

    /**
     * Lọc lịch sử đặt tiện ích theo tiêu chí cụ thể.
     *
     * @param type    kiểu lọc: name, household, date, month
     * @param keyword từ khoá tương ứng
     * @return danh sách kết quả phù hợp
     */
    public List<FacilityBooking> filter(String type, String keyword) {
        return dao.filter(type, keyword);
    }

    /**
     * Thêm một lượt đặt tiện ích mới.
     *
     * @param booking đối tượng chứa thông tin đặt tiện ích
     * @return true nếu thêm thành công, ngược lại false
     */
    public boolean addBooking(FacilityBooking booking) {
        return dao.addBooking(booking);
    }

    /**
     * Xóa một lượt đặt tiện ích theo ID.
     *
     * @param id ID của lượt đặt tiện ích
     * @return true nếu xóa thành công, ngược lại false
     */
    public boolean deleteBooking(int id) {
        return dao.deleteBooking(id);
    }
}
