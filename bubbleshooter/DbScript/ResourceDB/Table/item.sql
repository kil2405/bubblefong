USE `bbf_static`;

DROP TABLE IF EXISTS `item`;

CREATE TABLE `item` (
  `item_type` int(11) DEFAULT NULL COMMENT '아이템 타입\r\n1: 골드\r\n2: 붉은 다이아\r\n3: 아이템\r\n4: 캐릭터\r\n5: 파트너\r\n6: 뽑기팩\r\n7: 스킨\r\n8: 이모티콘\r\n9: 트로피',
  `item_id` int(11) NOT NULL COMMENT '아이템 id',
  `item_name` int(11) NOT NULL COMMENT '아이템 이름',
  `item_icon` varchar(50) NOT NULL COMMENT '리소스 이름',
  `is_nft` int(11) NOT NULL COMMENT 'NFT 가능 여부',
  `desc` varchar(50) NOT NULL COMMENT '설명',
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;