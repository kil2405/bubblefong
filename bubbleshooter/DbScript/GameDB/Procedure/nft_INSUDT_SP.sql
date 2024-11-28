DELIMITER $$

USE `bbf_game`$$

DROP PROCEDURE IF EXISTS `nft_INSUDT_SP`$$

CREATE PROCEDURE `nft_INSUDT_SP`(
	IN in_hash					VARCHAR(256),
	IN in_secret				VARCHAR(24),
	IN in_unique				VARCHAR(24),
	IN in_log_id				INTEGER,
	IN in_is_valid				TINYINT
    )
BEGIN
	INSERT INTO nft_tb 
	(
		`hash`
	,	secret
	,	`unique`
	,	log_id
	,	is_valid
	)
	VALUES 
	(
		in_hash
	,	in_secret
	,	in_unique
	,	in_log_id
	,	in_is_valid
	) ;
	
	SELECT 1;
	
END $$
	
DELIMITER ;
