USE `bbf_static`;

DROP TABLE IF EXISTS `ranking_season`;

CREATE TABLE `ranking_season` (
  `index` int(11) NOT NULL NOT NULL DEFAULT '0',
  `ranking_season_id` int(11) NOT NULL DEFAULT '0',
  `previous_season` int(11) NOT NULL DEFAULT '0',
  `season_start` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `season_end` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `season_desc` text COLLATE utf8_unicode_ci,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`index`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;