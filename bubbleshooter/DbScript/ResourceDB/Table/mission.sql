USE `bbf_static`;

DROP TABLE IF EXISTS `mission`;

CREATE TABLE `mission` (
  `mission_id` INT(11) NOT NULL COMMENT '미션 ID',
  `difficulty` INT(11) NOT NULL COMMENT '난이도',
  `mission_title_id` INT(11) NOT NULL COMMENT '미션 제목',
  `mission_detail` INT(11) NOT NULL COMMENT '미션 내용',
  `hero_type` TINYINT(4) NOT NULL COMMENT '영웅 타입',
  `hero_id` INT(11) NOT NULL COMMENT '캐릭터 ID',
  `hero_grade` INT(11) NOT NULL COMMENT '캐릭터등급',
  `hero_level` INT(11) NOT NULL COMMENT '캐릭터레벨',
  `time` INT(11) NOT NULL COMMENT '미션 시간(초)',
  `b_type_1` INT(11) NOT NULL COMMENT '기본보상1',
  `basic_reward_1` INT(11) NOT NULL COMMENT '기본보상1',
  `b_count_1` INT(11) NOT NULL COMMENT '기본보상1 개수',
  `b_type_2` INT(11) NOT NULL COMMENT '기본보상2',
  `basic_reward_2` INT(11) NOT NULL COMMENT '기본보상2',
  `b_count_2` INT(11) NOT NULL COMMENT '기본보상2 개수',
  `g_type_1` INT(11) NOT NULL COMMENT '대박보상1',
  `grand_reward_1` INT(11) NOT NULL COMMENT '대박보상1',
  `g_count_1` INT(11) NOT NULL COMMENT '대박보상1 개수',
  `g_type_2` INT(11) NOT NULL COMMENT '대박보상2',
  `grand_reward_2` INT(11) NOT NULL COMMENT '대박보상2',
  `g_count_2` INT(11) NOT NULL COMMENT '대박보상2 개수',
  `g_type_3` INT(11) NOT NULL COMMENT '대박보상3',
  `grand_reward_3` INT(11) NOT NULL COMMENT '대박보상3',
  `g_count_3` INT(11) NOT NULL COMMENT '대박보상3 개수',
  `reg_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 시간',
  PRIMARY KEY (`mission_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;