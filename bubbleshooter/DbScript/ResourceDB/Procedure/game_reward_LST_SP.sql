DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `game_reward_LST_SP`$$

CREATE PROCEDURE `game_reward_LST_SP`(
)
BEGIN
	SELECT 	
		`index`
	,	game_mode
	,	rank_from
	,	rank_to
	,	item_id_0
	,	item_type_0
	,	item_count_0
	FROM 	
		`game_reward`
	;
	
    END$$

DELIMITER ;