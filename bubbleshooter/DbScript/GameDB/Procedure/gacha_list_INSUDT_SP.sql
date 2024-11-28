DELIMITER $$

USE `bbf_game`$$

DROP PROCEDURE IF EXISTS `gacha_list_INSUDT_SP`$$

CREATE PROCEDURE `gacha_list_INSUDT_SP`(
		IN	in_seq										INT
	,	IN	in_gacha_id									INT
	,	IN	in_order_index								INT
	,	IN	in_grade_list								JSON
	,	IN	in_start_time								BIGINT
	,	IN	in_end_time									BIGINT
	,	IN	in_valid									TINYINT
    )
BEGIN
	
	UPDATE gacha_list
	SET
		order_index = in_order_index
	,	grade_list = in_grade_list
	,	start_time = in_start_time
	,	end_time = in_end_time
	,	valid = in_valid
	WHERE
		seq = in_seq ;

	SELECT 	1;
	
END$$

DELIMITER ;