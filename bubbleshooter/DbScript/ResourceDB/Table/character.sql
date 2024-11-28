USE `bbf_static`;

DROP TABLE IF EXISTS `character`;

CREATE TABLE `character` (
  `index` INT(11) NOT NULL COMMENT '타입',
  `item_type` INT(11) NOT NULL COMMENT '타입',
  `id` INT(11) NOT NULL COMMENT '아이디',
  `game_spine_name` VARCHAR(50) NOT NULL COMMENT '인게임스파인',
  `game_spine_skin` VARCHAR(50) NOT NULL COMMENT '스파인스킨',
  `menu_spine_name` VARCHAR(50) NOT NULL COMMENT '메뉴스파인',
  `icon_inven_name` VARCHAR(50) NOT NULL COMMENT '인벤토리_아이콘',
  `icon_matching_name` VARCHAR(50) NOT NULL COMMENT '팀전 매칭 아이콘',
  `icon_ranking_name` VARCHAR(50) NOT NULL COMMENT '랭킹_아이콘',
  `voice_pack_name` VARCHAR(50) NOT NULL COMMENT '보이스팩_이름',
  `voice_pack_name_kr` VARCHAR(50) NOT NULL COMMENT '보이스팩_한국 버전',
  `id_text_name` INT(11) NOT NULL COMMENT '텍스트ID_이름',
  `id_text_intro` INT(11) NOT NULL COMMENT '텍스트ID_소개',
  `id_character_active` INT(11) NOT NULL COMMENT '액티브스킬ID',
  `is_nft` INT(11) NOT NULL COMMENT 'NFT 여부 확인',
  `sort_number` INT(11) NOT NULL COMMENT '오름차순정렬',
  `desc` VARCHAR(50) NOT NULL COMMENT '설명',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;