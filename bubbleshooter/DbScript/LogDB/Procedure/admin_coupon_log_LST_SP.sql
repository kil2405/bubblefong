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

/* Procedure structure for procedure `admin_coupon_log_LST_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `admin_coupon_log_LST_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `admin_coupon_log_LST_SP`(
	IN in_user_id				BIGINT,
	IN in_start_date			BIGINT,
	IN in_end_date				BIGINT
    )
BEGIN

	IF in_user_id > 0 THEN
		SELECT seq, coupon_id, coupon, user_id, UNIX_TIMESTAMP(reg_date) as reg_date
		FROM coupon_log_TB
		WHERE 
			user_id = in_user_id
			AND
			( 	UNIX_TIMESTAMP(reg_date) >= in_start_date 
				AND
				UNIX_TIMESTAMP(reg_date) <= in_end_date
			);
	ELSE
		SELECT seq, coupon_id, coupon, user_id, UNIX_TIMESTAMP(reg_date) as reg_date
		FROM coupon_log_TB
		WHERE
			( 	UNIX_TIMESTAMP(reg_date) >= in_start_date 
				AND
				UNIX_TIMESTAMP(reg_date) <= in_end_date
			);
	
	END IF;
	
	END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
