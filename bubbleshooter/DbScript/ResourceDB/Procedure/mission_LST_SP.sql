DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `mission_LST_SP`$$

CREATE PROCEDURE `mission_LST_SP`(
)
BEGIN
	SELECT 	
		mission_id
	,	difficulty
	,	mission_title_id
	,	mission_detail
	,	hero_type
	,	hero_id
	,	hero_grade
	,	hero_level
	,	time
	,	b_type_1
	,	basic_reward_1
	,	b_count_1
	,	b_type_2
	,	basic_reward_2
	,	b_count_2
	,	g_type_1
	,	grand_reward_1
	,	g_count_1
	,	g_type_2
	,	grand_reward_2
	,	g_count_2
	,	g_type_3
	,	grand_reward_3
	,	g_count_3
	FROM 	
		`mission`
	;
	
    END$$

DELIMITER ;