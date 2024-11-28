USE `bbf_static`;

DROP TABLE IF EXISTS `gacha_reward`;

CREATE TABLE `gacha_reward` (
  `index` int(11) NOT NULL COMMENT '인덱스',
  `grade` int(11) NOT NULL COMMENT '뽑기 등급',
  `item_type` int(11) NOT NULL COMMENT '보상 아이템 타입',
  `item_id` int(11) NOT NULL COMMENT '보상 아이템 아이디',
  `item_count` int(11) NOT NULL COMMENT '보상 아이템 개수',
  `hero_grade` tinyint(4) NOT NULL COMMENT '영웅 등급',
  PRIMARY KEY (`index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;