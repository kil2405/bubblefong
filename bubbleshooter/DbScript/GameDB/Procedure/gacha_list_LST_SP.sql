DELIMITER $$

USE `bbf_game`$$

DROP PROCEDURE IF EXISTS `gacha_list_LST_SP`$$

CREATE PROCEDURE `gacha_list_LST_SP`(
    )
BEGIN
	SELECT 	
		`seq`
	,	gacha_id
	,	order_index
	,	grade_list
	,	start_time
	,	end_time
	,	valid
	FROM 	
		gacha_list ;
	
    END$$

DELIMITER ;