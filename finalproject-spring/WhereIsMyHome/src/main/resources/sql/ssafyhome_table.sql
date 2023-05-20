create schema ssafyhome;

use ssafyhome;

CREATE TABLE `members` (
  `user_id` varchar(16) NOT NULL,
  `user_name` varchar(20) NOT NULL,
  `user_password` varchar(16) NOT NULL,
  `email_id` varchar(20) DEFAULT NULL,
  `email_domain` varchar(45) DEFAULT NULL,
  `join_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `apart` (
  `id` int NOT NULL AUTO_INCREMENT,
  `build_year` int DEFAULT NULL,
  `dong` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `area` varchar(45) DEFAULT NULL,
  `jibun` varchar(45) DEFAULT NULL,
  `regional_code` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14664 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `board` (
  `article_no` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(16) DEFAULT NULL,
  `subject` varchar(100) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `hit` int DEFAULT '0',
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`article_no`),
  KEY `fk_board_members_idx` (`user_id`),
  CONSTRAINT `fk_board_members` FOREIGN KEY (`user_id`) REFERENCES `members` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `dong` (
  `si_code` int NOT NULL,
  `bub_code` int NOT NULL,
  `si_name` varchar(45) DEFAULT NULL,
  `bub_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`si_code`,`bub_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `favorite_dong` (
  `si_code` int NOT NULL,
  `bub_code` int NOT NULL,
  `user_id` varchar(16) NOT NULL,
  PRIMARY KEY (`bub_code`,`si_code`,`user_id`),
  KEY `si_code` (`si_code`,`bub_code`),
  KEY `favorite_dong_members_idx` (`user_id`),
  CONSTRAINT `favorite_dong_ibfk_1` FOREIGN KEY (`si_code`, `bub_code`) REFERENCES `dong` (`si_code`, `bub_code`),
  CONSTRAINT `favorite_dong_members` FOREIGN KEY (`user_id`) REFERENCES `members` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `safe_hospital` (
  `no` int NOT NULL,
  `do_city` varchar(45) NOT NULL,
  `city_gun_borough` varchar(45) NOT NULL,
  `hospital_name` varchar(100) NOT NULL,
  `hospital_address` varchar(100) NOT NULL,
  `type` varchar(1) NOT NULL,
  `phone_number` varchar(45) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `screeningcenter` (
  `no` int NOT NULL,
  `do_city` varchar(45) DEFAULT NULL,
  `city_gun_borough` varchar(45) DEFAULT NULL,
  `hospital_name` varchar(100) DEFAULT NULL,
  `hospital_address` varchar(100) DEFAULT NULL,
  `operating_hours_weekday` varchar(1000) DEFAULT NULL,
  `operating_hours_saturday` varchar(1000) DEFAULT NULL,
  `operating_hours_sunday` varchar(1000) DEFAULT NULL,
  `operating_hours_holiday` varchar(1000) DEFAULT NULL,
  `representative_phone_number` varchar(45) DEFAULT NULL,
  `local_public_health_center` varchar(100) DEFAULT NULL,
  `local_public_health_center_phone_number` varchar(100) DEFAULT NULL,
  `accommodation_for_disabled_person` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `surrounding_commercial` (
  `store_num` int NOT NULL,
  `store_name` varchar(100) DEFAULT NULL,
  `store_place_name` varchar(100) DEFAULT NULL,
  `stroe_code` varchar(100) DEFAULT NULL,
  `store_type` varchar(100) DEFAULT NULL,
  `store_type_code` varchar(100) DEFAULT NULL,
  `store_type_name` varchar(100) DEFAULT NULL,
  `store_type_code_detail` varchar(100) DEFAULT NULL,
  `store_type_name_detail` varchar(100) DEFAULT NULL,
  `industrial_code_classification` varchar(100) DEFAULT NULL,
  `industrial_name_classification` varchar(100) DEFAULT NULL,
  `city_code` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `gun_borough_code` varchar(100) DEFAULT NULL,
  `gun_borough` varchar(100) DEFAULT NULL,
  `administrative_dong_code` varchar(100) DEFAULT NULL,
  `administrative_dong` varchar(100) DEFAULT NULL,
  `legaldong_code` varchar(100) DEFAULT NULL,
  `legaldong` varchar(100) DEFAULT NULL,
  `number_code` varchar(100) DEFAULT NULL,
  `site_type_code` varchar(100) DEFAULT NULL,
  `site` varchar(100) DEFAULT NULL,
  `site_bungi` varchar(100) DEFAULT NULL,
  `site_bubungi` varchar(100) DEFAULT NULL,
  `gibun_address` varchar(100) DEFAULT NULL,
  `doromyung_code` varchar(100) DEFAULT NULL,
  `doromyung` varchar(100) DEFAULT NULL,
  `building_bungi` varchar(100) DEFAULT NULL,
  `building_bubungi` varchar(100) DEFAULT NULL,
  `building_num` varchar(100) DEFAULT NULL,
  `buildingname` varchar(100) DEFAULT NULL,
  `address` varchar(1000) DEFAULT NULL,
  `old_zipcode` varchar(100) DEFAULT NULL,
  `new_zipcode` varchar(100) DEFAULT NULL,
  `dong_info` varchar(100) DEFAULT NULL,
  `floor_info` varchar(100) DEFAULT NULL,
  `ho_info` varchar(100) DEFAULT NULL,
  `longitude` varchar(100) DEFAULT NULL,
  `latitude` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`store_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `trade` (
  `id` int NOT NULL,
  `deal_year` int DEFAULT NULL,
  `deal_month` int DEFAULT NULL,
  `deal_day` int DEFAULT NULL,
  `deal_amount` varchar(45) DEFAULT NULL,
  `apart_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_apart_id_idx` (`apart_id`),
  CONSTRAINT `fk_apart_id` FOREIGN KEY (`apart_id`) REFERENCES `apart` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `accommodation` (
  `id` int NOT NULL,
  `running` varchar(45) DEFAULT NULL,
  `phone_number` varchar(45) DEFAULT NULL,
  `si_code` int NOT NULL,
  `bub_code` int NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `x` varchar(45) DEFAULT NULL,
  `y` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

