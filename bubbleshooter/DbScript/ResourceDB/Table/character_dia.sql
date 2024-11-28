USE `bbf_static`;

DROP TABLE IF EXISTS `character_dia`;

CREATE TABLE `character_dia` (
  `index` int(11) NOT NULL,
  `enchant_level` int(11) NOT NULL DEFAULT '0',
  `r_max` int(11) NOT NULL DEFAULT '0',
  `sr_max` int(11) NOT NULL DEFAULT '0',
  `ssr_max` int(11) NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;