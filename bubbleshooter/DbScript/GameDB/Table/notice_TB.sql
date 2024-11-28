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

/*Table structure for table `notice_tb` */

DROP TABLE IF EXISTS `notice_tb`;

CREATE TABLE `notice_tb` (
  `notice_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '고유 id',
  `notice_title` VARCHAR(128) NOT NULL COMMENT '공지 제목',
  `type` TINYINT(4) DEFAULT NULL COMMENT 'URL: 0, TEXT: 1',
  `text` TEXT COMMENT 'URL 혹은 공지사항',
  `image_address` TEXT COMMENT '이미지의 주소값',
  `start_time` INT(11) NOT NULL COMMENT '공지 시작 시간',
  `end_time` INT(11) NOT NULL COMMENT '공지 끝나는 시간',
  `sort_number` INT(11) DEFAULT NULL COMMENT '정렬 순서',
  `valid` TINYINT(4) NOT NULL DEFAULT '0' NULL COMMENT '0:삭제, 1:정상',
  `reg_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 시간',
  PRIMARY KEY (`notice_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;