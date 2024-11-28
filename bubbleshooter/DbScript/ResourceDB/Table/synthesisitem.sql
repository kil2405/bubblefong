USE `bbf_static`;

DROP TABLE IF EXISTS `synthesisitem`;

CREATE TABLE `synthesisitem` (
  `index` int(11) NOT NULL COMMENT '인덱스',
  `item_type` tinyint(4) NOT NULL COMMENT '아이템타입',
  `item_id` int(11) NOT NULL COMMENT 'item_id',
  `count` int(11) NOT NULL COMMENT '아이템 개수',
  `item_rate` int(11) NOT NULL COMMENT '확률',
  `reward_type` tinyint(4) NOT NULL COMMENT '보상 타입',
  `reward_item` int(11) NOT NULL COMMENT '보상 아이템 id',
  `reward_count` int(11) NOT NULL COMMENT '보상 아이템 개수',
  `g_rate` int(11) NOT NULL COMMENT '대박 확률',
  `g_item_type` int(11) NOT NULL COMMENT '대박 아이템 타입',
  `g_item` int(11) NOT NULL COMMENT '대박 아이템 아이디',
  `g_count` int(11) NOT NULL COMMENT '대박 아이템 개수',
  `desc` varchar(50) DEFAULT NULL COMMENT '설명',
  PRIMARY KEY (`index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;