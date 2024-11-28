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

/* Procedure structure for procedure `user_balance_export_log_INSUDT_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `user_balance_export_log_INSUDT_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `user_balance_export_log_INSUDT_SP`(
	IN in_user_id			INTEGER,
	IN in_item_type			TINYINT,
	IN in_item_id			INTEGER,
	IN in_item_count		INTEGER,
	IN in_update_date		INTEGER
    )
BEGIN
	INSERT INTO user_balance_export_log_tb 
	(
		user_id
	,	item_type
	,	item_id
	,	item_count
	,	update_date
	)
	VALUES 
	(
		in_user_id
	,	in_item_type
	,	in_item_id
	,	in_item_count
	,	in_update_date
	) ON DUPLICATE KEY UPDATE 
		item_type = in_item_type
	,	item_count = in_item_count
	,	update_date = in_update_date;
	
	SELECT 1;
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
