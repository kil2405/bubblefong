package com.bubbleShooter.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bubbleShooter.common.ServerConfig;

@RestController
@RequestMapping("/service/init/")
public class InitController {
	@Autowired
	private initService initService;
	
	@Value("${server.config.gamedb_shard_count}")
	private int gamedb_shard_count;

	@Value("${server.config.redis_shard_count}")
	private int redis_shard_count;

	@PostConstruct
	public void initQry() {
		/////////////////////////////////////////////////////////////////////////////////
		// 게임디비, 레디스의 샤딩 카운트 값을 세팅한다. ** 매우 중요 **
//		storageContext.setGameDBCount(gamedb_shard_count);
//		storageContext.setRedisDBCount(redis_shard_count);
//		if (storageContext.getGameDBCount() <= 0) {
//			System.out.println("========== GameDB Shard Count Error ==========");
//			System.exit(0);
//		}
//
//		if (storageContext.getRedisDBCount() <= 0) {
//			System.out.println("========== RedisDB Shard Count Error ==========");
//			System.exit(0);
//		}
//		/////////////////////////////////////////////////////////////////////////////////
//
//		int dbConnectionCheckValue = storageContext.checkDBConnection();
//
//		if (dbConnectionCheckValue != -1) {
//			System.out.println("dbConnectionCheckValue:" + dbConnectionCheckValue);
//			System.exit(0);
//		}
	}

	@Value("${spring.server.mode}")
	private String serverMode;

	@Value("${server.status.close}")
	private boolean server_status_close;

	@Value("${server.status.notice}")
	private String server_status_notice;

	@PostConstruct
	public void initController() throws Exception {
		initService.initServerMode(serverMode);
		if (initService.serverStatusClose(server_status_close, server_status_notice))
			return;
		
		if(serverMode.equals("live"))
		{
			ServerConfig.USE_ENCRYPTION = true;
		}
		System.out.println("USE_ENCRYPTION : " + ServerConfig.USE_ENCRYPTION);

		initService.serverInfoCheck();
		initService.initResource();
	}
}
