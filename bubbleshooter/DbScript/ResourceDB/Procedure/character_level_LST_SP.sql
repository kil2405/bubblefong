DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `character_level_LST_SP`$$

CREATE PROCEDURE `character_level_LST_SP`(
)
BEGIN
	SELECT 	
		`index`
	,	is_nft
	,	`level`
	,	`grade`
	,	item_id_0
	,	item_type_0
	,	item_count_0
	,	item_id_1
	,	item_type_1
	,	item_count_1
	FROM 	
		`character_level`
	;
	
    END$$

DELIMITER ;