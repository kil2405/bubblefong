DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `skill_partner_LST_SP`$$

CREATE PROCEDURE `skill_partner_LST_SP`(
)
BEGIN
	SELECT 	
		`index`
	,	id
	,	id_text_info
	,	id_text_summary_info
	,	use_slot
	,	initial_value1
	,	growth_value_per_level1
	,	is_percent1
	,	initial_value2
	,	growth_value_per_level2
	,	is_percent2
	,	initial_value3
	,	growth_value_per_level3
	,	is_percent3
	,	desc1
	,	desc2
	,	desc4
	FROM 	
		`skill_partner`
	;
	
    END$$

DELIMITER ;