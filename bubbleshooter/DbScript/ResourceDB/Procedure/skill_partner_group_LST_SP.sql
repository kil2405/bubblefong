DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `skill_partner_group_LST_SP`$$

CREATE PROCEDURE `skill_partner_group_LST_SP`(
)
BEGIN
	SELECT 	
		`index`
	,	id
	,	id_text_name
	,	id_text_info
	,	id_character
	,	id_partner_1
	,	id_partner_2
	,	id_partner_3
	,	target_count
	,	value1
	,	`desc`
	FROM 	
		`skill_partner_group`
	;
	
    END$$

DELIMITER ;