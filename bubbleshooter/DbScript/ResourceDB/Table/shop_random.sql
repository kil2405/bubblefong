USE `bbf_static`;

DROP TABLE IF EXISTS `shop_random`;

CREATE TABLE `shop_random` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `random_id` int(11) NOT NULL DEFAULT '0',
  `item_id` int(11) NOT NULL DEFAULT '0',
  `item_type` int(11) NOT NULL DEFAULT '0',
  `rate` int(11) NOT NULL DEFAULT '0',
  `min_count` int(11) NOT NULL DEFAULT '0',
  `max_count` int(11) NOT NULL DEFAULT '0',
  `hero_grade` tinyint(4) NOT NULL DEFAULT '0',
  `desc` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'EMPTY',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;