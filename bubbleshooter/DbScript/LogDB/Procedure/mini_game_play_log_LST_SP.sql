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

/* Procedure structure for procedure `mini_game_play_log_LST_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `mini_game_play_log_LST_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `mini_game_play_log_LST_SP`(
	IN in_user_id 						INTEGER,
	IN in_room_id	 					VARCHAR(48)
    )
BEGIN
	SELECT
		seq
	,	ptn_month
	,	ptn_day
	,	log_date
	,	room_uid
	,	user_id
	,	game_id
	,	rank
	,	ruby
	,	is_ticket
	FROM
		mini_game_play_log_tb
	WHERE
		user_id = in_user_id
	AND
		room_uid = in_room_id ;
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
