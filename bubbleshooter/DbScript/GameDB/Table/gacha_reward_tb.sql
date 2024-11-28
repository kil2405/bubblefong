USE `bbf_game`;

DROP TABLE IF EXISTS `gacha_reward_tb`;

CREATE TABLE `gacha_reward_tb` (
  `seq` int(11) NOT NULL COMMENT 'index',
  `gacha_id` int(11) NOT NULL COMMENT '가차 ID',
  `grade` TINYINT(4) NOT NULL COMMENT '등수',
  `item_type` int(11) NOT NULL COMMENT '보상 아이템 타입',
  `item_id` int(11) NOT NULL COMMENT '보상 아이템 아이디',
  `item_count` int(11) NOT NULL COMMENT '보상 아이템 개수',
  `hero_grade` TINYINT(4) NOT NULL COMMENT '보상 아이템 등급',
  `total_count` int(11) NOT NULL COMMENT '해당 등수에서 총 당첨자 수',
  `prize_count` int(11) NOT NULL COMMENT '현재까지 당첨자 수',
  `cur_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최근 업데이트 시간',
  `reg_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 시간',
  PRIMARY KEY (`seq`, `gacha_id`, `grade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;