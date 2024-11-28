USE `bbf_static`;

DROP TABLE IF EXISTS `gacha_list`;

CREATE TABLE `gacha_list` (
  `index` int(11) NOT NULL COMMENT '인덱스',
  `gacha_id` int(11) NOT NULL COMMENT '가차 ID',
  `grade` int(11) NOT NULL COMMENT '뽑기 등급',
  `count` int(11) NOT NULL COMMENT '당첨 인원 수',
  PRIMARY KEY (`index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;