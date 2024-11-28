USE `bbf_static`;

DROP TABLE IF EXISTS `attendance_daily`;

CREATE TABLE `attendance_daily` (
  `days` int(11) NOT NULL COMMENT '날짜',
  `next_index` int(11) NOT NULL COMMENT '다음날짜',
  `item_id` int(11) NOT NULL COMMENT '아이템 id',
  `item_type` int(11) NOT NULL COMMENT '아이템 타입',
  `item_icon` varchar(50) NOT NULL COMMENT '리소스 이름',
  `item_count` int(11) NOT NULL COMMENT '아이템 개수',
  PRIMARY KEY (`days`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
