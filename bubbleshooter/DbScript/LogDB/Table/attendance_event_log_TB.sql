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

/*Table structure for table `attendance_event_log_tb` */

DROP TABLE IF EXISTS `attendance_event_log_tb`;

CREATE TABLE `attendance_event_log_tb` (
  `ptn_month` TINYINT(4) NOT NULL COMMENT '로그 월',
  `ptn_day` TINYINT(4) NOT NULL COMMENT '로그 일',
  `log_time` BIGINT(11) NOT NULL COMMENT '출석보상 받은 시간',
  `user_id` INTEGER(11) NOT NULL COMMENT '사용자 아이디',
  `event_no` INTEGER(11) NOT NULL COMMENT '이벤트 아이디',
  `event_day` INTEGER(11) NOT NULL COMMENT '출석일자',
  `reward_day` INTEGER(11) NOT NULL COMMENT '보상받을 일자',
  `cur_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최근 업데이트 시간',
  `reg_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 시간',
  PRIMARY KEY (`user_id`, `event_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='출석 이벤트 로그 테이블';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
