/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.20-log : Database - bbf_game
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bbf_game` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `bbf_game`;

/*Table structure for table `event_tb` */

DROP TABLE IF EXISTS `event_tb`;

CREATE TABLE `event_tb` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '이벤트 id',
  `event_type` tinyint(4) NOT NULL COMMENT '이벤트 타입(0: 모든유저, 1:신규유저, 2:복귀유저)',
  `event_name` varchar(128) NOT NULL COMMENT '이벤트 명',
  `start_time` bigint(20) NOT NULL COMMENT '이벤트 시작시간',
  `end_time` bigint(20) NOT NULL COMMENT '이벤트 종료시간',
  `item_0` varchar(128) NOT NULL DEFAULT '' COMMENT '이벤트 보상 아이템',
  `item_1` varchar(128) NOT NULL DEFAULT '' COMMENT '이벤트 보상 아이템',
  `item_2` varchar(128) NOT NULL DEFAULT '' COMMENT '이벤트 보상 아이템',
  `item_3` varchar(128) NOT NULL DEFAULT '' COMMENT '이벤트 보상 아이템',
  `item_4` varchar(128) NOT NULL DEFAULT '' COMMENT '이벤트 보상 아이템',
  `valid` TINYINT(4) NOT NULL COMMENT '이벤트 진행 여부 (0: 중지, 1: 진행)',
  `cur_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최근 업데이트 시간',
  `reg_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 시간',
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
