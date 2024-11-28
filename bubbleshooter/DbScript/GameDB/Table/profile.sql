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

/*Table structure for table `profile` */

DROP TABLE IF EXISTS `profile`;

CREATE TABLE `profile` (
  `user_id` INTEGER(11) NOT NULL COMMENT '유저 고유아이디',
  `profile_id` INTEGER(11) NOT NULL COMMENT '프로필 아이디',
  `item_type` INTEGER(11) NOT NULL COMMENT '프로필의 아이템타입',
  `character_id` INTEGER(11) NOT NULL COMMENT '프로필과 연동된 캐릭터ID',
  `is_new` TINYINT(4) NOT NULL COMMENT '신규 여부 (0: 기존, 1: 신규)',
  `is_have` TINYINT(4) NOT NULL COMMENT '보유 여부 (0: 미보유, 1: 보유)',
  `is_use` TINYINT(4) NOT NULL COMMENT '착용 여부 (0: 미착용, 1:착용)',
  `cur_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최근 업데이트 시간',
  `reg_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 시간',
  PRIMARY KEY (`user_id`, `profile_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='유저 프로필 정보';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
