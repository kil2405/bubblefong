DELIMITER $$

USE `bbf_game`$$

DROP PROCEDURE IF EXISTS `gacha_reward_LST_SP`$$

CREATE PROCEDURE `gacha_reward_LST_SP`(
		IN in_seq			INT
	,	IN in_gacha_id		INT
    )
BEGIN
	SELECT 	
		`seq`
	,	gacha_id
	,	grade
	,	item_type
	,	item_id
	,	item_count
	,	hero_grade
	,	total_count
	,	prize_count
	FROM 	
		gacha_reward_tb
	WHERE
		`seq` = in_seq
	AND
		gacha_id = in_gacha_id ;
	
    END$$

DELIMITER ;