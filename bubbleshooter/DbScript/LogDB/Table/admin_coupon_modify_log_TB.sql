/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.20-log : Database - BS_log_00
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`BS_log_00` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `BS_log_00`;

/*Table structure for table `admin_coupon_modify_log_TB` */

DROP TABLE IF EXISTS `admin_coupon_modify_log_TB`;

CREATE TABLE `admin_coupon_modify_log_TB` (
  `seq` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '번호',
  `ptn_month` tinyint(4) NOT NULL COMMENT '파티셔닝 키',
  `log_time` int(11) NOT NULL,
  `member_id` VARCHAR(20) NOT NULL COMMENT '운영자 아이디',
  `coupon_id` bigint(20) NOT NULL COMMENT '쿠폰아이디',
  `coupon` VARCHAR(128) DEFAULT NULL COMMENT '쿠폰 string',
  `start_time` INTEGER(11) NOT NULL COMMENT '쿠폰 시작 시간',
  `end_time` INTEGER(11) NOT NULL COMMENT '쿠폰 끝나는 시간',
  `limit_count` INTEGER(11) NOT NULL COMMENT '쿠폰 사용 가능 횟수',
  `item_type` tinyint(4) NOT NULL COMMENT '쿠폰에 포함된 item_type',
  `item_id` int(11) NOT NULL COMMENT '쿠폰에 포함된 item_id',
  `item_count` int(11) NOT NULL COMMENT '쿠폰에 포함된 item_count',
  `grade` tinyint(4) DEFAULT '-1' COMMENT 'grade (캐릭터, 파트너가 아닐경우 -1)',
  `level` tinyint(4) DEFAULT '-1' COMMENT 'level (캐릭터, 파트너가 아닐경우 -1)',
  `enhance` tinyint(4) DEFAULT '-1' COMMENT 'enhance` (캐릭터, 파트너가 아닐경우 -1)',
  `skill_1` integer(11) DEFAULT '-1' COMMENT '첫번째 스킬 (캐릭터, 파트너가 아닐경우 -1)',
  `effect_grade_1` tinyint(4) DEFAULT '-1' COMMENT '스킬 등급 (캐릭터, 파트너가 아닐경우 -1)',
  `skill_2` integer(11) DEFAULT '-1' COMMENT '두번째 스킬 (캐릭터, 파트너가 아닐경우 -1)',
  `effect_grade_2` tinyint(4) DEFAULT '-1' COMMENT '스킬 등급 (캐릭터, 파트너가 아닐경우 -1)',
  `skill_3` integer(11) DEFAULT '-1' COMMENT '두번째 스킬 (캐릭터, 파트너가 아닐경우 -1)',
  `effect_grade_3` tinyint(4) DEFAULT '-1' COMMENT '스킬 등급 (캐릭터, 파트너가 아닐경우 -1)',
  `is_delete` tinyint(4)DEFAULT '-1' COMMENT '삭제 여부 (0: 정상, 1: 삭제)',
  `reg_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 시간',
  PRIMARY KEY (`seq`,`ptn_month`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4
/*!50100 PARTITION BY LIST (ptn_month)
(PARTITION p1 VALUES IN (1) ENGINE = InnoDB,
 PARTITION p2 VALUES IN (2) ENGINE = InnoDB,
 PARTITION p3 VALUES IN (3) ENGINE = InnoDB,
 PARTITION p4 VALUES IN (4) ENGINE = InnoDB,
 PARTITION p5 VALUES IN (5) ENGINE = InnoDB,
 PARTITION p6 VALUES IN (6) ENGINE = InnoDB,
 PARTITION p7 VALUES IN (7) ENGINE = InnoDB,
 PARTITION p8 VALUES IN (8) ENGINE = InnoDB,
 PARTITION p9 VALUES IN (9) ENGINE = InnoDB,
 PARTITION p10 VALUES IN (10) ENGINE = InnoDB,
 PARTITION p11 VALUES IN (11) ENGINE = InnoDB,
 PARTITION p12 VALUES IN (12) ENGINE = InnoDB) */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
