USE `bbf_static`;

DROP TABLE IF EXISTS `text`;

CREATE TABLE `text` (
  `index` int(11) DEFAULT NULL COMMENT '인덱스',
  `id` int(11) NOT NULL COMMENT '텍스트ID',
  `kr` text COMMENT '한국어',
  `us` text COMMENT '영어',
  `desc` varchar(50) DEFAULT NULL COMMENT '설명',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;