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

/* Procedure structure for procedure `game_money_log_INS_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `game_money_log_INS_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `game_money_log_INS_SP`(
	IN in_ptn_month 			TINYINT,
	IN in_ptn_day	 			TINYINT,
	IN in_log_date 				INT,
	IN in_log_type 				VARCHAR(128),
	IN in_user_id				BIGINT,
	IN in_money_type			TINYINT,
	IN in_item_id				INT,
	IN in_add_value				INT,
	IN in_total_value			BIGINT,
	IN in_description			VARCHAR(128)
    )
BEGIN
	INSERT IGNORE INTO game_money_log_tb
	(
		ptn_month
	, 	ptn_day
	, 	log_date
	,	log_type
	, 	user_id
	, 	money_type
	,	item_id
	, 	add_value
	, 	total_value
	,	description
	)
	VALUES (
		in_ptn_month
	, 	in_ptn_day
	, 	in_log_date
	,	in_log_type
	, 	in_user_id
	, 	in_money_type
	,	in_item_id
	, 	in_add_value
	, 	in_total_value
	,	in_description
	);
	
	SELECT 	1;
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
