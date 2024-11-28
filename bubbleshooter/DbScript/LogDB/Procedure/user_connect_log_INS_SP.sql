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

/* Procedure structure for procedure `user_connect_log_INS_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `user_connect_log_INS_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `user_connect_log_INS_SP`(
	IN in_ptn_month 			TINYINT,
	IN in_ptn_day	 			TINYINT,
	IN in_log_date 				BIGINT,
	IN in_user_id				BIGINT,
	IN in_uuid					VARCHAR(255),
	IN in_level					INT, 
	IN in_gold					INT,
	IN in_ruby	 				INT, 
	IN in_diamond				INT, 
	IN in_version				VARCHAR(10),
	IN in_rank_tier			 	TINYINT,
	IN in_rank_point			INT,
	IN in_region		 		VARCHAR(8),
	IN in_is_new				BIGINT
    )
BEGIN
	INSERT IGNORE INTO user_connect_tb
	(
		ptn_month
	, 	ptn_day
	, 	log_date
	, 	user_id
	,	uuid
	, 	level
	, 	gold
	, 	ruby
	,	diamond
	, 	version
	, 	rank_tier
	, 	rank_point
	, 	region
	, 	is_new
	)
	VALUES (
		in_ptn_month
	, 	in_ptn_day
	, 	in_log_date
	, 	in_user_id
	,	in_uuid
	, 	in_level
	, 	in_gold
	, 	in_ruby
	, 	in_diamond
	, 	in_version
	, 	in_rank_tier
	, 	in_rank_point
	, 	in_region
	, 	in_is_new
	);
	
	SELECT 	1;
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
