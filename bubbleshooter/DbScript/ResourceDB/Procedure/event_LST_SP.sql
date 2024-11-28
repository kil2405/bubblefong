DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `event_LST_SP`$$

CREATE PROCEDURE `event_LST_SP`(
)
BEGIN
	SELECT 	
		`index`
	,	event_no
	,	ui_type
	,	event_title
	,	start_date
	,	end_date
	FROM 	
		`event`
	;
	
    END$$

DELIMITER ;