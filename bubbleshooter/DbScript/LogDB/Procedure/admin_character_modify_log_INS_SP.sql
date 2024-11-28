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

/* Procedure structure for procedure `admin_character_modify_log_INS_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `admin_character_modify_log_INS_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `admin_character_modify_log_INS_SP`( 
	  
	IN in_ptn_month 						TINYINT,
	IN in_log_time 							INT,
	IN in_member_id							VARCHAR(20),
	IN in_user_id							BIGINT,
	IN in_character_id 						INT,
	IN in_name								VARCHAR(128),
	IN in_grade 							TINYINT,
	IN in_character_level					TINYINT,
	IN in_character_enhance					TINYINT,
	IN in_daily_mining						INT,
	IN in_lock	 							TINYINT,
	IN in_is_reward							TINYINT,
	IN in_valid 							TINYINT
		
    )
BEGIN
	INSERT INTO admin_character_modify_log_TB
	(
		ptn_month
	,	log_time
	,	member_id
	,	user_id
	,   character_id 	
	,	name
	,   grade 			
	,   character_level	
	,   character_enhance	
	,   daily_mining		
	,   `lock`	 			
	,   is_reward			
	,   valid 			
	
	)
	VALUES (
		in_ptn_month 		
	,	in_log_time 			
	,	in_member_id			
	,	in_user_id			
	,	in_character_id 
	,	in_name
	,	in_grade 			
	,	in_character_level	
	,	in_character_enhance	
	,	in_daily_mining		
	,	in_lock	 			
	,	in_is_reward			
	,	in_valid 			
				
	);
	
	SELECT 	1;
	
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
