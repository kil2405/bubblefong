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

/* Procedure structure for procedure `attendance_event_log_INS_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `attendance_event_log_INS_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `attendance_event_log_INS_SP`(
	IN in_ptn_month 					TINYINT,
	IN in_ptn_day	 					TINYINT,
	IN in_log_time 						BIGINT,
	IN in_user_id						INTEGER,
	IN in_event_no						INTEGER,
	IN in_event_day						INTEGER,
	IN in_reward_day					INTEGER
    )
BEGIN
	INSERT INTO attendance_event_log_tb
	(
		ptn_month
	, 	ptn_day
	, 	log_time
	, 	user_id
	, 	event_no
	,	event_day
	,	reward_day
	)
	VALUES (
		in_ptn_month
	, 	in_ptn_day
	, 	in_log_time
	, 	in_user_id
	, 	in_event_no
	,	in_event_day
	,	in_reward_day
	) ON DUPLICATE KEY UPDATE
		ptn_month = in_ptn_month
	,	ptn_day = in_ptn_day
	,	log_time = in_log_time
	,	event_no = in_event_no
	,	event_day = in_event_day
	,	reward_day = in_reward_day
	;
	
	SELECT 	1;
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
