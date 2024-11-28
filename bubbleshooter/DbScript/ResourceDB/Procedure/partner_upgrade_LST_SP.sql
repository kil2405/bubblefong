DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `partner_upgrade_LST_SP`$$

CREATE PROCEDURE `partner_upgrade_LST_SP`(
)
BEGIN
	SELECT 	
		`id`
	,	upgrade_level
	,	level_unlock
	FROM 	
		`partner_upgrade`
	;
	
    END$$

DELIMITER ;