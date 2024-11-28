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

/*Table structure for table `partner` */

DROP TABLE IF EXISTS `partner`;

CREATE TABLE `partner` (
  `user_id` INTEGER(11) NOT NULL COMMENT '유저의 고유값',
  `uid` VARCHAR(48) NOT NULL COMMENT '동료 유니크 고유값',
  `partner_id` INTEGER(11) NOT NULL COMMENT '동료 고유값',
  `grade` INTEGER(11) NOT NULL COMMENT '동료 등급',
  `partner_level` INTEGER(11) NOT NULL COMMENT '동료 레벨',
  `upgrade` INTEGER(11) NOT NULL COMMENT '동료 레벨',
  `skill_1` INTEGER(11) NOT NULL COMMENT '동료 스킬 1',
  `skill_2` INTEGER(11) NOT NULL COMMENT '동료 스킬 2',
  `skill_3` INTEGER(11) NOT NULL COMMENT '동료 스킬 3',
  `is_lock` TINYINT(4) NOT NULL COMMENT 'lock 여부 (0:unlock, 1:lock)',
  `is_nft` tinyint(4) NOT NULL DEFAULT '0' COMMENT '동료 NFT 여부',
  `is_new` tinyint(4) NOT NULL DEFAULT '0' COMMENT '동료 New 표시 정보',
  `is_mission` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '미션진행중인 파트너 여부 0: 미션 미진행, 1: 미션진행중',
  `use_or_not` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '파트너 사용여부',
  `cur_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최근 업데이트 시간',
  `reg_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 시간',
  PRIMARY KEY (`user_id`,`partner_id`,`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='동료 카드 정보';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
