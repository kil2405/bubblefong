USE `bbf_static`;

DROP TABLE IF EXISTS `event`;

CREATE TABLE `event` (
  `index` int(11) DEFAULT NULL COMMENT 'index',
  `event_no` int(11) NOT NULL COMMENT '아이템 id',
  `ui_type` tinyint(4) NOT NULL COMMENT '아이템 id',
  `event_title` int(11) NOT NULL COMMENT '아이템 이름',
  `start_date` varchar(50) NOT NULL COMMENT '리소스 이름',
  `end_date` int(11) NOT NULL COMMENT 'NFT 가능 여부',
  PRIMARY KEY (`event_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;