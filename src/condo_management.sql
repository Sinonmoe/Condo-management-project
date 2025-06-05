CREATE DATABASE  IF NOT EXISTS `condo_management` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `condo_management`;
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: condo_management
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `citizen`
--

DROP TABLE IF EXISTS `citizen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `citizen` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `household_id` int NOT NULL,
                           `name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
                           `date_of_birth` date NOT NULL,
                           `gender` enum('Nam','Nữ','Khác') COLLATE utf8mb4_general_ci NOT NULL,
                           `job` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
                           `relationship_to_head` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           KEY `household_id` (`household_id`),
                           CONSTRAINT `citizen_ibfk_1` FOREIGN KEY (`household_id`) REFERENCES `household` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `citizen`
--

-- LOCK TABLES `citizen` WRITE;
-- /*!40000 ALTER TABLE `citizen` DISABLE KEYS */;
-- INSERT INTO `citizen` VALUES (1,1,'Nguyễn Văn An','1980-05-10','Nam','Kỹ sư','Chủ hộ'),(2,1,'Trần Thị Bình','1985-09-15','Nữ','Giáo viên','Vợ'),(3,1,'Nguyễn Văn Cường','2010-04-02','Nam','Học sinh','Con'),(4,2,'Lê Văn Dũng','1975-03-22','Nam','Bác sĩ','Chủ hộ'),(5,2,'Lê Thị Hạnh','1978-07-11','Nữ','Nội trợ','Vợ'),(6,2,'Lê Văn Hải','2008-12-19','Nam','Học sinh','Con'),(7,2,'Lê Thị Hoa','2005-01-25','Nữ','Sinh viên','Con'),(8,3,'Phạm Thị Lan','1990-08-08','Nữ','Nhân viên văn phòng','Chủ hộ'),(9,3,'Phạm Văn Long','2015-06-30','Nam','Học sinh','Con');
-- /*!40000 ALTER TABLE `citizen` ENABLE KEYS */;
-- UNLOCK TABLES;

--
-- Table structure for table `facility`
--

DROP TABLE IF EXISTS `facility`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facility` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facility`
--

-- LOCK TABLES `facility` WRITE;
-- /*!40000 ALTER TABLE `facility` DISABLE KEYS */;
-- INSERT INTO `facility` VALUES (1,'Bể bơi'),(2,'Phòng gym'),(3,'Khu BBQ'),(4,'Khu đọc sách');
-- /*!40000 ALTER TABLE `facility` ENABLE KEYS */;
-- UNLOCK TABLES;

--
-- Table structure for table `facility_booking`
--

