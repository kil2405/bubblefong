/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.20-log : Database - BS_log_00
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`BS_log_00` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `BS_log_00`;

/*Table structure for table `admin_log_TB` */

DROP TABLE IF EXISTS `admin_log_TB`;

CREATE TABLE `admin_log_TB` (
  `seq` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '번호',
  `ptn_month` tinyint(4) NOT NULL COMMENT '파티셔닝 키',
  `log_time` int(11) NOT NULL,
  `member_id` VARCHAR(20) NOT NULL COMMENT '유저아이디',
  `request_url` varchar(128) DEFAULT NULL COMMENT '요청 주소',
  
  /* 계정 */
  `user_id` bigint(20) NOT NULL COMMENT '유저아이디',
  `uuid` VARCHAR(48) DEFAULT NULL COMMENT '유저 고유 키값',
  `nickname` VARCHAR(25) DEFAULT NULL COMMENT '닉네임',
  `vip` TINYINT(4) NOT NULL DEFAULT 0 COMMENT 'vip 레벨',
  `wallet` VARCHAR(255) DEFAULT NULL COMMENT '메타마스크 지갑 주소',
  
  /* 쿠폰 */
  `coupon_id` BIGINT(20) NOT NULL COMMENT '쿠폰 아이디',
  `coupon` VARCHAR(128) DEFAULT NULL COMMENT '쿠폰 string',
  `limit_count` INTEGER(11) NOT NULL COMMENT '쿠폰 사용 가능 횟수',
  
  /* 이벤트 */
  `event_id` BIGINT(20) NOT NULL COMMENT '이벤트 아이디',
  `name` VARCHAR(128) DEFAULT NULL COMMENT '이벤트 명',
  `event_type` TINYINT(4) NOT NULL COMMENT '이벤트 타입(0: 모든유저, 1: 신규유저, 2: 복귀유저) ',
  
  /* 매치 */
  `season` int(11) NOT NULL DEFAULT '0' COMMENT '진행중인 시즌',
  `before_season` int(11) NOT NULL DEFAULT '0' COMMENT '이전 시즌',
  `season_reward` int(11) NOT NULL DEFAULT '0' COMMENT '보상받은 시즌',
  `season_reward_score` int(11) NOT NULL DEFAULT '0' COMMENT '최종 Score 보상',
  `rank_season_tier` tinyint(4) NOT NULL DEFAULT '0' COMMENT '현재 시즌 티어',
  `rank_season_max_tier` tinyint(4) NOT NULL DEFAULT '0' COMMENT '현재 시즌 최고 티어',
  `rank_season_score` int(11) NOT NULL DEFAULT '0' COMMENT '현재 시즌 스코어',
  `rank_season_max_score` int(11) NOT NULL DEFAULT '0' COMMENT '현재 시즌 최고 스코어',
  `rank_private_season_count` int(11) NOT NULL DEFAULT '0' COMMENT '현재 시즌 101 참여 수',
  `rank_private_season_win_streak` int(11) NOT NULL DEFAULT '0' COMMENT '현재 시즌 101 연승 수',
  `rank_private_season_top_1` int(11) NOT NULL DEFAULT '0' COMMENT '현재 시즌 top1 카운트',
  `rank_private_season_top_3` int(11) NOT NULL DEFAULT '0' COMMENT '현재 시즌 top3 카운트',
  `rank_private_season_top_10` int(11) NOT NULL DEFAULT '0' COMMENT '현재 시즌 top10 카운트',
  `rank_team_season_count` int(11) NOT NULL DEFAULT '0' COMMENT '현재 시즌 팀전 참여 수',
  `rank_team_season_win_count` int(11) NOT NULL DEFAULT '0' COMMENT '현재 시즌 팀전 승리 카운트',
  `rank_team_season_win_streak` int(11) NOT NULL DEFAULT '0' COMMENT '현재 시즌 팀전 연승 카운트',
  `rank_team_season_mvp` int(11) NOT NULL DEFAULT '0' COMMENT '현재 시즌 팀전 mvp 카운트',
  `rank_team_season_mvp_streak` int(11) NOT NULL DEFAULT '0' COMMENT '현재 시즌 팀전 연속 mvp 카운트',
  
  /* 캐릭터 */
  `character_id` INTEGER(11) NOT NULL COMMENT '캐릭터 id',
  `character_enhance` TINYINT(4) NOT NULL COMMENT '캐릭터 레벨',
  `daily_mining` INT(11) NOT NULL COMMENT '현재 BP',
  `is_reward` TINYINT(4) NOT NULL COMMENT '보상 획득 여부 (0:획득안함, 1:획득)',
  
  /* 파트너 */
  `partner_id` INTEGER(11) NOT NULL COMMENT '동료 고유값',
  
  /* 메일 */
  `mail_id` bigint(20) NOT NULL COMMENT '메일 아이디',
  `title` varchar(128) NOT NULL COMMENT '제목',
  `state` tinyint(3) NOT NULL DEFAULT '1' COMMENT '상태 [1:신규, 2:보유중, 3:지급완료]',
  `description` varchar(255) NOT NULL COMMENT '내용',
  `mail_time` int(11) NOT NULL COMMENT '메일 작성 시간',
  `expired_time` bigint(20) NOT NULL COMMENT '만료기간',
  
  /* 공용 (캐릭터 파트너)*/
  `uid` VARCHAR(48) NOT NULL COMMENT '캐릭터 및 파트너 고유값',
  `lock` TINYINT(4) NOT NULL COMMENT 'lock 여부 (0:unlock, 1:lock)',
  `valid` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '사용여부 (0: 삭제, 1: 사용)',
  `level` INTEGER(11) NOT NULL DEFAULT 0 COMMENT '레벨',
  `grade` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '등급',
  
  /* 공용 (쿠폰 이벤트)*/
  `start_time` INTEGER(11) NOT NULL COMMENT '쿠폰 및 이벤트 시작 시간',
  `end_time` INTEGER(11) NOT NULL COMMENT '쿠폰 및 이벤트 끝나는 시간',
  
  /* 공용 (아이템)*/
  `item_type` tinyint(4) NOT NULL COMMENT '쿠폰에 포함된 item_type',
  `item_id` int(11) NOT NULL COMMENT '쿠폰에 포함된 item_id',
  `item_count` int(11) NOT NULL COMMENT '쿠폰에 포함된 item_count',
  `item_grade` tinyint(4) DEFAULT '-1' COMMENT 'grade (캐릭터, 파트너가 아닐경우 -1)',
  `item_level` tinyint(4) DEFAULT '-1' COMMENT 'level (캐릭터, 파트너가 아닐경우 -1)',
  `enhance` tinyint(4) DEFAULT '-1' COMMENT 'enhance` (캐릭터, 파트너가 아닐경우 -1)',
  `skill_1` integer(11) DEFAULT '-1' COMMENT '첫번째 스킬 (캐릭터, 파트너가 아닐경우 -1)',
  `effect_grade_1` tinyint(4) DEFAULT '-1' COMMENT '스킬 등급 (캐릭터, 파트너가 아닐경우 -1)',
  `skill_2` integer(11) DEFAULT '-1' COMMENT '두번째 스킬 (캐릭터, 파트너가 아닐경우 -1)',
  `effect_grade_2` tinyint(4) DEFAULT '-1' COMMENT '스킬 등급 (캐릭터, 파트너가 아닐경우 -1)',
  `skill_3` integer(11) DEFAULT '-1' COMMENT '두번째 스킬 (캐릭터, 파트너가 아닐경우 -1)',
  `effect_grade_3` tinyint(4) DEFAULT '-1' COMMENT '스킬 등급 (캐릭터, 파트너가 아닐경우 -1)',  
  `is_delete` tinyint(4) DEFAULT 0 COMMENT '삭제 여부 (0: 정상, 1: 삭제)',

  `reg_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 시간',
  PRIMARY KEY (`seq`,`ptn_month`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4
/*!50100 PARTITION BY LIST (ptn_month)
(PARTITION p1 VALUES IN (1) ENGINE = InnoDB,
 PARTITION p2 VALUES IN (2) ENGINE = InnoDB,
 PARTITION p3 VALUES IN (3) ENGINE = InnoDB,
 PARTITION p4 VALUES IN (4) ENGINE = InnoDB,
 PARTITION p5 VALUES IN (5) ENGINE = InnoDB,
 PARTITION p6 VALUES IN (6) ENGINE = InnoDB,
 PARTITION p7 VALUES IN (7) ENGINE = InnoDB,
 PARTITION p8 VALUES IN (8) ENGINE = InnoDB,
 PARTITION p9 VALUES IN (9) ENGINE = InnoDB,
 PARTITION p10 VALUES IN (10) ENGINE = InnoDB,
 PARTITION p11 VALUES IN (11) ENGINE = InnoDB,
 PARTITION p12 VALUES IN (12) ENGINE = InnoDB) */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
