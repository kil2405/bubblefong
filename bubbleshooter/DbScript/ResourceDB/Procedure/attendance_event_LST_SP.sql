DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `attendance_event_LST_SP`$$

CREATE PROCEDURE `attendance_event_LST_SP`(
)
BEGIN
	SELECT 	
		`index`
	,	event_no
	,	days
	,	item_id
	,	item_type
	,	item_icon
	,	item_count
	FROM 	
		attendance_event
	;
	
    END$$

DELIMITER ;