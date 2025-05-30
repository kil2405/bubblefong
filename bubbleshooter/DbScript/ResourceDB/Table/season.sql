USE `bbf_static`;

DROP TABLE IF EXISTS `season`;

CREATE TABLE `season` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '시즌 ID',
  `season_num` int(11) NOT NULL DEFAULT '1',
  `title` varchar(100) NOT NULL DEFAULT 'EMPTY' COMMENT '시즌 제목',
  `start_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '시즌 시작일',
  `end_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '시즌 종료일',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;