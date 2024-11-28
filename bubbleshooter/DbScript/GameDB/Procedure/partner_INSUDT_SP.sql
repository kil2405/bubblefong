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

/* Procedure structure for procedure `partner_INSUDT_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `partner_INSUDT_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `partner_INSUDT_SP`(
	IN in_user_id			BIGINT, 
	IN in_uid				VARCHAR(48),
	IN in_partner_id		INTEGER,
	IN in_grade				INTEGER,
	IN in_partner_level		INTEGER,
	IN in_upgrade			INTEGER,
	IN in_skill_1			INTEGER,
	IN in_skill_2			INTEGER,
	IN in_skill_3			INTEGER,
	IN in_is_lock			TINYINT,
	IN in_is_nft			TINYINT,
	IN in_is_new			TINYINT,
	IN in_is_mission		TINYINT,
	IN in_use_or_not		TINYINT
    )
BEGIN
	INSERT INTO partner 
	(
		user_id
	,	uid
	,	partner_id
	,	grade
	,	partner_level
	,	upgrade
	,	skill_1
	,	skill_2
	,	skill_3
	,	is_lock
	,	is_nft
	,	is_new
	,	is_mission
	,	use_or_not
	)
	VALUES 
	(
		in_user_id
	,	in_uid
	,	in_partner_id
	,	in_grade
	,	in_partner_level
	,	in_upgrade
	,	in_skill_1
	,	in_skill_2
	,	in_skill_3
	,	in_is_lock
	,	in_is_nft
	,	in_is_new
	, 	in_is_mission
	,	in_use_or_not
	) ON DUPLICATE KEY UPDATE 
		grade = in_grade
	,	partner_level = in_partner_level
	,	upgrade = in_upgrade
	,	skill_1 = in_skill_1
	,	skill_2 = in_skill_2
	,	skill_3 = in_skill_3
	,	is_lock = in_is_lock
	,	is_nft = in_is_nft
	,	is_new = in_is_new
	,	is_mission = in_is_mission
	,	use_or_not = in_use_or_not ;
	
	SELECT 1;
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
