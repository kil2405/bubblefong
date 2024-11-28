DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `ranking_tier_LST_SP`$$

CREATE PROCEDURE `ranking_tier_LST_SP`(
)
BEGIN
	SELECT 	
		`id`
	,	ranking_season_id
	,	ranking_tier
	,	tier_name
	,	tier_icon
	,	season_reward_spine
	,	regulate_tier
	,	ranking_tier_standard_point
	,	ranking_max_rp
	,	ranking_min_rp
	FROM 	
		`ranking_tier`
	;
	
    END$$

DELIMITER ;