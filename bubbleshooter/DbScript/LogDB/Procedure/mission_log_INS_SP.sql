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

/* Procedure structure for procedure `mission_log_INS_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `mission_log_INS_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `mission_log_INS_SP`(
	IN in_ptn_month 					TINYINT,
	IN in_ptn_day	 					TINYINT,
	IN in_log_time 						BIGINT,
	IN in_user_id						INTEGER,
	IN in_index							INTEGER,
	IN in_mission_id					INTEGER,
	IN in_status						TINYINT,
	IN in_hero_type						TINYINT,
	IN in_hero_uuid						VARCHAR(48),
	IN in_mission_start_time			BIGINT,
	IN in_mission_reward_time			BIGINT,
	IN in_mission_update_time			BIGINT,
	IN in_reward_item_0					VARCHAR(128),
	IN in_reward_item_1					VARCHAR(128),
	IN in_reward_item_2					VARCHAR(128),
	IN in_reward_item_3					VARCHAR(128),
	IN in_reward_item_4					VARCHAR(128)

    )
BEGIN
	INSERT IGNORE INTO mission_log_tb
	(
		ptn_month
	, 	ptn_day
	, 	log_time
	, 	user_id
	,	index					
	,	mission_id			
	,	status				
	,	hero_type				
	,	hero_uuid				
	,	mission_start_time	
	,	mission_reward_time	
	,	mission_update_time	
	,	reward_item_0			
	,	reward_item_1			
	,	reward_item_2			
	,	reward_item_3			
	,	reward_item_4			
	
	)
	VALUES (
	,	in_ptn_month 			
	,	in_ptn_day	 			
	,	in_log_time 				
	,	in_user_id				
	,	in_index					
	,	in_mission_id			
	,	in_status				
	,	in_hero_type				
	,	in_hero_uuid				
	,	in_mission_start_time	
	,	in_mission_reward_time	
	,	in_mission_update_time	
	,	in_reward_item_0			
	, 	in_reward_item_1
	, 	in_reward_item_2
	,	in_reward_item_3
	,	in_reward_item_4
	
	);
	
	SELECT 	1;
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
