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

/* Procedure structure for procedure `admin_match_modify_log_INS_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `admin_match_modify_log_INS_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `admin_match_modify_log_INS_SP`( 
	  
	IN in_ptn_month 						TINYINT,
	IN in_log_time 							INT,
	IN in_member_id							VARCHAR(20),
	IN in_user_id	 						BIGINT, 
	IN in_season							INT,
	IN in_before_season						INT,
	IN in_season_reward						INT,
	IN in_season_reward_score				INT,
	IN in_rank_season_max_tier				TINYINT,
	IN in_rank_season_score					INT,
	IN in_rank_season_max_score				INT,
	IN in_rank_private_season_count			INT,
	IN in_rank_private_season_win_streak	INT,
	IN in_rank_private_season_top_1			INT,
	IN in_rank_private_season_top_3			INT,
	IN in_rank_private_season_top_10		INT,
	IN in_rank_team_season_count          	INT,
	IN in_rank_team_season_win_count   	    INT,
	IN in_rank_team_season_win_streak   	INT,
	IN in_rank_team_season_mvp            	INT,
	IN in_rank_team_season_mvp_streak      	INT

    )
BEGIN
	INSERT INTO admin_match_modify_log_TB
	(
		ptn_month 					
	,	log_time 						
	,	member_id						
	,	user_id	 					
	,	season						
	,	before_season					
	,	season_reward					
	,	season_reward_score			
	,	rank_season_max_tier			
	,	rank_season_score				
	,	rank_season_max_score			
	,	rank_private_season_count		
	,	rank_private_season_win_streak
	,	rank_private_season_top_1		
	,	rank_private_season_top_3		
	,	rank_private_season_top_10	
	,	rank_team_season_count        
	,	rank_team_season_win_count   	
	,	rank_team_season_win_streak   
	,	rank_team_season_mvp          
	,	rank_team_season_mvp_streak   
	
	)
	VALUES (
		in_ptn_month 					
    ,   in_log_time 						
    ,   in_member_id						
    ,   in_user_id	 					
    ,   in_season						
    ,   in_before_season					
    ,   in_season_reward					
    ,   in_season_reward_score			
    ,   in_rank_season_max_tier			
    ,   in_rank_season_score				
    ,   in_rank_season_max_score			
    ,   in_rank_private_season_count		
    ,   in_rank_private_season_win_streak
    ,   in_rank_private_season_top_1		
    ,   in_rank_private_season_top_3		
    ,   in_rank_private_season_top_10	
    ,   in_rank_team_season_count        
    ,   in_rank_team_season_win_count   	
    ,   in_rank_team_season_win_streak   
    ,   in_rank_team_season_mvp          
    ,   in_rank_team_season_mvp_streak   

	);
	
	SELECT 	1;
	
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
