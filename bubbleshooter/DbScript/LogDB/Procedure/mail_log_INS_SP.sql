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

/* Procedure structure for procedure `mail_log_INS_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `mail_log_INS_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `mail_log_INS_SP`(
	IN in_ptn_month 			TINYINT,
	IN in_ptn_day	 			TINYINT,
	IN in_log_date 				INT,
	IN in_user_id				BIGINT,
	IN in_mail_id				BIGINT,
	IN in_log_mail_type			TINYINT, 
	IN in_mailItem_index0		BIGINT,
	IN in_mailItem_index1		BIGINT,
	IN in_mailItem_index2		BIGINT,
	IN in_mailItem_index3		BIGINT,
	IN in_mailItem_index4		BIGINT
    )
BEGIN
	INSERT IGNORE INTO mail_log_TB
	(
		ptn_month 	
	, 	ptn_day	 	
	, 	log_date 		
	,	user_id	
	,	mail_id
	, 	log_mail_type	
	,	mailItem_index0
    ,   mailItem_index1
    ,   mailItem_index2
    ,   mailItem_index3
    ,   mailItem_index4
	)
	VALUES (
		in_ptn_month 	
	, 	in_ptn_day	 	
	, 	in_log_date 		
	,	in_user_id	
	,	in_mail_id
	, 	in_log_mail_type	
	,	in_mailItem_index0
    ,   in_mailItem_index1
    ,   in_mailItem_index2
    ,   in_mailItem_index3
    ,   in_mailItem_index4
	);
	
	SELECT 	1;
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
