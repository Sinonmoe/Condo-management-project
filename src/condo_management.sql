-- Tạo CSDL
CREATE DATABASE IF NOT EXISTS condo_management DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE condo_management;

-- Bảng hộ khẩu
CREATE TABLE household (
    id INT AUTO_INCREMENT PRIMARY KEY,
    apartment_number VARCHAR(20) NOT NULL,
    household_code VARCHAR(20) UNIQUE NOT NULL,
    number_of_members INT NOT NULL
);

-- Bảng nhân khẩu
CREATE TABLE citizen (
    id INT AUTO_INCREMENT PRIMARY KEY,
    household_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender ENUM('Nam', 'Nữ', 'Khác') NOT NULL,
    job VARCHAR(100),
    relationship_to_head VARCHAR(50),
    FOREIGN KEY (household_id) REFERENCES household(id) ON DELETE CASCADE
);

-- Bảng phí dịch vụ
CREATE TABLE fee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    household_id INT NOT NULL,
    type VARCHAR(50) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    due_date DATE,
    status ENUM('Đã thanh toán', 'Chưa thanh toán') DEFAULT 'Chưa thanh toán',
    FOREIGN KEY (household_id) REFERENCES household(id) ON DELETE CASCADE
);

-- Bảng tiện ích
CREATE TABLE facility (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- Lịch sử đăng ký tiện ích
CREATE TABLE facility_booking (
    id INT AUTO_INCREMENT PRIMARY KEY,
    facility_id INT NOT NULL,
    household_id INT NOT NULL,
    booking_time DATETIME NOT NULL,
    FOREIGN KEY (facility_id) REFERENCES facility(id),
    FOREIGN KEY (household_id) REFERENCES household(id)
);

INSERT INTO household (apartment_number, household_code, number_of_members)
VALUES 
  ('A101', 'HK001', 3),
  ('A102', 'HK002', 4),
  ('B201', 'HK003', 2);

INSERT INTO citizen (household_id, name, date_of_birth, gender, job, relationship_to_head)
VALUES
  (1, 'Nguyễn Văn An', '1980-05-10', 'Nam', 'Kỹ sư', 'Chủ hộ'),
  (1, 'Trần Thị Bình', '1985-09-15', 'Nữ', 'Giáo viên', 'Vợ'),
  (1, 'Nguyễn Văn Cường', '2010-04-02', 'Nam', 'Học sinh', 'Con'),

  (2, 'Lê Văn Dũng', '1975-03-22', 'Nam', 'Bác sĩ', 'Chủ hộ'),
  (2, 'Lê Thị Hạnh', '1978-07-11', 'Nữ', 'Nội trợ', 'Vợ'),
  (2, 'Lê Văn Hải', '2008-12-19', 'Nam', 'Học sinh', 'Con'),
  (2, 'Lê Thị Hoa', '2005-01-25', 'Nữ', 'Sinh viên', 'Con'),

  (3, 'Phạm Thị Lan', '1990-08-08', 'Nữ', 'Nhân viên văn phòng', 'Chủ hộ'),
  (3, 'Phạm Văn Long', '2015-06-30', 'Nam', 'Học sinh', 'Con');
