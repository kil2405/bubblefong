DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `shop_random_LST_SP`$$

CREATE PROCEDURE `shop_random_LST_SP`(
)
BEGIN
	SELECT 	
		`id`
	,	random_id
	,	item_id
	,	item_type
	,	rate
	,	min_count
	,	max_count
	,	hero_grade
	,	`desc`
	FROM 	
		`shop_random`
	;
	
    END$$

DELIMITER ;