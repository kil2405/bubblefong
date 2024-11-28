DELIMITER $$

USE `bbf_game`$$

DROP PROCEDURE IF EXISTS `user_UDT_SP`$$

CREATE PROCEDURE `user_UDT_SP`(
	IN in_id					INTEGER,
	IN in_uuid					VARCHAR(128),
	IN in_nickname				VARCHAR(34), 
	IN in_level					INTEGER,
	IN in_exp					INTEGER,
	IN in_season				INTEGER,
	IN in_rank_point			INTEGER,
	IN in_tier					INTEGER,
	IN in_vip					TINYINT,
	IN in_grade					TINYINT,
	IN in_secret				VARCHAR(128), 
	IN in_encryption			VARCHAR(63),
	IN in_wallet				VARCHAR(255),
	IN in_market				TINYINT,
	IN in_attendance_day		INTEGER,
	IN in_attendance_date		INTEGER,
	IN in_is_guest				TINYINT,
	IN in_language				VARCHAR(8),
	IN in_region				VARCHAR(8),
	IN in_login_time			INTEGER,
	IN in_nick_update			INTEGER
    )
BEGIN

	UPDATE users
	SET
		uuid = in_uuid
	,	nickname = in_nickname
	,	`level` = in_level
	,	`exp` = in_exp
	,	season = in_season
	,	rank_point = in_rank_point
	,	tier = in_tier
	,	vip = in_vip
	,	grade = in_grade
	,	secret = in_secret
	,	encryption = in_encryption
	,	wallet = in_wallet
	,	market = in_market
	,	attendance_day = in_attendance_day
	,	attendance_date = in_attendance_date
	,	is_guest = in_is_guest
	,	language = in_language
	,	region = in_region
	,	login_time = in_login_time
	,	nick_update = in_nick_update
	WHERE
		id = in_id ;
	
	SELECT 1;
	
END $$
	
DELIMITER ;
