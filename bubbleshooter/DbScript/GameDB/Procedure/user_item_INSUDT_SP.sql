DELIMITER $$

USE `bbf_game`$$

DROP PROCEDURE IF EXISTS `user_item_INSUDT_SP`$$

CREATE PROCEDURE `user_item_INSUDT_SP`(
		IN	in_user_id									BIGINT
	,	IN	in_item_type								TINYINT
	,	IN	in_item_id									INT
	,	IN	in_item_count								INT
    )
BEGIN
	INSERT INTO user_item (
		user_id
	,	item_type
	,	item_id
	,	item_count
	)
	VALUES (
		in_user_id
	,	in_item_type
	,	in_item_id
	,	in_item_count
	) ON DUPLICATE KEY UPDATE
		item_count = in_item_count ;
	
	SELECT 	1;
    END$$

DELIMITER ;