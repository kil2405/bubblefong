/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.20-log : Database - BS_log_00
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`BS_log_00` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `BS_log_00`;

/* Procedure structure for procedure `user_connect_log_LST_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `user_connect_log_LST_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `user_connect_log_LST_SP`(
	IN in_user_id 					BIGINT,
	IN in_start_date				BIGINT,
	IN in_end_date					BIGINT
)
BEGIN
	
	IF in_user_id > 0 THEN
		SELECT ptn_month, ptn_day, log_date, user_id, level, gold, cash, star, rank_tier, rank_point, region, is_new
		FROM user_connect_TB
		WHERE 
			user_id = in_user_id
			AND
			( 	log_date >= in_start_date 
				AND
				log_date <= in_end_date
			);
	ELSE
		SELECT ptn_month, ptn_day, log_date, user_id, level, gold, cash, star, rank_tier, rank_point, region, is_new
		FROM user_connect_TB
		WHERE
			( 	log_date >= in_start_date 
				AND
				log_date <= in_end_date
			);
	
	END IF;
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
