DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `ranking_reward_LST_SP`$$

CREATE PROCEDURE `ranking_reward_LST_SP`(
)
BEGIN
	SELECT 	
		kor_check
	,	ranking_season_id
	,	reward_type
	,	from_tier
	,	to_tier
	,	item_id
	,	item_type
	,	item_count
	FROM 	
		`ranking_reward`
	;
	
    END$$

DELIMITER ;