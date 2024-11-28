DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `item_LST_SP`$$

CREATE PROCEDURE `item_LST_SP`(
)
BEGIN
	SELECT 	
		item_type
	,	item_id
	,	item_name
	,	item_icon
	,	is_nft
	,	`desc`
	FROM 	
		`item`
	;
	
    END$$

DELIMITER ;