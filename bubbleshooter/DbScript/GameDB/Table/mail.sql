DROP TABLE IF EXISTS `mail`;

CREATE TABLE `mail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '메일 id',
  `mail_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '메일타입 [0: 일반메일, 1: NFT메일]',
  `user_id` int(11) NOT NULL COMMENT '유저의 id',
  `mail_id_verify` int(11) NOT NULL DEFAULT '0',
  `title` varchar(255) NOT NULL DEFAULT 'EMPTY' COMMENT '제목',
  `description` text COMMENT '내용',
  `mail_item_0` varchar(255) NOT NULL DEFAULT '',
  `mail_item_1` varchar(255) NOT NULL DEFAULT '',
  `mail_item_2` varchar(255) NOT NULL DEFAULT '',
  `mail_item_3` varchar(255) NOT NULL DEFAULT '',
  `mail_item_4` varchar(255) NOT NULL DEFAULT '',
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '상태 [1:신규, 2:보유중, 3:지급완료]',
  `expired_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '만료기간',
  `open_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '확인시간',
  `receive_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '받은시간',
  `is_expired` tinyint(4) NOT NULL DEFAULT '0' COMMENT '삭제 여부',
  `is_new` tinyint(4) NOT NULL DEFAULT '1' COMMENT '신규 우편 여부',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최근 업데이트 시간',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 시간',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
