package ktpm.condo.model.service.facility_service;

import java.util.List;

import ktpm.condo.model.dao.facility_dao.FacilityBookingDAO;
import ktpm.condo.model.entity.FacilityBooking;

/**
 * Service xử lý nghiệp vụ lịch sử đặt tiện ích.
 */
public class FacilityBookingService implements IFacilityBookingService {
    private final FacilityBookingDAO dao = new FacilityBookingDAO();

    @Override
    public List<FacilityBooking> getAll() {
        return dao.getAll();
    }

    @Override
    public List<FacilityBooking> filter(String type, String keyword) {
        return dao.filter(type, keyword);
    }

    @Override
    public boolean add(FacilityBooking booking) {
        return dao.addBooking(booking);
    }

    @Override
    public boolean delete(int id) {
        return dao.deleteBooking(id);
    }
}
