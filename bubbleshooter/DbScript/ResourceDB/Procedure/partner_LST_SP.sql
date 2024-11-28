DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `partner_LST_SP`$$

CREATE PROCEDURE `partner_LST_SP`(
)
BEGIN
	SELECT 	
		`index`
	,	item_type
	,	id
	,	spine_name
	,	icon_inven_name
	,	icon_preset_name
	,	id_text_name
	,	id_text_info
	,	ingame_position
	,	is_nft
	,	sort_number
	,	`desc`
	FROM 	
		`partner`
	;
	
    END$$

DELIMITER ;