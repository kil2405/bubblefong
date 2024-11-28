DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `user_exp_LST_SP`$$

CREATE PROCEDURE `user_exp_LST_SP`(
)
BEGIN
	SELECT 	
		`index`
	,	number
	,	value
	,	need
	FROM 	
		`user_exp`
	;
	
    END$$

DELIMITER ;