USE `bbf_static`;

DROP TABLE IF EXISTS `character_level`;

CREATE TABLE `character_level` (
  `index` int(11) NOT NULL,
  `is_nft` int(11) NOT NULL DEFAULT '0',
  `level` int(11) NOT NULL DEFAULT '0',
  `grade` int(11) NOT NULL DEFAULT '0',
  `item_id_0` int(11) NOT NULL DEFAULT '0',
  `item_type_0` int(11) NOT NULL DEFAULT '0',
  `item_count_0` int(11) NOT NULL DEFAULT '0',
  `item_id_1` int(11) NOT NULL DEFAULT '0',
  `item_type_1` int(11) NOT NULL DEFAULT '0',
  `item_count_1` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`index`, `level`, `grade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;