package ktpm.condo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import ktpm.condo.model.entity.FacilityBooking;
import ktpm.condo.model.service.facility_service.FacilityBookingService;
import ktpm.condo.model.service.facility_service.IFacilityBookingService;

/**
 * Controller điều phối nghiệp vụ lịch sử đặt tiện ích.
 */
public class FacilityController {
    private final IFacilityBookingService service;

    public FacilityController() {
        this.service = new FacilityBookingService();
    }

    // Lấy toàn bộ lịch sử
    public List<FacilityBooking> getAllBookings() {
        return service.getAll();
    }

    // Lọc lịch sử theo từ khóa (đã refactor logic filter từ panel xuống đây)
    public List<FacilityBooking> filterBookings(String facilityName, String householdIdStr, String usageDateStr) {
        List<FacilityBooking> list = service.getAll();

        if (facilityName != null && !facilityName.trim().isEmpty()) {
            String fNameLower = facilityName.trim().toLowerCase();
            list = list.stream()
                .filter(fb -> fb.getFacilityName().toLowerCase().contains(fNameLower))
                .collect(Collectors.toList());
        }

        if (householdIdStr != null && !householdIdStr.trim().isEmpty()) {
            try {
                int householdId = Integer.parseInt(householdIdStr.trim());
                list = list.stream()
                    .filter(fb -> fb.getHouseholdId() == householdId)
                    .collect(Collectors.toList());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("ID hộ khẩu phải là số nguyên.");
            }
        }

        if (usageDateStr != null && !usageDateStr.trim().isEmpty()) {
            try {
                LocalDate usageDate = LocalDate.parse(usageDateStr.trim());
                list = list.stream()
                    .filter(fb -> fb.getUsageDate().equals(usageDate))
                    .collect(Collectors.toList());
            } catch (Exception e) {
                throw new IllegalArgumentException("Ngày sử dụng không đúng định dạng yyyy-MM-dd.");
            }
        }

        return list;
    }

    public boolean addBooking(FacilityBooking booking) {
        return service.add(booking);
    }

    public boolean deleteBooking(int id) {
        return service.delete(id);
    }
}
