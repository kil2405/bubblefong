DELIMITER $$

USE `bbf_game`$$

DROP PROCEDURE IF EXISTS `mail_INSUDT_SP`$$

CREATE PROCEDURE `mail_INSUDT_SP`(
	IN in_id			INTEGER,
	IN in_mail_type			TINYINT,
	IN in_user_id			BIGINT, 
	IN in_mail_id_verify	INTEGER,
	IN in_title 			VARCHAR(128),
	IN in_description 		VARCHAR(128),
	IN in_mail_item_0		VARCHAR(128),
	IN in_mail_item_1		VARCHAR(128),
	IN in_mail_item_2		VARCHAR(128),
	IN in_mail_item_3		VARCHAR(128),
	IN in_mail_item_4		VARCHAR(128),
	IN in_state 			TINYINT, 
	IN in_expired_time 		BIGINT, 
	IN in_open_time 		BIGINT,
	IN in_receive_time 		BIGINT,
	IN in_is_expired 		TINYINT,
	IN in_is_new			TINYINT
    )
BEGIN
	INSERT INTO mail
	(
		mail_type
	,	user_id
	,	mail_id_verify
	,	title
	,	description
	,	mail_item_0
	,	mail_item_1
	,	mail_item_2
	,	mail_item_3
	,	mail_item_4
	,	state
	,	expired_time
	,	open_time
	,	receive_time
	,	is_expired
	,	is_new
	)
	VALUES
	(	
		in_mail_type
	,	in_user_id
	,	in_mail_id_verify
	,	in_title
	,	in_description
	,	in_mail_item_0
	,	in_mail_item_1
	,	in_mail_item_2
	,	in_mail_item_3
	,	in_mail_item_4
	,	in_state
	,	in_expired_time
	,	in_open_time
	,	in_receive_time
	,	in_is_expired
	,	in_is_new
	) ON DUPLICATE KEY UPDATE
		state = in_state
	,	expired_time = in_expired_time
	,	open_time = in_open_time
	,	receive_time = in_receive_time
	,	is_expired = in_is_expired
	,	is_new = in_is_new
		;
	SELECT 1;
    END$$

DELIMITER ;