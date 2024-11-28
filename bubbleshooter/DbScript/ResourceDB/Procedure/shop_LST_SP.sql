DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `shop_LST_SP`$$

CREATE PROCEDURE `shop_LST_SP`(
)
BEGIN
	SELECT 	
		`id`
	,	product_id
	,	category
	,	product_name
	,	product_icon
	,	tag
	,	is_ticket
	,	ticket_id
	,	price_item_type
	,	price_item_id
	,	price
	,	discount_rate
	,	discount_price
	,	sell_type
	,	buy_limit
	,	sell_start_date
	,	sell_end_date
	,	gacha_type
	,	sort_number
	,	`desc`
	FROM 	
		`shop`
	;
	
    END$$

DELIMITER ;