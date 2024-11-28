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

/* Procedure structure for procedure `coupon_log_INS_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `coupon_log_INS_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `coupon_log_INS_SP`(
	IN in_coupon_id				BIGINT,
	IN in_coupon				VARCHAR(128),
	IN in_user_id				BIGINT

    )
BEGIN
	INSERT IGNORE INTO coupon_log_TB
	(
	 	coupon_id
	,	coupon
	, 	user_id
		
	)
	VALUES (
	 	in_coupon_id
	,	in_coupon
	, 	in_user_id
	);
	
	SELECT 	1;
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
