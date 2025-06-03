package ktpm.condo.controller;

import java.util.List;

import ktpm.condo.model.entity.FacilityBooking;
import ktpm.condo.model.service.FacilityBookingService;

/**
 * Controller điều phối giữa FacilityPanel và tầng Service.
 */
public class FacilityController {
    private final FacilityBookingService service = new FacilityBookingService();

    /**
     * Lấy toàn bộ lịch sử đặt tiện ích.
     */
    public List<FacilityBooking> getAllBookings() {
        return service.getAll();
    }

    /**
     * Lọc lịch sử theo tiêu chí.
     */
    public List<FacilityBooking> filter(String type, String keyword) {
        return service.filter(type, keyword);
    }

    /**
     * Thêm một lượt đặt tiện ích mới.
     *
     * @param booking đối tượng FacilityBooking cần thêm
     * @return true nếu thêm thành công, ngược lại false
     */
    public boolean addBooking(FacilityBooking booking) {
        return service.addBooking(booking);
    }

    /**
     * Xoá một lượt đặt tiện ích.
     *
     * @param id ID của lượt đặt tiện ích cần xoá
     * @return true nếu xoá thành công, ngược lại false
     */
    public boolean deleteBooking(int id) {
        return service.deleteBooking(id);
    }
}
