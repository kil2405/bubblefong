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

/*Table structure for table `gamecharacter` */

DROP TABLE IF EXISTS `gamecharacter`;

CREATE TABLE `gamecharacter` (
  `user_id` INTEGER(11) NOT NULL COMMENT '유저의 고유값',
  `uid` VARCHAR(48) NOT NULL COMMENT '캐릭터 고유값',
  `character_id` INTEGER(11) NOT NULL COMMENT '캐릭터 id',
  `grade` INTEGER(11) NOT NULL COMMENT '캐릭터 등급',
  `character_level` INTEGER(11) NOT NULL COMMENT '캐릭터 레벨',
  `character_upgrade` INTEGER(11) NOT NULL COMMENT '캐릭터 레벨',
  `active_skill` INTEGER(11) NOT NULL COMMENT '캐릭터 엑티브 스킬',
  `is_lock` TINYINT(4) NOT NULL COMMENT 'lock 여부 (0:unlock, 1:lock)',
  `dailyearn` INTEGER(11) NOT NULL COMMENT '현재 BP',
  `dailyearnlimit` INTEGER(11) NOT NULL COMMENT '현재 받을 수 있는 붉은 다이아 개수',
  `is_reward` TINYINT(4) NOT NULL COMMENT '보상 획득 여부 (0:획득안함, 1:획득)',
  `is_nft` TINYINT(4) NOT NULL COMMENT 'nft캐릭터 여부',
  `is_new` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '캐릭터 New 표시 정보',
  `is_mission` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '미션진행중인 캐릭터 여부 0: 미션 미진행, 1: 미션진행중',
  `use_or_not` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '캐릭터 사용여부',
  `cur_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최근 업데이트 시간',
  `reg_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 시간',
  PRIMARY KEY (`user_id`,`character_id`, `uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='캐릭터 카드 정보';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
