DELIMITER $$

USE `bbf_game`$$

DROP PROCEDURE IF EXISTS `nft_LST_SP`$$

CREATE PROCEDURE `nft_LST_SP`(
	IN in_hash			VARCHAR(256)
)
BEGIN

	SELECT 	
		id
	,	`hash`
	,	secret
	,	`unique`
	,	log_id
	,	is_valid
	FROM 	
		nft_tb
	WHERE 	
		`hash` = in_hash ;
	
    END$$

DELIMITER ;
