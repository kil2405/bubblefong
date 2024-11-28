USE `bbf_static`;

DROP TABLE IF EXISTS `character_upgrade`;

CREATE TABLE `character_upgrade` (
  `index` int(11) NOT NULL AUTO_INCREMENT,
  `upgrade_level` int(11) NOT NULL,
  `level_unlock` int(11) NOT NULL,
  PRIMARY KEY (`index`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;