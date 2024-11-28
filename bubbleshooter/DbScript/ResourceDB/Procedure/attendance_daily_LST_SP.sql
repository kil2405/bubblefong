DELIMITER $$

USE `bbf_static`$$

DROP PROCEDURE IF EXISTS `attendance_daily_LST_SP`$$

CREATE PROCEDURE `attendance_daily_LST_SP`(
)
BEGIN
	SELECT 	
		days
	,	next_index
	,	item_id
	,	item_type
	,	item_icon
	,	item_count
	FROM 	
		attendance_daily
	;
	
    END$$

DELIMITER ;