USE `bbf_game`;

DROP TABLE IF EXISTS `gacha_list`;

CREATE TABLE `gacha_list` (
  `seq` int(11) NOT NULL AUTO_INCREMENT COMMENT '인덱스',
  `gacha_id` int(11) NOT NULL COMMENT '가차 ID',
  `order_index` int(11) NOT NULL COMMENT '지금까지 뽑힌 인덱스',
  `grade_list` JSON DEFAULT NULL COMMENT '당첨자 리스트',
  `start_time` BIGINT NOT NULL DEFAULT 0 COMMENT '이벤트 시작시간',
  `end_time` BIGINT NOT NULL DEFAULT 0 COMMENT '이벤트 종료시간',
  `valid` TINYINT NOT NULL DEFAULT 0 COMMENT '사용 여부',
  `cur_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최근 업데이트 시간',
  `reg_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 시간',
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;