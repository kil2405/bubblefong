USE `bbf_static`;

DROP TABLE IF EXISTS `ranking_tier`;

CREATE TABLE `ranking_tier` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `ranking_season_id` int(11) NOT NULL COMMENT '랭킹 시즌 id \r\n0: 시즌 정보 없을 때',
  `ranking_tier` int(11) NOT NULL DEFAULT '0' COMMENT '랭크 티어\r\n0: 브론즈3 \r\n1: 브론즈2\r\n2: 브론즈1\r\n3: 실버3\r\n4: 실버2\r\n5: 실버1\r\n6: 골드3\r\n7: 골드2\r\n8: 골드1\r\n9:다이아몬드3\r\n10: 다이아몬드2\r\n11: 다이아몬드1',
  `tier_name` int(11) NOT NULL COMMENT '티어 이름',
  `tier_icon` varchar(255) NOT NULL COMMENT '티어 리소스 이름',
  `season_reward_spine` varchar(255) NOT NULL COMMENT '티어 보상 연출',
  `regulate_tier` int(11) NOT NULL COMMENT '새 시즌 시작 시 조정 티어',
  `ranking_tier_standard_point` int(11) NOT NULL COMMENT '해당 티어 기준 점수',
  `ranking_max_rp` int(11) NOT NULL COMMENT '101 모드 최대RP',
  `ranking_min_rp` int(11) NOT NULL COMMENT '101 모드 최소RP',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;