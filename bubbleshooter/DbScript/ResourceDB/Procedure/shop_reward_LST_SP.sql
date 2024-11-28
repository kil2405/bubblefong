DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `shop_reward_LST_SP`$$

CREATE PROCEDURE `shop_reward_LST_SP`(
)
BEGIN
	SELECT 	
		`index`
	,	product_id
	,	item_id
	,	item_type
	,	item_count
	FROM 	
		`shop_reward`
	;
	
    END$$

DELIMITER ;