USE `bbf_static`;

DROP TABLE IF EXISTS `partner_upgrade`;

CREATE TABLE `partner_upgrade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `upgrade_level` int(11) NOT NULL,
  `level_unlock` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;