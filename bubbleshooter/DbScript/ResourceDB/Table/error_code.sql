USE `bbf_static`;

DROP TABLE IF EXISTS `error_code`;

CREATE TABLE `error_code` (
  `error_code` int(11) COMMENT 'errorCode',
  `action_code` int(11) COMMENT 'actionCode',
  `message_ko` varchar(256) COMMENT '에러메시지_한글',
  `message_en` varchar(256) COMMENT '에러메시지_영문'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;