USE `bbf_static`;

DROP TABLE IF EXISTS `skill_partner_group`;

CREATE TABLE `skill_partner_group` (
  `index` int(11) DEFAULT NULL COMMENT '인덱스',
  `id` int(11) NOT NULL COMMENT '아이디',
  `id_text_name` int(11) DEFAULT NULL COMMENT '스킬ID',
  `id_text_info` int(11) DEFAULT NULL COMMENT '파트너1 ID',
  `id_character` int(11) DEFAULT NULL COMMENT '파트너2 ID',
  `id_partner_1` int(11) DEFAULT NULL COMMENT '파트너3 ID',
  `id_partner_2` int(11) DEFAULT NULL COMMENT '파트너3 ID',
  `id_partner_3` int(11) DEFAULT NULL COMMENT '파트너3 ID',
  `target_count` int(11) DEFAULT NULL COMMENT '타겟 숫자',
  `value1` int(11) DEFAULT NULL COMMENT '스킬 수치',
  `sort_number` int(11) DEFAULT NULL COMMENT '오름차순 정렬',
  `desc` varchar(255) DEFAULT NULL COMMENT '설명',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;