USE `bbf_static`;

DROP TABLE IF EXISTS `partner_level`;

CREATE TABLE `partner_level` (
  `index` int(11) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `grade` int(11) DEFAULT NULL,
  `item_id_0` int(11) DEFAULT NULL,
  `item_type_0` int(11) DEFAULT NULL,
  `item_count_0` int(11) DEFAULT NULL,
  `item_id_1` int(11) DEFAULT NULL,
  `item_type_1` int(11) DEFAULT NULL,
  `item_count_1` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;