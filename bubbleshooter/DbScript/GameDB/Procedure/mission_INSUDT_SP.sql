/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.20-log : Database - bbf_game
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bbf_game` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `bbf_game`;

/* Procedure structure for procedure `mission_INSUDT_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `mission_INSUDT_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `mission_INSUDT_SP`(
	IN in_user_id				INTEGER,
	IN in_index					INTEGER,
	IN in_mission_id			INTEGER,
	IN in_status				TINYINT,
	IN in_hero_type				TINYINT,
	IN in_hero_uuid				VARCHAR(48),
	IN in_mission_start_time	BIGINT,
	IN in_mission_reward_time	BIGINT,
	IN in_mission_update_time	BIGINT
    )
BEGIN
	INSERT INTO mission_tb
	(
		user_id
	,	`index`
	,	mission_id
	,	status
	,	hero_type
	,	hero_uuid
	,	mission_start_time
	,	mission_reward_time
	,	mission_update_time
	)
	VALUES 
	(
		in_user_id
	,	in_index
	,	in_mission_id
	,	in_status
	,	in_hero_type
	,	in_hero_uuid
	,	in_mission_start_time
	,	in_mission_reward_time
	,	in_mission_update_time
	) ON DUPLICATE KEY UPDATE
		mission_id = in_mission_id
	,	status = in_status
	,	hero_type = in_hero_type
	,	hero_uuid = in_hero_uuid
	,	mission_start_time = in_mission_start_time
	,	mission_reward_time = in_mission_reward_time
	,	mission_update_time = in_mission_update_time ;
	
	SELECT 1;
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
