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
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bbf_log` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `bbf_log`;

/*Table structure for table `admin_event_modify_log_TB` */

DROP TABLE IF EXISTS `admin_event_modify_log_TB`;

CREATE TABLE `admin_event_modify_log_TB` (
  `seq` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '번호',
  `ptn_month` tinyint(4) NOT NULL COMMENT '파티셔닝 키',
  `log_time` int(11) NOT NULL,
  `member_id` VARCHAR(20) NOT NULL COMMENT '운영자 아이디',
  `event_id` int(11) NOT NULL COMMENT ' 이벤트 아이디',
  `event_type` TINYINT(4) NOT NULL COMMENT '이벤트 타입(0: 모든유저, 1: 신규유저, 2: 복귀유저)',
  `event_name` VARCHAR(128) DEFAULT NULL COMMENT '이벤트 명',
  `start_time` bigint(20) NOT NULL COMMENT '이벤트 시작 시간',
  `end_time` bigint(20) NOT NULL COMMENT '이벤트 끝나는 시간',
  `item_0` VARCHAR(128) DEFAULT NULL COMMENT '이벤트 아이템 0 string',
  `item_1` VARCHAR(128) DEFAULT NULL COMMENT '이벤트 아이템 1 string',
  `item_2` VARCHAR(128) DEFAULT NULL COMMENT '이벤트 아이템 2 string',
  `item_3` VARCHAR(128) DEFAULT NULL COMMENT '이벤트 아이템 3 string',
  `item_4` VARCHAR(128) DEFAULT NULL COMMENT '이벤트 아이템 4 string',
  `valid` TINYINT(4) NOT NULL COMMENT '이벤트 진행 여부 (0: 중지, 1: 진행)',
  
  `reg_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 시간',
  PRIMARY KEY (`seq`,`ptn_month`, `event_id`)
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
