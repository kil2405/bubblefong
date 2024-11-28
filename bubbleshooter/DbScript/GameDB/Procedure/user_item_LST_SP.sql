DELIMITER $$

USE `bbf_game`$$

DROP PROCEDURE IF EXISTS `user_item_LST_SP`$$

CREATE PROCEDURE `user_item_LST_SP`(
	IN in_user_id			BIGINT
    )
BEGIN
	SELECT 	
		user_id
	,	item_type
	,	item_id
	,	item_count
	FROM 	
		user_item
	WHERE 	
		user_id = in_user_id
	;
	
    END$$

DELIMITER ;