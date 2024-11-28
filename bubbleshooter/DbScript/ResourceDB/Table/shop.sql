USE `bbf_static`;

DROP TABLE IF EXISTS `shop`;

CREATE TABLE `shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL DEFAULT '0',
  `category` int(11) NOT NULL DEFAULT '0',
  `product_name` int(11) NOT NULL DEFAULT '0',
  `product_icon` varchar(255) NOT NULL DEFAULT 'EMPTY',
  `tag` varchar(255) NOT NULL DEFAULT 'EMPTY',
  `is_ticket` integer(11) NOT NULL DEFAULT '0',
  `ticket_id` integer(11) NOT NULL DEFAULT '0',
  `price_item_type` int(11) NOT NULL DEFAULT '0',
  `price_item_id` int(11) NOT NULL DEFAULT '0',
  `price` int(11) NOT NULL DEFAULT '0',
  `discount_rate` int(11) NOT NULL DEFAULT '0',
  `discount_price` int(11) NOT NULL DEFAULT '0',
  `sell_type` int(11) NOT NULL DEFAULT '0',
  `buy_limit` int(11) NOT NULL DEFAULT '0',
  `sell_start_date` int(11) NOT NULL DEFAULT '-1',
  `sell_end_date` int(11) NOT NULL DEFAULT '-1',
  `gacha_type` varchar(255) NOT NULL DEFAULT 'EMPTY',
  `sort_number` int(11) NOT NULL DEFAULT '0',
  `desc` varchar(128) NOT NULL DEFAULT 'EMPTY',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;