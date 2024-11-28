/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.20-log : Database - BS_game_00
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`BS_game_00` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `BS_game_00`;

/* Procedure structure for procedure `admin_mail_LST_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `admin_mail_LST_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `admin_mail_LST_SP`(	
	IN in_mail_month		TINYINT,
	IN in_user_id			BIGINT,
	IN in_mail_time			INT,
	IN in_state 			TINYINT
)
BEGIN

		SELECT 	mail_month, mail_type, mail_id, user_id, mail_time, title, description, mail_item_0, mail_item_1, mail_item_2, mail_item_3, mail_item_4,state, expired_time, open_time, receive_time
		FROM 	mail_TB
		WHERE	user_id = in_user_id ;

END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
