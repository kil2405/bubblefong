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

/* Procedure structure for procedure `admin_log_INS_SP` */

/*!50003 DROP PROCEDURE IF EXISTS  `admin_log_INS_SP` */;

DELIMITER $$

/*!50003 CREATE PROCEDURE `admin_log_INS_SP`( 
	  
	IN in_ptn_month 						TINYINT,
	IN in_log_time 							INT,
	IN in_member_id							VARCHAR(20),
	IN in_request_url						VARCHAR(128),
	
	IN in_user_id							BIGINT,
	IN in_uuid								VARCHAR(48),
	IN in_nickname							VARCHAR(25),
	IN in_vip								TINYINT,
	IN in_wallet							VARCHAR(255),
	
	IN in_coupon_id							BIGINT,
	IN in_coupon							VARCHAR(128),
	IN in_limit_count						INT,
	
	IN in_event_id							BIGINT,
	IN in_name								VARCHAR(128),
	IN in_event_type						TINYINT,
	
	IN in_season								INT,
	IN in_before_season							INT,
	IN in_season_reward							INT,
	IN in_season_reward_score					INT,
	IN in_rank_season_tier						TINYINT,
	IN in_rank_season_max_tier					TINYINT,
	IN in_rank_season_score						INT,
	IN in_rank_season_max_score					INT,
	IN in_rank_private_season_count				INT,
	IN in_rank_private_season_win_streak		INT,
	IN in_rank_private_season_top_1				INT,
	IN in_rank_private_season_top_3				INT,
	IN in_rank_private_season_top_10			INT,
	IN in_rank_team_season_count              	INT,
	IN in_rank_team_season_win_count            INT,
	IN in_rank_team_season_win_streak           INT,
	IN in_rank_team_season_mvp                 	INT,
	IN in_rank_team_season_mvp_streak           INT,
	
	IN in_character_id						INT,
	IN in_character_enhance					TINYINT,
	IN in_daily_mining						INT,
	IN in_is_reward							TINYINT,
	
	IN in_partner_id						INT,
	
	IN in_mail_id							BIGINT,
	IN in_title								VARCHAR(128),
	IN in_state								TINYINT,
	IN in_description						VARCHAR(255),
	IN in_mail_time							INT,
	IN in_expired_time						BIGINT,
	
	IN in_uid								VARCHAR(48),
	IN in_lock								TINYINT,
	IN in_valid								TINYINT,
	IN in_level								INT,
	IN in_grade								TINYINT,
	
	IN in_start_time						INT,
	IN in_end_time							INT,
	
	IN in_item_type							TINYINT, 
	IN in_item_id							INT,
	IN in_item_count 						INT,
	IN in_item_grade 						TINYINT,
	IN in_item_level 						TINYINT,
	IN in_enhance 							TINYINT,
	IN in_skill_1	 						INT,
	IN in_effect_grade_1					TINYINT,
	IN in_skill_2	 						INT,
	IN in_effect_grade_2					TINYINT,
	IN in_skill_3	 						INT,
	IN in_effect_grade_3					TINYINT,
	IN in_is_delete							TINYINT

    )
BEGIN
	INSERT INTO admin_log_TB
	(
		ptn_month
	,	log_time
	,	member_id
	,	request_url
	,	user_id
	,	uuid
	,	nickname
	,	vip
	,	wallet
	,	coupon_id
	,	coupon
	,	limit_count
	,	event_id
	,	name
	,	event_type
	,	season						
	,   before_season				
	,   season_reward				
	,   season_reward_score			
	,   rank_season_tier				
	,   rank_season_max_tier			
	,   rank_season_score			
	,   rank_season_max_score		
	,   rank_private_season_count	
	,   rank_private_season_win_streak
	,   rank_private_season_top_1	
	,   rank_private_season_top_3	
	,   rank_private_season_top_10	
	,   rank_team_season_count        
	,   rank_team_season_win_count    
	,   rank_team_season_win_streak   
	,   rank_team_season_mvp          
	,   rank_team_season_mvp_streak   
	,	character_id		
	,   character_enhance	
	,   daily_mining		
	,   is_reward			
	,   partner_id		
	,   mail_id			
	,   title				
	,   state				
	,   description		
	,   mail_time			
	,   expired_time		
	,   uid				
	,   `lock`				
	,   valid				
	,   level				
	,   grade				
	,   start_time		
	,   end_time			
	,   item_type			
	,   item_id			
	,   item_count 		
	,   item_grade 		
	,   item_level 		
	,   enhance 			
	,   skill_1	 		
	,   effect_grade_1	
	,   skill_2	 		
	,   effect_grade_2	
	,   skill_3	 		
	,   effect_grade_3	
    ,   is_delete			

	)
	VALUES (
		in_ptn_month 					
	,	in_log_time 						
	,	in_member_id						
	,	in_request_url					
	,	in_user_id						
	,	in_uuid							
	,	in_nickname						
	,	in_vip							
	,	in_wallet						
	,	in_coupon_id						
	,	in_coupon						
	,	in_limit_count					
	,	in_event_id						
	,	in_name							
	,	in_event_type					
	,	in_season						
	,	in_before_season					
	,	in_season_reward					
	,	in_season_reward_score			
	,	in_rank_season_tier				
	,	in_rank_season_max_tier			
	,	in_rank_season_score				
	,	in_rank_season_max_score			
	,	in_rank_private_season_count		
	,	in_rank_private_season_win_streak
	,	in_rank_private_season_top_1		
	,	in_rank_private_season_top_3		
	,	in_rank_private_season_top_10	
	,	in_rank_team_season_count        
	,	in_rank_team_season_win_count    
	,	in_rank_team_season_win_streak   
	,	in_rank_team_season_mvp          
	,	in_rank_team_season_mvp_streak   
	,	in_character_id					
	,	in_character_enhance				
	,	in_daily_mining					
	,	in_is_reward						
	,	in_partner_id					
	,	in_mail_id						
	,	in_title							
	,	in_state							
	,	in_description					
	,	in_mail_time						
	,	in_expired_time					
	,	in_uid							
	,	in_lock							
	,	in_valid							
	,	in_level							
	,	in_grade							
	,	in_start_time					
	,	in_end_time						
	,	in_item_type						
	,	in_item_id						
	,	in_item_count 					
	,	in_item_grade 					
	,	in_item_level 					
	,	in_enhance 						
	,	in_skill_1	 					
	,	in_effect_grade_1				
	,	in_skill_2	 					
	,	in_effect_grade_2				
	,	in_skill_3	 					
	,	in_effect_grade_3				
	,	in_is_delete						
		
	);
	
	SELECT 	1;
	
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
