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

/* Procedure structure for procedure `user_shop_buy_log_INSUDP_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `user_shop_buy_log_INSUDP_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `user_shop_buy_log_INSUDP_SP`(
	IN in_user_id			BIGINT, 
	IN in_product_id		INT, 
	IN in_buy_count			INT,
	IN in_limit_date		BIGINT,
	IN in_last_buy_time		BIGINT
    )
BEGIN
	INSERT INTO user_shop_buy_log_tb(user_id, product_id, buy_count, limit_date, last_buy_time)
	VALUES (in_user_id, in_product_id, in_buy_count, in_limit_date, FROM_UNIXTIME(in_last_buy_time))
	ON DUPLICATE KEY UPDATE 
		buy_count = in_buy_count
	,	limit_date = in_limit_date
	,	last_buy_time = FROM_UNIXTIME(in_last_buy_time);
	
	SELECT 1;
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
