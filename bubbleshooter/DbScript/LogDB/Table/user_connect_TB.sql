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

/*Table structure for table `user_connect_tb` */

DROP TABLE IF EXISTS `user_connect_tb`;

CREATE TABLE `user_connect_tb` (
  `ptn_month` tinyint(4) NOT NULL COMMENT '로그 월',
  `ptn_day` tinyint(4) NOT NULL COMMENT '로그 일',
  `log_date` bigint(20) NOT NULL COMMENT '로그 데이터 시간',
  `user_id` INTEGER(20) NOT NULL COMMENT '사용자 아이디',
  `uuid` VARCHAR(255) NOT NULL COMMENT '유저 고유아이디',
  `level` int(11) NOT NULL COMMENT '유저의 게임 레벨',
  `gold` int(11) NOT NULL DEFAULT '0' COMMENT '보유 골드',
  `ruby` int(11) NOT NULL DEFAULT '0' COMMENT '보유 루비',
  `diamond` int(11) NOT NULL DEFAULT '0' COMMENT '보유 다이아몬드',
  `version` VARCHAR(10) NOT NULL DEFAULT '0' COMMENT '접속 클라이언트 버전',
  `rank_tier` tinyint(4) NOT NULL COMMENT 'rank_tier',
  `rank_point` int(11) NOT NULL DEFAULT '0' COMMENT 'rank point',
  `region` varchar(8) NOT NULL COMMENT '접속 지역',
  `is_new` tinyint(4) NOT NULL COMMENT '신규 가입 여부 (0 : 기존 가입 유저, 1 : 신규 유저, 2 : 복귀유저)',
  `reg_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 시간',
  PRIMARY KEY (`ptn_month`, `log_date`, `user_id`)
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
