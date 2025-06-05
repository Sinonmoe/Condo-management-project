-- Xóa database cũ nếu có để tạo lại mới sạch sẽ
DROP DATABASE IF EXISTS `condo_management`;

-- Tạo database mới với charset utf8mb4
CREATE DATABASE `condo_management`
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;

USE `condo_management`;

-- Tắt kiểm tra khóa ngoại tạm thời để dễ tạo bảng
SET FOREIGN_KEY_CHECKS = 0;

-- Xóa bảng nếu đã tồn tại (theo thứ tự khóa ngoại)
DROP TABLE IF EXISTS `facility_booking`;
DROP TABLE IF EXISTS `fee`;
DROP TABLE IF EXISTS `citizen`;
DROP TABLE IF EXISTS `facility`;
DROP TABLE IF EXISTS `household`;

-- Tạo bảng household trước vì các bảng khác phụ thuộc vào nó
CREATE TABLE `household` (
  `id` int NOT NULL AUTO_INCREMENT,
  `apartment_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `household_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `number_of_members` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `household_code` (`household_code`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tạo bảng citizen
CREATE TABLE `citizen` (
  `id` int NOT NULL AUTO_INCREMENT,
  `household_id` int NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `date_of_birth` date NOT NULL,
  `gender` enum('Nam','Nữ','Khác') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `job` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `relationship_to_head` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `household_id` (`household_id`),
  CONSTRAINT `citizen_ibfk_1` FOREIGN KEY (`household_id`) REFERENCES `household` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tạo bảng facility
CREATE TABLE `facility` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tạo bảng fee
CREATE TABLE `fee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `household_id` int NOT NULL,
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `due_date` date DEFAULT NULL,
  `status` enum('Đã thanh toán','Chưa thanh toán') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'Chưa thanh toán',
  PRIMARY KEY (`id`),
  KEY `household_id` (`household_id`),
  CONSTRAINT `fee_ibfk_1` FOREIGN KEY (`household_id`) REFERENCES `household` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tạo bảng facility_booking
CREATE TABLE `facility_booking` (
  `id` int NOT NULL AUTO_INCREMENT,
  `facility_id` int NOT NULL,
  `household_id` int NOT NULL,
  `booking_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `facility_id` (`facility_id`),
  KEY `household_id` (`household_id`),
  CONSTRAINT `facility_booking_ibfk_1` FOREIGN KEY (`facility_id`) REFERENCES `facility` (`id`),
  CONSTRAINT `facility_booking_ibfk_2` FOREIGN KEY (`household_id`) REFERENCES `household` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Bật lại kiểm tra khóa ngoại
SET FOREIGN_KEY_CHECKS = 1;

-- Tạo trigger cập nhật số lượng thành viên khi thêm citizen
DELIMITER $$
CREATE TRIGGER trg_citizen_after_insert
AFTER INSERT ON citizen
FOR EACH ROW
BEGIN
  UPDATE household
  SET number_of_members = number_of_members + 1
  WHERE id = NEW.household_id;
END$$

-- Tạo trigger cập nhật số lượng thành viên khi xóa citizen
CREATE TRIGGER trg_citizen_after_delete
AFTER DELETE ON citizen
FOR EACH ROW
BEGIN
  UPDATE household
  SET number_of_members = number_of_members - 1
  WHERE id = OLD.household_id;
END$$
DELIMITER ;

-- Chèn dữ liệu vào bảng household
INSERT INTO `household` (`id`, `apartment_number`, `household_code`, `number_of_members`) VALUES
(1,'A101','HK001',3),
(3,'B201','HK003',2),
(4,'C303','HK004',0);

-- Chèn dữ liệu vào bảng citizen
INSERT INTO `citizen` (`id`, `household_id`, `name`, `date_of_birth`, `gender`, `job`, `relationship_to_head`) VALUES
(1,1,'Nguyễn Văn An','1980-05-10','Nam','Kỹ sư','Chủ hộ'),
(2,1,'Trần Thị Bình','1985-09-15','Nữ','Giáo viên','Vợ'),
(3,1,'Nguyễn Văn Cường','2010-04-02','Nam','Học sinh','Con'),
(8,3,'Phạm Thị Lan','1990-08-08','Nữ','Nhân viên văn phòng','Chủ hộ'),
(9,3,'Phạm Văn Long','2015-06-30','Nam','Học sinh','Con');

-- Chèn dữ liệu vào bảng facility
INSERT INTO `facility` (`id`, `name`) VALUES
(1,'Bể bơi'),
(2,'Phòng gym'),
(3,'Khu BBQ'),
(4,'Khu đọc sách');

-- Chèn dữ liệu vào bảng fee
INSERT INTO `fee` (`id`, `household_id`, `type`, `amount`, `due_date`, `status`) VALUES
(1,1,'Phí quản lý',300000.00,'2025-06-10','Chưa thanh toán'),
(2,1,'Phí giữ xe',150000.00,'2025-06-05','Đã thanh toán'),
(3,1,'Phí vệ sinh',100000.00,'2025-06-15','Chưa thanh toán'),
(7,3,'Phí giữ xe',150000.00,'2025-06-05','Đã thanh toán'),
(8,1,'Phí nước',100000.00,'2025-06-20','Đã thanh toán'),
(9,3,'Phí vệ sinh',90000.00,'2025-06-15','Chưa thanh toán');

-- Chèn dữ liệu vào bảng facility_booking
INSERT INTO `facility_booking` (`id`, `facility_id`, `household_id`, `booking_time`) VALUES
(1,1,1,'2025-06-01 08:00:00'),
(3,2,1,'2025-06-03 10:00:00'),
(4,3,3,'2025-06-10 18:00:00'),
(6,4,1,'2025-04-15 14:00:00'),
(7,2,3,'2025-06-01 15:30:00'),
(8,1,3,'2025-06-02 08:45:00');
