DELIMITER $$

USE `bbf_game`$$

DROP PROCEDURE IF EXISTS `gacha_reward_INSUDT_SP`$$

CREATE PROCEDURE `gacha_reward_INSUDT_SP`(
		IN	in_seq							INT
	,	IN	in_gacha_id						INT
	,	IN	in_grade						TINYINT
	,	IN	in_item_type					INT
	,	IN	in_item_id						INT
	,	IN	in_item_count					INT
	,	IN	in_hero_grade					TINYINT
	,	IN	in_total_count					INT
	,	IN	in_prize_count					INT
    )
BEGIN
	
	INSERT INTO gacha_reward_tb 
	(
		`seq`
	,	gacha_id
	,	grade
	,	item_type
	,	item_id
	,	item_count
	,	hero_grade
	,	total_count
	,	prize_count
	)
	VALUES 
	(
		in_seq
	,	in_gacha_id
	,	in_grade
	,	in_item_type
	,	in_item_id
	,	in_item_count
	,	in_hero_grade
	,	in_total_count
	,	in_prize_count
	) ON DUPLICATE KEY UPDATE 
		item_type = in_item_type
	,	item_id = in_item_id
	,	item_count = in_item_count
	,	hero_grade = in_hero_grade
	,	total_count = in_total_count
	,	prize_count = in_prize_count ;

	SELECT 	1;
	
END$$

DELIMITER ;