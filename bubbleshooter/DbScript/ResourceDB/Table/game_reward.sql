USE `bbf_static`;

DROP TABLE IF EXISTS `game_reward`;

CREATE TABLE `game_reward` (
  `index` int(11) NOT NULL AUTO_INCREMENT COMMENT '게임 mode',
  `game_mode` tinyint(4) NOT NULL COMMENT '게임 mode',
  `rank_from` tinyint(4) NOT NULL COMMENT '개인전 순위(부터)',
  `rank_to` tinyint(4) NOT NULL COMMENT '개인전 순위(까지)',
  `item_id_0` int(11) NOT NULL COMMENT '아이템 ID',
  `item_type_0` tinyint(4) NOT NULL COMMENT '아이템 타입 1 : 골드 2 : 붉은 다이아 3 : 아이템 4 : 캐릭터 5 : 파트너 6 : 뽑기팩 7 : 스킨 8 : 이모티콘',
  `item_count_0` int(11) NOT NULL COMMENT '아이템 개수',
  `cur_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최근 업데이트 시간',
  `reg_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 시간',
  PRIMARY KEY (`index`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;