USE `bbf_static`;

DROP TABLE IF EXISTS `forbidden_words`;

CREATE TABLE `forbidden_words` (
  `index` int(11) NOT NULL COMMENT 'index',
  `language` VARCHAR(8) NOT NULL COMMENT '언어',
  `text` VARCHAR(48) COMMENT '금칙어목록',
  PRIMARY KEY (`index`)
) ENGINE=InnoDB CHARSET=utf8mb4;