package ktpm.condo.model.entity;

/**
 * Đại diện cho một loại tiện ích (ví dụ: Bể bơi, Phòng gym) trong chung cư.
 * Thông tin này được lưu trữ trong bảng 'facility'.
 */
public class Facility {
    private int id;
    private String name;

    /**
     * Khởi tạo đối tượng Facility đầy đủ thông tin.
     *
     * @param id   mã định danh tiện ích
     * @param name tên tiện ích
     */
    public Facility(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Khởi tạo tiện ích chỉ với tên (khi thêm mới).
     *
     * @param name tên tiện ích
     */
    public Facility(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
