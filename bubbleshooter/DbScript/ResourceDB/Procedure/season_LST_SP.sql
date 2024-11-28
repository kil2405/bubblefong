DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `season_LST_SP`$$

CREATE PROCEDURE `season_LST_SP`(
)
BEGIN
	SELECT 	
		`id`
	,	season_num
	,	title
	,	start_at
	,	end_at
	FROM 	
		`season`
	;
	
    END$$

DELIMITER ;