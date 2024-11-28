DELIMITER $$

USE `bbf_game`$$

DROP PROCEDURE IF EXISTS `rank_LST_SP`$$

CREATE PROCEDURE `rank_LST_SP`(
	IN in_user_id			BIGINT,
	IN in_room_uid			VARCHAR(48)
    )
BEGIN
	SELECT 	
		id
	,	room_uid
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
	FROM 	
		`rank`
	WHERE 	
		user_id = in_user_id
		AND
		room_uid = in_room_uid
	;
	
    END$$

DELIMITER ;