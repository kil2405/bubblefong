DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `text_LST_SP`$$

CREATE PROCEDURE `text_LST_SP`(
)
BEGIN
	SELECT 	
		`index`
	,	id
	,	kr
	,	us
	,	`desc`
	FROM 	
		`text`
	;
	
    END$$

DELIMITER ;