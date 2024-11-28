DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `ranking_season_LST_SP`$$

CREATE PROCEDURE `ranking_season_LST_SP`(
)
BEGIN
	SELECT 	
		`index`
	,	ranking_season_id
	,	previous_season
	,	UNIX_TIMESTAMP(season_start) as season_start
	,	UNIX_TIMESTAMP(season_end) as season_end
	,	season_desc
	FROM 	
		`ranking_season`
	;
	
    END$$

DELIMITER ;