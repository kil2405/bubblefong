USE `bbf_static`;

DROP TABLE IF EXISTS `notice`;

CREATE TABLE `notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '공지 ID',
  `title` varchar(128) NOT NULL COMMENT '공지 제목',
  `image_address` varchar(512) NOT NULL COMMENT '배너 이미지 주소',
  `url` varchar(512) NOT NULL COMMENT '배너 터치 링크 주소',
  `start_time` datetime NOT NULL COMMENT '공지 시작시간',
  `end_time` datetime NOT NULL COMMENT '공지 종료시간',
  `sort_number` int(11) DEFAULT '0' COMMENT '정렬순서',
  `is_valid` tinyint(4) NOT NULL DEFAULT '1' COMMENT '삭제여부 0:삭제, 1: 정상',
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일자',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='공지';