/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.20-log : Database - bbf_log
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bbf_log` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `bbf_log`;

/* Procedure structure for procedure `user_profile_log_INS_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `user_profile_log_INS_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `user_profile_log_INS_SP`(
	IN in_ptn_month 			TINYINT,
	IN in_ptn_day	 			INT,
	IN in_log_date 				BIGINT,
	IN in_user_id				INT,
	IN in_profile_id			INT,
	IN in_item_type				INT,
	IN in_character_id			INT,
	IN in_is_new				TINYINT,
	IN in_is_have				TINYINT,
	IN in_is_use				TINYINT
    )
BEGIN
	INSERT INTO user_profile_log_tb
	(
		ptn_month
	, 	ptn_day
	, 	log_date
	, 	user_id
	,	profile_id
	, 	item_type
	, 	character_id
	, 	is_new
	,	is_have
	, 	is_use
	)
	VALUES (
		in_ptn_month
	, 	in_ptn_day
	, 	in_log_date
	, 	in_user_id
	,	in_profile_id
	, 	in_item_type
	, 	in_character_id
	, 	in_is_new
	, 	in_is_have
	, 	in_is_use
	);
	
	SELECT 	1;
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
