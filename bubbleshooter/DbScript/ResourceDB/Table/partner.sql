USE `bbf_static`;

DROP TABLE IF EXISTS `partner`;

CREATE TABLE `partner` (
  `index` int(11) NOT NULL COMMENT '인덱스',
  `item_type` int(11) NOT NULL COMMENT '타입',
  `id` int(11) NOT NULL COMMENT '아이디',
  `spine_name` varchar(50) NOT NULL COMMENT '스파인',
  `icon_inven_name` varchar(50) NOT NULL COMMENT '인벤토리_아이콘',
  `icon_preset_name` varchar(50) NOT NULL COMMENT '프리셋_아이콘',
  `id_text_name` int(11) NOT NULL COMMENT '텍스트ID_이름',
  `id_text_info` int(11) NOT NULL COMMENT '텍스트ID_설명',
  `ingame_position` int(11) NOT NULL COMMENT '게임 화면 위치 (0 : 헤엄 불가능, 1 : 헤엄 가능)',
  `is_nft` tinyint(4) NOT NULL COMMENT 'NFT 여부 확인',
  `sort_number` int(11) NOT NULL COMMENT '오름차순정렬',
  `desc` varchar(50) NOT NULL COMMENT '설명',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;