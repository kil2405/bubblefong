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

/* Procedure structure for procedure `admin_event_modify_log_INS_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `admin_event_modify_log_INS_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `admin_event_modify_log_INS_SP`( 
	  
	IN in_ptn_month 						TINYINT,
	IN in_log_time 							INT,
	IN in_member_id							VARCHAR(20),
	IN in_event_id	 						BIGINT, 
	IN in_name								VARCHAR(128),
	IN in_start_time						BIGINT,
	IN in_end_time							BIGINT,
	IN in_event_type						TINYINT,
	IN in_valid								TINYINT,
	IN in_item_type							TINYINT, 
	IN in_item_id							INT,
	IN in_item_count 						INT,
	IN in_grade 							TINYINT,
	IN in_level 							TINYINT,
	IN in_enhance 							TINYINT,
	IN in_skill_1	 						INT,
	IN in_effect_grade_1					TINYINT,
	IN in_skill_2	 						INT,
	IN in_effect_grade_2					TINYINT,
	IN in_skill_3	 						INT,
	IN in_effect_grade_3					TINYINT,
	IN in_is_delete							TINYINT
	
    )
BEGIN
	INSERT INTO admin_event_modify_log_TB
	(
		ptn_month
	,	log_time
	,	member_id
	,	event_id	 	
	,   name		
	,   start_time	
	,   end_time		
	,   event_type
	,   valid
	,   item_type		
	,   item_id		
	,   item_count 	
	,   grade 		
	,   level 		
	,   enhance 		
	,   skill_1	 	
	,   effect_grade_1
	,   skill_2	 	
	,   effect_grade_2
	,   skill_3	 	
	,   effect_grade_3
	,   is_delete		
	
	)
	VALUES (
		in_ptn_month 					
	,	in_log_time 						
	,	in_member_id	
	,	in_event_id	 	
	,	in_name						
	,	in_start_time	
	,	in_end_time		
	,	in_event_type
	,	in_valid
	,	in_item_type		
	,	in_item_id		
	,	in_item_count 	
	,	in_grade 		
	,	in_level 		
	,	in_enhance 		
	,	in_skill_1	 	
	,	in_effect_grade_1
	,	in_skill_2	 	
	,	in_effect_grade_2
	,	in_skill_3	 	
	,	in_effect_grade_3
	,	in_is_delete		
		
	);
	
	SELECT 	1;
	
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
