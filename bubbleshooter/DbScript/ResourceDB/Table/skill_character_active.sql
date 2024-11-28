USE `bbf_static`;

DROP TABLE IF EXISTS `skill_character_active`;

CREATE TABLE `skill_character_active` (
  `index` int(11) DEFAULT NULL COMMENT '인덱스',
  `id` int(11) NOT NULL COMMENT '아이디',
  `icon_name` varchar(50) DEFAULT NULL COMMENT '아이콘',
  `level` int(11) NOT NULL COMMENT '레벨',
  `id_text_skill` int(11) DEFAULT NULL COMMENT '텍스트ID_스킬',
  `id_text_info` int(11) DEFAULT NULL COMMENT '텍스트ID_설명',
  `id_text_upgrade_value` int(11) DEFAULT NULL COMMENT '업그레이드 되는 텍스트 ID',
  `max_gauge` float DEFAULT NULL COMMENT '필요게이지',
  `value1` float DEFAULT NULL COMMENT '값 = A',
  `value2` float DEFAULT NULL COMMENT '값 = B',
  `value3` float DEFAULT NULL COMMENT '값 = C',
  `value4` float DEFAULT NULL COMMENT '값 = D',
  `skill_name` varchar(50) DEFAULT NULL COMMENT '스킬이름 NFT METAFILE 영역',
  `desc1` varchar(256) DEFAULT NULL COMMENT '설명(영문) NFT METAFILE 영역',
  `desc2` varchar(128) DEFAULT NULL COMMENT '설명(국문)',
  `desc3` varchar(50) DEFAULT NULL COMMENT 'VALIE 설명',
  PRIMARY KEY (`id`,`level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;