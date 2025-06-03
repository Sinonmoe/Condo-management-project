package ktpm.condo.model.entity;

import java.time.LocalDate;

/**
 * Lớp đại diện cho một khoản phí thuộc một hộ khẩu trong hệ thống chung cư.
 */
public class Fee {
    private int id;
    private int householdId;
    private String type;
    private double amount;
    private LocalDate dueDate;
    private String status;

    /**
     * Khởi tạo một đối tượng Fee rỗng.
     */
    public Fee() {}

    /**
     * Khởi tạo một đối tượng Fee với đầy đủ thông tin.
     *
     * @param id         ID phí
     * @param householdId ID hộ khẩu
     * @param type       Loại phí (ví dụ: điện, nước...)
     * @param amount     Số tiền
     * @param dueDate    Ngày đến hạn thanh toán
     * @param status     Trạng thái thanh toán
     */
    public Fee(int id, int householdId, String type, double amount, LocalDate dueDate, String status) {
        this.id = id;
        this.householdId = householdId;
        this.type = type;
        this.amount = amount;
        this.dueDate = dueDate;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getHouseholdId() { return householdId; }
    public void setHouseholdId(int householdId) { this.householdId = householdId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
