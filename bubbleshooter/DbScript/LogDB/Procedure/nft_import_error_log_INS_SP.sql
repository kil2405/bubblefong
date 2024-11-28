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

/* Procedure structure for procedure `nft_import_error_log_INS_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `nft_import_error_log_INS_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `nft_import_error_log_INS_SP`(
	IN in_ptn_month				TINYINT,
	IN in_ptn_day				INTEGER,
	IN in_log_date				BIGINT,
	IN in_user_id				INTEGER,
	IN in_created_game_yn		VARCHAR(2),
	IN in_hash					VARCHAR(256),
	IN in_secret				VARCHAR(20),
	IN in_unique				VARCHAR(20),
	IN in_json					JSON
    )
BEGIN
	INSERT INTO nft_import_error_log_tb
	(
	 	ptn_month
	,	ptn_day
	, 	log_date
	, 	user_id
	,	created_game_yn
	,	hash
	,	secret
	,	`unique`
	,	json
	)
	VALUES (
	 	in_ptn_month
	,	in_ptn_day
	, 	in_log_date
	, 	in_user_id
	,	in_created_game_yn
	,	in_hash
	,	in_secret
	,	in_unique
	,	in_json
	);
	
	SELECT 	1;
	
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
