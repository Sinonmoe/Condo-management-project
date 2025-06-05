package ktpm.condo.model.service.facility_service;

import java.util.List;

import ktpm.condo.model.entity.FacilityBooking;

/**
 * Interface định nghĩa nghiệp vụ cho lịch sử đặt tiện ích.
 */
public interface IFacilityBookingService {
    /**
     * Lấy toàn bộ lịch sử đặt tiện ích.
     */
    List<FacilityBooking> getAll();

    /**
     * Lọc lịch sử đặt tiện ích theo tiêu chí cụ thể.
     *
     * @param type    kiểu lọc: name, household, date, month
     * @param keyword từ khóa tương ứng
     * @return danh sách kết quả
     */
    List<FacilityBooking> filter(String type, String keyword);

    /**
     * Thêm một lượt đặt tiện ích mới.
     *
     * @param booking thông tin đặt tiện ích
     * @return true nếu thành công
     */
    boolean add(FacilityBooking booking);

    /**
     * Xóa lượt đặt tiện ích theo ID.
     *
     * @param id ID lượt đặt
     * @return true nếu thành công
     */
    boolean delete(int id);
}
