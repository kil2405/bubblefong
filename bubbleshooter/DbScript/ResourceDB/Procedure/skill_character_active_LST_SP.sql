DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `skill_character_active_LST_SP`$$

CREATE PROCEDURE `skill_character_active_LST_SP`(
)
BEGIN
	SELECT 	
		`index`
	,	id
	,	icon_name
	,	`level`
	,	id_text_skill
	,	id_text_info
	,	id_text_upgrade_value
	,	max_gauge
	,	value1
	,	value2
	,	value3
	,	value4
	,	skill_name
	,	desc1
	,	desc2
	,	desc3
	FROM 	
		`skill_character_active`
	;
	
    END$$

DELIMITER ;