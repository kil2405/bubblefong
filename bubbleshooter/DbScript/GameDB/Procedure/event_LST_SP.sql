DELIMITER $$

USE `bbf_game`$$

DROP PROCEDURE IF EXISTS `event_LST_SP`$$

CREATE PROCEDURE `event_LST_SP`(
)
BEGIN
	SELECT
		event_id
	,	event_type
	,	event_name
	,	start_time
	,	end_time
	,	item_0
	,	item_1
	,	item_2
	,	item_3
	,	item_4
	,	valid
	FROM 
		event_tb
	;
END$$

DELIMITER ;