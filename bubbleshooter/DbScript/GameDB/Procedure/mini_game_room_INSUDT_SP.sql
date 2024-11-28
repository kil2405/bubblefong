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

/* Procedure structure for procedure `mini_game_room_INSUDT_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `mini_game_room_INSUDT_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `mini_game_room_INSUDT_SP`(
	IN in_uuid				VARCHAR(48),
	IN in_game_id			INTEGER,
	IN in_create_at			INTEGER,
	IN in_game_started_at	INTEGER,
	IN in_users				TEXT,
	IN in_expired_at		INTEGER
    )
BEGIN
	INSERT INTO mini_game_room 
	(
		`uuid`
	,	game_id
	,	created_at
	,	game_started_at
	,	users
	,	expired_at
	)
	VALUES 
	(
		in_uuid
	,	in_game_id
	,	in_create_at
	,	in_game_started_at
	,	in_users
	,	in_expired_at
	) ON DUPLICATE KEY UPDATE 
		game_id = in_game_id
	,	created_at = in_create_at
	,	game_started_at = in_game_started_at
	,	users = in_users
	,	expired_at = in_expired_at ;
	
	SELECT 1;
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
