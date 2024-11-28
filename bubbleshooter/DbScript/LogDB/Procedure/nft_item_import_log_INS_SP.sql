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

/* Procedure structure for procedure `nft_item_import_log_INS_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `nft_item_import_log_INS_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `nft_item_import_log_INS_SP`(
	IN in_ptn_month 			TINYINT,
	IN in_ptn_day	 			TINYINT,
	IN in_log_date 				BIGINT,
	IN in_user_id				INT,
	IN in_wallet				VARCHAR(255), 
	IN in_url					VARCHAR(48),
	IN in_market_id				VARCHAR(4),
	IN in_created_game_yn		VARCHAR(2),
	IN in_item_type				INT, 
	IN in_item_id				INT,
	IN in_item_count		 	INT,
	IN in_grade					INT,
	IN in_level		 			INT,
	IN in_upgrade				INT,
	IN in_skill1			 	INT,
	IN in_skill2			 	INT,
	IN in_skill3			 	INT
    )
BEGIN
	INSERT INTO nft_item_import_log_tb
	(
		ptn_month
	, 	ptn_day
	, 	log_date
	, 	user_id
	, 	wallet
	, 	url
	,	market_id
	,	created_game_yn
	, 	item_type
	, 	item_id
	, 	item_count
	, 	grade
	, 	level
	, 	upgrade
	,	skill1
	,	skill2
	,	skill3
	)
	VALUES (
		in_ptn_month
	, 	in_ptn_day
	, 	in_log_date
	, 	in_user_id
	, 	in_wallet
	, 	in_url
	,	in_market_id
	,	in_created_game_yn
	, 	in_item_type
	, 	in_item_id
	, 	in_item_count
	, 	in_grade
	, 	in_level
	, 	in_upgrade
	,	in_skill1
	,	in_skill2
	,	in_skill3
	);
	
	SELECT 	1;
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
