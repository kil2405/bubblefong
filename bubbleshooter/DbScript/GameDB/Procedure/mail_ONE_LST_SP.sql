DELIMITER $$

USE `bbf_game`$$

DROP PROCEDURE IF EXISTS `mail_ONE_LST_SP`$$

CREATE DEFINER=`root`@`%` PROCEDURE `mail_ONE_LST_SP`(	
	IN in_id			INT
    )
BEGIN
	SELECT 	
		id
	,	mail_type
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
	,	UNIX_TIMESTAMP(created) as created
	FROM 	mail
	WHERE 	id = in_id;
    END$$

DELIMITER ;