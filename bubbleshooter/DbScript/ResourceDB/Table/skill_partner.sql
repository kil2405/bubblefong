USE `bbf_static`;

DROP TABLE IF EXISTS `skill_partner`;

CREATE TABLE `skill_partner` (
  `index` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `id_text_info` int(11) NOT NULL,
  `id_text_summary_info` int(11) NOT NULL,
  `use_slot` int(11) NOT NULL,
  `initial_value1` int(11) NOT NULL,
  `growth_value_per_level1` FLOAT(2) NOT NULL,
  `is_percent1` int(11) NOT NULL,
  `initial_value2` int(11) NOT NULL,
  `growth_value_per_level2` FLOAT(2) NOT NULL,
  `is_percent2` int(11) NOT NULL,
  `initial_value3` int(11) NOT NULL,
  `growth_value_per_level3` FLOAT(2) NOT NULL,
  `is_percent3` int(11) NOT NULL,
  `desc1` varchar(256) DEFAULT NULL,
  `desc2` varchar(256) DEFAULT NULL,
  `desc4` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;