DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `gacha_list_LST_SP`$$

CREATE PROCEDURE `gacha_list_LST_SP`(
)
BEGIN
	SELECT 	
		`index`
	,	gacha_id
	,	grade
	,	`count`
	FROM 	
		`gacha_list`
	;
	
    END$$

DELIMITER ;