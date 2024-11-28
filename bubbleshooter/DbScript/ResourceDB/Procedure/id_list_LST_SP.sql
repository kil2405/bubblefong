DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `id_list_LST_SP`$$

CREATE PROCEDURE `id_list_LST_SP`(
)
BEGIN
	SELECT 	
		`idx`
	,	id
	FROM 	
		`id_list`
	;
	
    END$$

DELIMITER ;