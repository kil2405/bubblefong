DELIMITER $$

USE `bbf_game`$$

DROP PROCEDURE IF EXISTS `mail_UDT_SP`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `mail_UDT_SP`(
	IN in_id			INT,
	IN in_user_id			INT, 
	IN in_state 			INT, 
	IN in_expired_time 		BIGINT, 
	IN in_open_time 		BIGINT,
	IN in_receive_time 		BIGINT,
	IN in_is_expired		TINYINT,
	IN in_is_new			TINYINT
)
BEGIN
	UPDATE  mail
	SET
		state = in_state
	,	expired_time = in_expired_time
	,	open_time = in_open_time
	,	receive_time = in_receive_time
	,	is_expired = in_is_expired
	,	is_new = in_is_new
	WHERE 
		id = in_id
		AND
		user_id = in_user_id;
		
	SELECT 1;
    END$$

DELIMITER ;