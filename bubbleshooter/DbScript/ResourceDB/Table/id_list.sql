USE `bbf_static`;

DROP TABLE IF EXISTS `id_list`;

CREATE TABLE `id_list` (
  `idx` int(11) NOT NULL COMMENT '인덱스',
  `id` VARCHAR(25) NOT NULL COMMENT 'nickname',
  `cur_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최근 업데이트 시간',
  `reg_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 시간',
  PRIMARY KEY (`idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;