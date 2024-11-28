DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `character_dia_LST_SP`$$

CREATE PROCEDURE `character_dia_LST_SP`(
)
BEGIN
	SELECT 	
		`index`
	,	enchant_level
	,	r_max
	,	sr_max
	,	ssr_max
	FROM 	
		`character_dia`
	;
	
    END$$

DELIMITER ;