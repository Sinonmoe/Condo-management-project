package ktpm.condo.model.service.facility_service;

import java.util.List;

import ktpm.condo.model.entity.Facility;

/**
 * Interface định nghĩa nghiệp vụ quản lý tiện ích.
 */
public interface IFacilityService {
    /**
     * Lấy danh sách tất cả tiện ích.
     */
    List<Facility> getAll();

    /**
     * Thêm tiện ích mới.
     *
     * @param name tên tiện ích
     * @return true nếu thành công
     */
    boolean add(String name);

    /**
     * Xóa tiện ích theo ID.
     *
     * @param id ID tiện ích
     * @return true nếu thành công
     */
    boolean delete(int id);
}
