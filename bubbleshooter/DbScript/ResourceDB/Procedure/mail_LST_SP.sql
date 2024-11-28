DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `mail_LST_SP`$$

CREATE PROCEDURE `mail_LST_SP`(
)
BEGIN
	SELECT 	
		`index`
	,	mail_title_kr
	,	mail_contents_kr
	,	mail_title_en
	,	mail_contents_en
	,	`desc`
	FROM 	
		`mail`
	;
	
    END$$

DELIMITER ;