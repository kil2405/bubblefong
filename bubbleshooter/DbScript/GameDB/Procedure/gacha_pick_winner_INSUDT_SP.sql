DELIMITER $$

USE `bbf_game`$$

DROP PROCEDURE IF EXISTS `gacha_pick_winner_INSUDT_SP`$$

CREATE PROCEDURE `gacha_pick_winner_INSUDT_SP`(
		IN	in_seq					INT
	,	IN	in_gacha_id				INT
    )
BEGIN
	
	DECLARE out_order_index INT DEFAULT -1;
	DECLARE out_grade INT DEFAULT 0;
	 
	-- gacha_list 에서 현재 사용할 order index값을 얻어온다.
	SELECT order_index INTO out_order_index FROM gacha_list WHERE seq = in_seq AND gacha_id = in_gacha_id;
	
	
	-- grade_list에 있는 값중 order_index 번째 값을 저장.
	SELECT 
		JSON_EXTRACT(grade_list, CONCAT('$[', out_order_index, ']')) INTO out_grade 
	FROM 
		gacha_list 
	WHERE 
		seq = in_seq 
	AND 
		gacha_id = in_gacha_id;
	
	
	-- order index 값을 +1 시켜준다 (다음 뽑기 등수의 인덱스)
	UPDATE gacha_list
	SET
		order_index = order_index + 1
	WHERE
		seq = in_seq
	AND
		gacha_id = in_gacha_id ;
	
	
	-- 당첨자 등수에 + 1 카운트 증가를 해 준다.
	UPDATE gacha_reward_tb
	SET
		prize_count = prize_count + 1
	WHERE
		seq = in_seq AND gacha_id = in_gacha_id AND grade = out_grade ;
	
	SELECT out_grade;
	
END$$

DELIMITER ;