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

/*Table structure for table `rank` */

DROP TABLE IF EXISTS `rank`;

CREATE TABLE `rank` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'index',
  `room_uid` VARCHAR(48) NOT NULL COMMENT '방 uid',
  `user_id` INTEGER NOT NULL COMMENT 'userId',
  `user_uuid` VARCHAR(48) NOT NULL COMMENT '유저 uuid',
  `season` INTEGER NOT NULL COMMENT '진행한 시즌',
  `previous_rank_point` INTEGER NOT NULL COMMENT '이전 랭킹포인트',
  `acquired_rank_point` INTEGER NOT NULL COMMENT '획득 랭킹포인트',
  `total_rank_point` INTEGER NOT NULL COMMENT '최종 랭킹포인트',
  `previous_exp_point` INTEGER NOT NULL COMMENT '이전 exp',
  `acquired_exp_point` INTEGER NOT NULL COMMENT '획득 exp',
  `total_exp_point` INTEGER NOT NULL COMMENT '최종 exp',
  `rank` INTEGER NOT NULL COMMENT '경기 랭킹',
  `ticket_rank` INTEGER NOT NULL COMMENT '경쟁전 랭킹',
  `gold` INTEGER NOT NULL COMMENT '획득 골드',
  `ruby` INTEGER NOT NULL COMMENT '획득 루비',
  `diamond` INTEGER NOT NULL COMMENT '획득 다이아몬드',
  `character_uid` VARCHAR(48) NOT NULL COMMENT '캐릭터 uid',
  `partner_uid_1` VARCHAR(48) NOT NULL COMMENT '파트너1 uid',
  `partner_uid_2` VARCHAR(48) NOT NULL COMMENT '파트너2 uid',
  `partner_uid_3` VARCHAR(48) NOT NULL COMMENT '파트너3 uid',
  `cur_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최근 업데이트 시간',
  `reg_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 시간',
  PRIMARY KEY (`id`),
  INDEX(`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='매칭 방 정보';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
