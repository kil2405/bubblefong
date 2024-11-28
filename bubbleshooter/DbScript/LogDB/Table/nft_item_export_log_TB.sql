/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.20-log : Database - bbf_log
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

/*Table structure for table `nft_item_export_log_TB` */

DROP TABLE IF EXISTS `nft_item_export_log_TB`;

CREATE TABLE `nft_item_export_log_TB` (
  `seq` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'seq',
  `ptn_month` tinyint(4) NOT NULL COMMENT '로그 월',
  `ptn_day` integer(11) NOT NULL COMMENT '로그 일',
  `log_date` bigint(20) NOT NULL COMMENT '로그 데이터 시간',
  `user_id` integer(11) NOT NULL COMMENT '사용자 아이디',
  `wallet` varchar(255) NOT NULL COMMENT '유저 지갑 주소',
  `url` varchar(48) NOT NULL COMMENT 'relay server url',
  `uid` varchar(255) NOT NULL COMMENT '캐릭터&동료의 uid',
  `meta_data` json NOT NULL COMMENT 'metedata',
  `res_code` varchar(12) NOT NULL COMMENT '성공여부',
  `reg_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 시간',
  PRIMARY KEY (`seq`, `ptn_month`, `ptn_day`, `log_date`, `user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
PARTITION BY LIST (ptn_month)
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
 PARTITION p12 VALUES IN (12) ENGINE = InnoDB);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
