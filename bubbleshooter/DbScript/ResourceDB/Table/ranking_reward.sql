USE `bbf_static`;

DROP TABLE IF EXISTS `ranking_reward`;

CREATE TABLE `ranking_reward` (
  `kor_check` tinyint(4) DEFAULT NULL,
  `ranking_season_id` int(11) DEFAULT NULL,
  `reward_type` int(11) DEFAULT NULL,
  `from_tier` int(11) DEFAULT NULL,
  `to_tier` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `item_type` int(11) DEFAULT NULL,
  `item_count` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;