package ktpm.condo.model.entity;

import java.time.LocalDateTime;

/**
 * Đại diện cho một lượt đặt tiện ích của hộ dân.
 */
public class FacilityBooking {
    private int id;
    private String facilityName;
    private int householdId;
    private LocalDateTime bookingTime;

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

    public void setId(int id) {
        this.id = id;
    }
}
