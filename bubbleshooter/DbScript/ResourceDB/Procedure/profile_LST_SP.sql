DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `profile_LST_SP`$$

CREATE PROCEDURE `profile_LST_SP`(
)
BEGIN
	SELECT 	
		`index`
	,	`id`
	,	item_type
	,	id_character
	,	icon_spine_profile
	,	id_text_name
	,	id_text_intro
	,	sort_number
	,	start_date
	,	end_date
	FROM 	
		`profile`
	;
	
    END$$

DELIMITER ;