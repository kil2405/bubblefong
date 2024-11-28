DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `global_LST_SP`$$

CREATE PROCEDURE `global_LST_SP`(
)
BEGIN
	SELECT 	
		`index`
	,	id
	,	name
	,	`value`
	,	desc1
	,	desc2
	FROM 	
		`global`
	;
	
    END$$

DELIMITER ;