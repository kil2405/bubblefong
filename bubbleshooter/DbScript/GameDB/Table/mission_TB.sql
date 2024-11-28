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

/*Table structure for table `mission_TB` */

DROP TABLE IF EXISTS `mission_TB`;

CREATE TABLE `mission_TB` (
  `user_id` INTEGER(11) NOT NULL COMMENT '유저의 고유값',
  `index` INTEGER(11) NOT NULL COMMENT '유저의 고유값',
  `mission_id` INTEGER(11) NOT NULL COMMENT '미션 고유값',
  `status` TINYINT(4) NOT NULL COMMENT '미션 상태값, 0:기본상태, 1:진행중, 2:보상을 받을 수 있는 상태',
  `hero_type` TINYINT(4) NOT NULL COMMENT '미션중인 영웅 타입',
  `hero_uuid` VARCHAR(48) NOT NULL COMMENT '미션중인 캐릭터 uuid',
  `mission_start_time` BIGINT(20) NOT NULL COMMENT '미션 시작시간',
  `mission_reward_time` BIGINT(20) NOT NULL COMMENT '보상받은 시간',
  `mission_update_time` INT(11) NOT NULL COMMENT '미션 갱신일자',
  `cur_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최근 업데이트 시간',
  `reg_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 시간',
  PRIMARY KEY (`user_id`,`index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='유저 미션정보';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
