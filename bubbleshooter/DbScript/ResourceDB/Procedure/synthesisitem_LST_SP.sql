DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `synthesisitem_LST_SP`$$

CREATE PROCEDURE `synthesisitem_LST_SP`(
)
BEGIN
	SELECT 	
		`index`
	,	item_type
	,	item_id
	,	`count`
	,	item_rate
	,	reward_type
	,	reward_item
	,	reward_count
	,	g_rate
	,	g_item_type
	,	g_item
	,	g_count
	,	`desc`
	FROM 	
		`synthesisitem` ;
	
    END$$

DELIMITER ;