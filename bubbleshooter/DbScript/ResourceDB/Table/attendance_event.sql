USE `bbf_static`;

DROP TABLE IF EXISTS `attendance_event`;

CREATE TABLE `attendance_event` (
  `index` int(11) NOT NULL AUTO_INCREMENT,
  `event_no` int(11) NOT NULL,
  `days` int(11) DEFAULT NULL COMMENT '보상일차',
  `item_id` int(11) DEFAULT NULL COMMENT '아이템ID',
  `item_type` int(11) DEFAULT NULL COMMENT '아이템타입',
  `item_icon` varchar(50) DEFAULT NULL COMMENT '리소스 이름',
  `item_count` int(11) DEFAULT NULL COMMENT '아이템 개수',
  PRIMARY KEY (`index`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;