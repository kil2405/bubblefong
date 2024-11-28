/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.20-log : Database - BS_log_00
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`BS_log_00` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `BS_log_00`;

/* Procedure structure for procedure `admin_coupon_modify_log_LST_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `admin_coupon_modify_log_LST_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `admin_coupon_modify_log_LST_SP`()
BEGIN
	SELECT 
		  seq
		, ptn_month
		, log_time 
		, member_id
		, coupon_id
		, coupon		
		, start_time	
		, end_time		
		, limit_count	
		, item_type		
		, item_id		
		, item_count 	
		, grade 		
		, level 		
		, enhance 		
		, skill_1	 	
		, effect_grade_1
		, skill_2	 	
		, effect_grade_2
		, skill_3	 	
		, effect_grade_3
		, is_delete
	
	FROM
		admin_coupon_modify_log_TB
	;
	
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
