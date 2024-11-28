DELIMITER $$

USE `bbf_game`$$

DROP PROCEDURE IF EXISTS `rank_INSUDT_SP`$$

CREATE PROCEDURE `rank_INSUDT_SP`(
		IN in_room_uid									VARCHAR(48)
	,	IN in_user_id									INTEGER
	,	IN in_user_uuid									VARCHAR(48)
	,	IN in_season									INTEGER
	,	IN in_previous_rank_point						INTEGER
	,	IN in_acquired_rank_point						INTEGER
	,	IN in_total_rank_point							INTEGER
	,	IN in_previous_exp_point						INTEGER
	,	IN in_acquired_exp_point						INTEGER
	,	IN in_total_exp_point							INTEGER
	,	IN in_rank										INTEGER
	,	IN in_ticketRank								INTEGER
	,	IN in_gold										INTEGER
	,	IN in_ruby										INTEGER
	,	IN in_diamond									INTEGER
	,	IN in_character_uuid							VARCHAR(48)
	,	IN in_partner_uid_1								VARCHAR(48)
	,	IN in_partner_uid_2								VARCHAR(48)
	,	IN in_partner_uid_3								VARCHAR(48)
)
BEGIN
	INSERT INTO `rank` (
		room_uid
	,	user_id
	,	user_uuid
	,	season
	,	previous_rank_point
	,	acquired_rank_point
	,	total_rank_point
	,	previous_exp_point
	,	acquired_exp_point
	,	total_exp_point
	,	`rank`
	,	ticket_rank
	,	gold
	,	ruby
	,	diamond
	,	character_uid
	,	partner_uid_1
	,	partner_uid_2
	,	partner_uid_3
	)
	VALUES (
		in_room_uid
	,	in_user_id
	,	in_user_uuid
	,	in_season
	,	in_previous_rank_point
	,	in_acquired_rank_point
	,	in_total_rank_point
	,	in_previous_exp_point
	,	in_acquired_exp_point
	,	in_total_exp_point
	,	in_rank
	,	in_ticketRank
	,	in_gold
	,	in_ruby
	,	in_diamond
	,	in_character_uuid
	,	in_partner_uid_1
	,	in_partner_uid_2
	,	in_partner_uid_3
	) ;
		
	SELECT 	1;
END$$

DELIMITER ;