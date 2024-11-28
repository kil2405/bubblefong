USE bbf_game;

DROP TABLE IF EXISTS `nft_tb`;

CREATE TABLE `nft_tb` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '유저의 고유값',
  `hash` VARCHAR(256) NOT NULL COMMENT 'mmd에서 받은 hash값',
  `secret` VARCHAR(24) DEFAULT NULL COMMENT '게임서버에서 생성한 secret값',
  `unique` VARCHAR(24) NOT NULL DEFAULT 0 COMMENT 'mmd에서 받은 unique값',
  `log_id` int(11) NOT NULL DEFAULT 0 COMMENT '로그테이블에 생성된 index값',
  `is_valid` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '0:발행, 1:입금완료처리',
  `cur_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최근 업데이트 시간',
  `reg_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 시간',
  PRIMARY KEY (`id`),
  UNIQUE (`hash`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='NFT 내역'