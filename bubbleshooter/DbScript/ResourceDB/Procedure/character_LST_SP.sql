DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `character_LST_SP`$$

CREATE PROCEDURE `character_LST_SP`(
)
BEGIN
	SELECT 	
		`index`
	,	item_type
	,	`id`
	,	game_spine_name
	,	game_spine_skin
	,	menu_spine_name
	,	icon_inven_name
	,	icon_matching_name
	,	icon_ranking_name
	,	voice_pack_name
	,	voice_pack_name_kr
	,	id_text_name
	,	id_text_intro
	,	id_character_active
	,	is_nft
	,	sort_number
	,	`desc`
	FROM 	
		`character`
	;
	
    END$$

DELIMITER ;