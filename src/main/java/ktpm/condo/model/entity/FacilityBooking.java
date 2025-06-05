package ktpm.condo.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FacilityBooking {
    private int id;
    private int facilityId;       // Thay facilityName bằng facilityId để lưu DB
    private String facilityName;  // Có thể dùng để hiển thị, không dùng để insert
    private int householdId;
    private LocalDateTime bookingTime;

    public FacilityBooking() {}

    public FacilityBooking(int id, String facilityName, int householdId, LocalDateTime bookingTime) {
        this.id = id;
        this.facilityName = facilityName;
        this.householdId = householdId;
        this.bookingTime = bookingTime;
    }

    // Dùng constructor này để insert, facilityName không dùng để insert
    public FacilityBooking(int facilityId, int householdId, LocalDateTime bookingTime) {
        this.facilityId = facilityId;
        this.householdId = householdId;
        this.bookingTime = bookingTime;
    }

    // Getter & Setter
    public int getId() { return id; }
    public int getFacilityId() { return facilityId; }
    public void setFacilityId(int facilityId) { this.facilityId = facilityId; }

    public String getFacilityName() { return facilityName; }
    public void setFacilityName(String facilityName) { this.facilityName = facilityName; }

    public int getHouseholdId() { return householdId; }
    public void setHouseholdId(int householdId) { this.householdId = householdId; }

    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }

    public LocalDate getUsageDate() { return bookingTime.toLocalDate(); }
    public void setUsageDate(LocalDate date) { this.bookingTime = date.atStartOfDay(); }
}
