DELIMITER $$

USE `bbf_game`$$

DROP PROCEDURE IF EXISTS `nft_UDT_SP`$$

CREATE PROCEDURE `nft_UDT_SP`(
	IN in_hash					VARCHAR(256),
	IN in_is_valid				TINYINT
    )
BEGIN
	UPDATE nft_tb
	SET
		is_valid = in_is_valid
	WHERE
		`hash` = in_hash ;
	
	SELECT 1;
	
END $$
	
DELIMITER ;
