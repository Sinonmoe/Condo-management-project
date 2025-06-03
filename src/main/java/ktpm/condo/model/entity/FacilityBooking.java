package ktpm.condo.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Đại diện cho một lượt đặt tiện ích của hộ dân.
 */
public class FacilityBooking {
    private int id;
    private String facilityName;
    private int householdId;
    private LocalDateTime bookingTime;

    public FacilityBooking() {
        // Constructor không đối số
    }

    public FacilityBooking(int id, String facilityName, int householdId, LocalDateTime bookingTime) {
        this.id = id;
        this.facilityName = facilityName;
        this.householdId = householdId;
        this.bookingTime = bookingTime;
    }

    public FacilityBooking(String facilityName, int householdId, LocalDateTime bookingTime) {
        this.facilityName = facilityName;
        this.householdId = householdId;
        this.bookingTime = bookingTime;
    }

    public int getId() {
        return id;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public int getHouseholdId() {
        return householdId;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    // Thêm getter trả về LocalDate cho UI tiện dùng
    public LocalDate getUsageDate() {
        return bookingTime.toLocalDate();
    }

    // Các setter bổ sung
    public void setId(int id) {
        this.id = id;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public void setHouseholdId(int householdId) {
        this.householdId = householdId;
    }

    public void setUsageDate(LocalDate date) {
        this.bookingTime = date.atStartOfDay();
    }
}
