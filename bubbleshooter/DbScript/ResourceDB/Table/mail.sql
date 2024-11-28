USE `bbf_static`;

DROP TABLE IF EXISTS `mail`;

CREATE TABLE `mail` (
  `index` int(11) NOT NULL,
  `mail_title_kr` varchar(100) NOT NULL COMMENT '우편 제목(KR)',
  `mail_contents_kr` varchar(100) NOT NULL COMMENT '우편 내용(KR)',
  `mail_title_en` varchar(100) NOT NULL COMMENT '우편 제목(EN)',
  `mail_contents_en` varchar(100) NOT NULL COMMENT '우편 내용(EN)',
  `desc` varchar(100) NOT NULL COMMENT '우편 내용(EN)',
  PRIMARY KEY (`index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;