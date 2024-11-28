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

/* Procedure structure for procedure `preset_INSUDT_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `preset_INSUDT_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `preset_INSUDT_SP`(
	IN in_user_id			BIGINT, 
	IN in_character_uid		VARCHAR(48),
	IN in_partner1_uid		VARCHAR(48),
	IN in_partner2_uid		VARCHAR(48),
	IN in_partner3_uid		VARCHAR(48)
    )
BEGIN
	INSERT INTO preset 
	(
		user_id
	,	character_uid
	,	partner1_uid
	,	partner2_uid
	,	partner3_uid
	)
	VALUES 
	(
		in_user_id
	,	in_character_uid
	,	in_partner1_uid
	,	in_partner2_uid
	,	in_partner3_uid
	) ON DUPLICATE KEY UPDATE 
		character_uid = in_character_uid
	,	partner1_uid = in_partner1_uid
	,	partner2_uid = in_partner2_uid
	,	partner3_uid = in_partner3_uid ;
	
	SELECT 1;
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
