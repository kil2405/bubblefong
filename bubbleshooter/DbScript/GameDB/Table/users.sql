USE bbf_game;

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '유저의 고유값',
  `uuid` VARCHAR(128) NOT NULL COMMENT '노출되는 유저 고유값',
  `nickname` VARCHAR(25) DEFAULT NULL COMMENT '닉네임',
  `level` INTEGER(11) NOT NULL DEFAULT 0 COMMENT '계정레벨',
  `exp` INTEGER(11) NOT NULL DEFAULT 0 COMMENT '계정 경험치',
  `season` INTEGER(11) NOT NULL DEFAULT 0 COMMENT '진행중인 시즌',
  `rank_point` INTEGER(11) NOT NULL DEFAULT 0 COMMENT '랭킹 포인트',
  `tier` INTEGER(11) NOT NULL DEFAULT 0 COMMENT '랭킹 티어',
  `vip` TINYINT(4) NOT NULL DEFAULT 0 COMMENT 'vip 레벨',
  `grade` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '유저등급',
  `secret` VARCHAR(128) DEFAULT NULL COMMENT '유저 고유 비밀키값',
  `encryption` VARCHAR(63) DEFAULT NULL COMMENT '암호화 키값',
  `wallet` VARCHAR(255) DEFAULT NULL COMMENT '메타마스크 지갑 주소',
  `market` tinyint(4) NOT NULL DEFAULT '0' COMMENT '마켓 구분',
  `attendance_day` INTEGER(11) NOT NULL DEFAULT 0 COMMENT '출석일',
  `attendance_date` INTEGER(11) NOT NULL DEFAULT 0 COMMENT '출석날짜',
  `is_guest` tinyint(4) NOT NULL DEFAULT 1 COMMENT '게스트 로그인 여부',
  `language` VARCHAR(8) DEFAULT NULL COMMENT '사용 언어',
  `region` VARCHAR(8) DEFAULT NULL COMMENT '접속 지역',
  `login_time` int(11) NOT NULL COMMENT '로그인 시각',
  `nick_update` int(11) NOT NULL COMMENT '닉네임 변경시간',
  `cur_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최근 업데이트 시간',
  `reg_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 시간',
  PRIMARY KEY (`id`),
  UNIQUE (`uuid`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='유저의 게임 정보'