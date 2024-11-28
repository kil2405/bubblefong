DELIMITER $$

USE `bbf_game`$$

DROP PROCEDURE IF EXISTS `user_bot_INS_SP`$$

CREATE PROCEDURE `user_bot_INS_SP`(
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
	INSERT INTO users 
	(
		id
	,	uuid
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
	)
	VALUES 
	(
		in_id
	,	in_uuid
	,	in_nickname
	,	in_level
	,	in_exp
	,	in_season
	,	in_rank_point
	,	in_tier
	,	in_vip
	,	in_grade
	,	in_secret
	,	in_encryption
	,	in_wallet
	,	in_market
	,	in_attendance_day
	,	in_attendance_date
	,	in_is_guest
	,	in_language
	,	in_region
	, 	in_login_time
	,	in_nick_update
	);
	
	SELECT 1;
	
END $$
	
DELIMITER ;
