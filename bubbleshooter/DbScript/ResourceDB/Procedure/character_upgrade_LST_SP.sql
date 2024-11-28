DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `character_upgrade_LST_SP`$$

CREATE PROCEDURE `character_upgrade_LST_SP`(
)
BEGIN
	SELECT 	
		`index`
	,	upgrade_level
	,	level_unlock
	FROM 	
		`character_upgrade`
	;
	
    END$$

DELIMITER ;