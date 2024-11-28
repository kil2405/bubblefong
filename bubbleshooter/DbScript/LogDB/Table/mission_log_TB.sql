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

/*Table structure for table `mission_log_tb` */

DROP TABLE IF EXISTS `mission_log_tb`;

CREATE TABLE `mission_log_tb` (
  `seq` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '번호',
  `ptn_month` tinyint(4) NOT NULL COMMENT '파티셔닝 키',
  `ptn_day` TINYINT(4) NOT NULL COMMENT '로그 일',
  `log_time` int(11) NOT NULL COMMENT '임무 로그 시간',
  `user_id` INTEGER(11) NOT NULL COMMENT '유저의 고유값',
  `index` INTEGER(11) NOT NULL COMMENT '유저의 고유값',
  `mission_id` INTEGER(11) NOT NULL COMMENT '미션 고유값',
  `status` TINYINT(4) NOT NULL COMMENT '미션 상태값, 0:기본상태, 1:진행중, 2:보상을 받을 수 있는 상태',
  `hero_type` TINYINT(4) NOT NULL COMMENT '미션중인 영웅 타입',
  `hero_uuid` VARCHAR(48) NOT NULL COMMENT '미션중인 캐릭터 uuid',
  `mission_start_time` BIGINT(20) NOT NULL COMMENT '미션 시작시간',
  `mission_reward_time` BIGINT(20) NOT NULL COMMENT '보상받은 시간',
  `mission_update_time` INT(11) NOT NULL COMMENT '미션 갱신일자',
  `mail_item_0` VARCHAR(128) DEFAULT NULL COMMENT '메일 아이템 0 string',
  `mail_item_1` VARCHAR(128) DEFAULT NULL COMMENT '메일 아이템 1 string',
  `mail_item_2` VARCHAR(128) DEFAULT NULL COMMENT '메일 아이템 2 string',
  `mail_item_3` VARCHAR(128) DEFAULT NULL COMMENT '메일 아이템 3 string',
  `mail_item_4` VARCHAR(128) DEFAULT NULL COMMENT '메일 아이템 4 string',
  `cur_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최근 업데이트 시간',
  `reg_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 시간',
  PRIMARY KEY (`seq`,`ptn_month`, `log_time`, `user_id`)
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
