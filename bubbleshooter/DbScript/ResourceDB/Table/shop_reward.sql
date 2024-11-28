USE `bbf_static`;

DROP TABLE IF EXISTS `shop_reward`;

CREATE TABLE `shop_reward` (
  `index` int(11) NOT NULL AUTO_INCREMENT COMMENT '게임 mode',
  `product_id` int(11) NOT NULL COMMENT '게임 mode',
  `item_id` int(11) NOT NULL COMMENT '게임 mode',
  `item_type` int(11) NOT NULL COMMENT '게임 mode',
  `item_count` int(11) NOT NULL COMMENT '개인전 순위(부터)',
  `cur_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최근 업데이트 시간',
  `reg_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 시간',
  PRIMARY KEY (`index`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;