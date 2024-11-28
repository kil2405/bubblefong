USE `bbf_static`;

DROP TABLE IF EXISTS `user_exp`;

CREATE TABLE `user_exp` (
  `index` int(11) DEFAULT NULL COMMENT '인덱스',
  `number` int(11) NOT NULL COMMENT '계정',
  `value` int(11) DEFAULT NULL COMMENT '누적 경험치',
  `need` int(11) DEFAULT NULL COMMENT '레벨 업 시 필요 경험치',
  PRIMARY KEY (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;