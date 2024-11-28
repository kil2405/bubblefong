DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `gacha_reward_LST_SP`$$

CREATE PROCEDURE `gacha_reward_LST_SP`(
)
BEGIN
	SELECT 	
		`index`
	,	grade
	,	item_type
	,	item_id
	,	item_count
	,	hero_grade
	FROM 	
		`gacha_reward`
	;
	
    END$$

DELIMITER ;