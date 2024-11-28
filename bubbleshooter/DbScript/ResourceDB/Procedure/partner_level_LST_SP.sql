DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `partner_level_LST_SP`$$

CREATE PROCEDURE `partner_level_LST_SP`(
)
BEGIN
	SELECT 	
		`index`
	,	`level`
	,	grade
	,	item_id_0
	,	item_type_0
	,	item_count_0
	,	item_id_1
	,	item_type_1
	,	item_count_1
	FROM 	
		`partner_level`
	;
	
    END$$

DELIMITER ;