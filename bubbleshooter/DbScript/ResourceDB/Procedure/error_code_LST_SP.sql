DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `error_code_LST_SP`$$

CREATE PROCEDURE `error_code_LST_SP`(
)
BEGIN
	SELECT 	
		error_code
	,	action_code
	,	message_ko
	,	message_en
	FROM 	
		`error_code`
	;
	
    END$$

DELIMITER ;