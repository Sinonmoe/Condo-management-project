package ktpm.condo.model.entity;

import java.time.LocalDate;

/**
 * Lớp đại diện cho một nhân khẩu thuộc hộ khẩu trong chung cư.
 */
public class Citizen {
    private int id;
    private int householdId;
    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    private String job;
    private String relationshipToHead;

    public Citizen() {}

    public Citizen(int id, int householdId, String name, LocalDate dateOfBirth, String gender, String job, String relationshipToHead) {
        this.id = id;
        this.householdId = householdId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.job = job;
        this.relationshipToHead = relationshipToHead;
    }

    /** @return ID nhân khẩu */
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    /** @return ID hộ khẩu liên kết */
    public int getHouseholdId() { return householdId; }
    public void setHouseholdId(int householdId) { this.householdId = householdId; }

    /** @return Họ tên */
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    /** @return Ngày sinh */
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    /** @return Giới tính */
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    /** @return Nghề nghiệp */
    public String getJob() { return job; }
    public void setJob(String job) { this.job = job; }

    /** @return Quan hệ với chủ hộ */
    public String getRelationshipToHead() { return relationshipToHead; }
    public void setRelationshipToHead(String relationshipToHead) { this.relationshipToHead = relationshipToHead; }
}
