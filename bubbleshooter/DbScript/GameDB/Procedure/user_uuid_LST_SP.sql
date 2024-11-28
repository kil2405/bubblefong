DELIMITER $$

USE `bbf_game`$$

DROP PROCEDURE IF EXISTS `user_uuid_LST_SP`$$

CREATE PROCEDURE `user_uuid_LST_SP`(
	IN in_uuid			VARCHAR(128)
)
BEGIN

	SELECT 	
		id
	,	`uuid`
	,	nickname
	,	`level`
	,	`exp`
	,	season
	,	rank_point
	,	tier
	,	vip
	,	grade
	,	secret
	,	encryption
	,	wallet
	,	market
	,	attendance_day
	,	attendance_date
	,	is_guest
	,	language
	,	region
	,	login_time
	,	nick_update
	,	UNIX_TIMESTAMP(reg_date) as reg_date
	FROM 	
		users
	WHERE 	
		`uuid` = in_uuid
	;
	
    END$$

DELIMITER ;
