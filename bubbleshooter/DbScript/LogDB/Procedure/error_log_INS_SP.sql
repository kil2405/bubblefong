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

/* Procedure structure for procedure `error_log_INS_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `error_log_INS_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `error_log_INS_SP`( 
	  
	  IN in_month			INT
	, IN in_log_time		INT
	, IN in_user_id         INT
	, IN in_request_url	    VARCHAR(128)
	, IN in_error_code      INT
	, IN in_request_body    VARCHAR(4096)
	, IN in_error_text      VARCHAR(8192)
)
proc_label:BEGIN
INSERT INTO error_log_tb
	(
		ptn_month
	,	log_time
	,	user_id
	, 	request_url
	, 	error_code
	,	request_body
	, 	error_text
	, 	reg_time
	) 
	VALUES 
	(
		in_month
	,	in_log_time
	,   in_user_id  
	,   in_request_url		
	,   in_error_code
	,   in_request_body	
	,   in_error_text  
	,   NOW()
	)
	;
	
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
