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

/* Procedure structure for procedure `profile_INSUDT_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `profile_INSUDT_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `profile_INSUDT_SP`(
	IN in_user_id			INTEGER,
	IN in_profile_id		INTEGER,
	IN in_item_type			INTEGER,
	IN in_character_id		INTEGER,
	IN in_is_new			TINYINT,
	IN in_is_have			TINYINT,
	IN in_is_use			TINYINT
    )
BEGIN
	INSERT INTO profile 
	(
		user_id
	,	profile_id
	,	item_type
	,	character_id
	,	is_new
	,	is_have
	,	is_use
	)
	VALUES 
	(
		in_user_id
	,	in_profile_id
	,	in_item_type
	,	in_character_id
	,	in_is_new
	,	in_is_have
	,	in_is_use
	) ON DUPLICATE KEY UPDATE 
		item_type = in_item_type
	,	character_id = in_character_id
	,	is_new = in_is_new
	,	is_have = in_is_have
	,	is_use = in_is_use ;
	
	SELECT 1;
	
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
