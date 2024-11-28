DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `forbidden_words_LST_SP`$$

CREATE PROCEDURE `forbidden_words_LST_SP`(
)
BEGIN
	SELECT 	
		`index`
	,	`language`
	,	`text`
	FROM 	
		`forbidden_words`
	;
	
    END$$

DELIMITER ;