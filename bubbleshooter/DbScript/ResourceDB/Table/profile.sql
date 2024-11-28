USE `bbf_static`;

DROP TABLE IF EXISTS `profile`;

CREATE TABLE `profile` (
  `index` int(11) NOT NULL,
  `id` int(11) NOT NULL DEFAULT '0',
  `item_type` int(11) NOT NULL DEFAULT '0',
  `id_character` int(11) NOT NULL DEFAULT '0',
  `icon_spine_profile` varchar(64) NOT NULL DEFAULT '',
  `id_text_name` int(11) NOT NULL DEFAULT '0',
  `id_text_intro` integer(11) NOT NULL DEFAULT '0',
  `sort_number` integer(11) NOT NULL DEFAULT '0',
  `start_date` int(11) NOT NULL DEFAULT '0',
  `end_date` int(11) NOT NULL DEFAULT '0',
  `desc` varchar(64) NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;