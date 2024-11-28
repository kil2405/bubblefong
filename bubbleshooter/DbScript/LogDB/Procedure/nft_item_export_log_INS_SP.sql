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

/* Procedure structure for procedure `nft_item_export_log_INS_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `nft_item_export_log_INS_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `nft_item_export_log_INS_SP`(
	IN in_ptn_month 			TINYINT,
	IN in_ptn_day	 			INTEGER,
	IN in_log_date 				BIGINT,
	IN in_user_id				INTEGER,
	IN in_wallet				VARCHAR(255), 
	IN in_url					VARCHAR(48),
	IN in_uid					VARCHAR(255),
	IN in_meta_data				JSON,
	IN in_res_code				VARCHAR(12)
    )
BEGIN
	INSERT IGNORE INTO nft_item_export_log_tb
	(
		ptn_month
	, 	ptn_day
	, 	log_date
	, 	user_id
	, 	wallet
	, 	url
	,	uid
	, 	meta_data
	,	res_code
	)
	VALUES (
		in_ptn_month
	, 	in_ptn_day
	, 	in_log_date
	, 	in_user_id
	, 	in_wallet
	, 	in_url
	,	in_uid
	, 	in_meta_data
	,	in_res_code
	);
	
	SELECT LAST_INSERT_ID();
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
