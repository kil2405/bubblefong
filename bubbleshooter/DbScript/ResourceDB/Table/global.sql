USE `bbf_static`;

DROP TABLE IF EXISTS `global`;

CREATE TABLE `global` (
  `index` int(11) COMMENT '인덱스',
  `id` int(11) COMMENT '아이템 타입\r\n1: 골드\r\n2: 붉은 다이아\r\n3: 아이템\r\n4: 캐릭터\r\n5: 파트너\r\n6: 뽑기팩\r\n7: 스킨\r\n8: 이모티콘\r\n9: 트로피',
  `name` varchar(255) COMMENT '아이템 id',
  `value` int(11) COMMENT '아이템 이름',
  `desc1` varchar(255) COMMENT '리소스 이름',
  `desc2` varchar(255) COMMENT 'NFT 가능 여부',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;