DROP TABLE IF EXISTS `facility_booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facility_booking` (
                                    `id` int NOT NULL AUTO_INCREMENT,
                                    `facility_id` int NOT NULL,
                                    `household_id` int NOT NULL,
                                    `booking_time` datetime NOT NULL,
                                    PRIMARY KEY (`id`),
                                    KEY `facility_id` (`facility_id`),
                                    KEY `household_id` (`household_id`),
                                    CONSTRAINT `facility_booking_ibfk_1` FOREIGN KEY (`facility_id`) REFERENCES `facility` (`id`),
                                    CONSTRAINT `facility_booking_ibfk_2` FOREIGN KEY (`household_id`) REFERENCES `household` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facility_booking`
--

-- LOCK TABLES `facility_booking` WRITE;
-- /*!40000 ALTER TABLE `facility_booking` DISABLE KEYS */;
-- INSERT INTO `facility_booking` VALUES (1,1,1,'2025-06-01 08:00:00'),(2,2,2,'2025-06-01 09:00:00'),(3,2,1,'2025-06-03 10:00:00'),(4,3,3,'2025-06-10 18:00:00'),(5,1,2,'2025-05-30 17:00:00'),(6,4,1,'2025-04-15 14:00:00'),(7,2,3,'2025-06-01 15:30:00'),(8,1,3,'2025-06-02 08:45:00');
-- /*!40000 ALTER TABLE `facility_booking` ENABLE KEYS */;
-- UNLOCK TABLES;

--
-- Table structure for table `fee`
--

DROP TABLE IF EXISTS `fee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fee` (
                       `id` int NOT NULL AUTO_INCREMENT,
                       `household_id` int NOT NULL,
                       `type` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
                       `amount` decimal(10,2) NOT NULL,
                       `due_date` date DEFAULT NULL,
                       `status` enum('Đã thanh toán','Chưa thanh toán') COLLATE utf8mb4_general_ci DEFAULT 'Chưa thanh toán',
                       PRIMARY KEY (`id`),
                       KEY `household_id` (`household_id`),
                       CONSTRAINT `fee_ibfk_1` FOREIGN KEY (`household_id`) REFERENCES `household` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fee`
--

-- LOCK TABLES `fee` WRITE;
-- /*!40000 ALTER TABLE `fee` DISABLE KEYS */;
-- INSERT INTO `fee` VALUES (1,1,'Phí quản lý',300000.00,'2025-06-10','Chưa thanh toán'),(2,1,'Phí giữ xe',150000.00,'2025-06-05','Đã thanh toán'),(3,1,'Phí vệ sinh',100000.00,'2025-06-15','Chưa thanh toán'),(4,2,'Phí quản lý',300000.00,'2025-06-10','Đã thanh toán'),(5,2,'Phí nước',120000.00,'2025-06-12','Chưa thanh toán'),(6,2,'Phí điện',500000.00,'2025-06-08','Chưa thanh toán'),(7,3,'Phí giữ xe',150000.00,'2025-06-05','Đã thanh toán'),(9,1,'Phí nước',100000.00,'2025-06-20','Đã thanh toán');
-- /*!40000 ALTER TABLE `fee` ENABLE KEYS */;
-- UNLOCK TABLES;

--
-- Table structure for table `household`
--

DROP TABLE IF EXISTS `household`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `household` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `apartment_number` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
                             `household_code` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
                             `number_of_members` int NOT NULL DEFAULT 0,
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `household_code` (`household_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DELIMITER $$

CREATE TRIGGER trg_citizen_after_insert
    AFTER INSERT ON citizen
    FOR EACH ROW
BEGIN
    UPDATE household
    SET number_of_members = number_of_members + 1
    WHERE id = NEW.household_id;
    END$$

    CREATE TRIGGER trg_citizen_after_delete
        AFTER DELETE ON citizen
        FOR EACH ROW
    BEGIN
        UPDATE household
        SET number_of_members = number_of_members - 1
        WHERE id = OLD.household_id;
        END$$



        DELIMITER ;

--
-- Dumping data for table `household`
--

-- LOCK TABLES `household` WRITE;
-- /*!40000 ALTER TABLE `household` DISABLE KEYS */;
-- INSERT INTO `household` VALUES (1,'A101','HK001',3),(2,'A102','HK002',4),(3,'B201','HK003',2);
-- /*!40000 ALTER TABLE `household` ENABLE KEYS */;
-- UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dữ liệu cho bảng household (3 hộ)
        INSERT INTO `household` (`id`, `apartment_number`, `household_code`) VALUES
                                                                                 (1, 'A101', 'HK001'),
                                                                                 (2, 'A102', 'HK002'),
                                                                                 (3, 'B201', 'HK003');

-- Dữ liệu cho bảng citizen (9 người)
        INSERT INTO `citizen` (`id`, `household_id`, `name`, `date_of_birth`, `gender`, `job`, `relationship_to_head`) VALUES
                                                                                                                           (1, 1, 'Nguyễn Văn An', '1980-05-10', 'Nam', 'Kỹ sư', 'Chủ hộ'),
                                                                                                                           (2, 1, 'Trần Thị Bình', '1985-09-15', 'Nữ', 'Giáo viên', 'Vợ'),
                                                                                                                           (3, 1, 'Nguyễn Văn Cường', '2010-04-02', 'Nam', 'Học sinh', 'Con'),
                                                                                                                           (4, 2, 'Lê Văn Dũng', '1975-03-22', 'Nam', 'Bác sĩ', 'Chủ hộ'),
                                                                                                                           (5, 2, 'Lê Thị Hạnh', '1978-07-11', 'Nữ', 'Nội trợ', 'Vợ'),
                                                                                                                           (6, 2, 'Lê Văn Hải', '2008-12-19', 'Nam', 'Học sinh', 'Con'),
                                                                                                                           (7, 2, 'Lê Thị Hoa', '2005-01-25', 'Nữ', 'Sinh viên', 'Con'),
                                                                                                                           (8, 3, 'Phạm Thị Lan', '1990-08-08', 'Nữ', 'Nhân viên văn phòng', 'Chủ hộ'),
                                                                                                                           (9, 3, 'Phạm Văn Long', '2015-06-30', 'Nam', 'Học sinh', 'Con');

-- Dữ liệu cho bảng facility (4 tiện ích)
        INSERT INTO `facility` (`id`, `name`) VALUES
                                                  (1, 'Bể bơi'),
                                                  (2, 'Phòng gym'),
                                                  (3, 'Khu BBQ'),
                                                  (4, 'Khu đọc sách');

-- Dữ liệu cho bảng facility_booking (8 lần đặt)
        INSERT INTO `facility_booking` (`id`, `facility_id`, `household_id`, `booking_time`) VALUES
                                                                                                 (1, 1, 1, '2025-06-01 08:00:00'),
                                                                                                 (2, 2, 2, '2025-06-01 09:00:00'),
                                                                                                 (3, 2, 1, '2025-06-03 10:00:00'),
                                                                                                 (4, 3, 3, '2025-06-10 18:00:00'),
                                                                                                 (5, 1, 2, '2025-05-30 17:00:00'),
                                                                                                 (6, 4, 1, '2025-04-15 14:00:00'),
                                                                                                 (7, 2, 3, '2025-06-01 15:30:00'),
                                                                                                 (8, 1, 3, '2025-06-02 08:45:00');


-- Dữ liệu cho bảng fee (9 bản ghi)
        INSERT INTO `fee` (`id`, `household_id`, `type`, `amount`, `due_date`, `status`) VALUES
                                                                                             (1, 1, 'Phí quản lý', 300000.00, '2025-06-10', 'Chưa thanh toán'),
                                                                                             (2, 1, 'Phí giữ xe', 150000.00, '2025-06-05', 'Đã thanh toán'),
                                                                                             (3, 1, 'Phí vệ sinh', 100000.00, '2025-06-15', 'Chưa thanh toán'),
                                                                                             (4, 2, 'Phí quản lý', 300000.00, '2025-06-10', 'Đã thanh toán'),
                                                                                             (5, 2, 'Phí nước', 120000.00, '2025-06-12', 'Chưa thanh toán'),
                                                                                             (6, 2, 'Phí điện', 500000.00, '2025-06-08', 'Chưa thanh toán'),
                                                                                             (7, 3, 'Phí giữ xe', 150000.00, '2025-06-05', 'Đã thanh toán'),
                                                                                             (8, 1, 'Phí nước', 100000.00, '2025-06-20', 'Đã thanh toán'),
                                                                                             (9, 3, 'Phí vệ sinh', 90000.00, '2025-06-15', 'Chưa thanh toán');
