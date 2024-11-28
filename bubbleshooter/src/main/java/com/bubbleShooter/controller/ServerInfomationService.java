package com.bubbleShooter.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bubbleShooter.common.ServerConfig;
import com.bubbleShooter.domain.ServerInfomation;
import com.bubbleShooter.util.TimeCalculation;

@Service
public class ServerInfomationService {
	
	@Value("${spring.server.mode}")
	private String serverMode;
	
	public ServerInfomation GetInfo() throws Exception 
	{
		ServerInfomation res = new ServerInfomation();
		
		
		res.setServerId(ServerConfig.SERVER_ID);
		res.setServerIp(ServerConfig.SERVER_IP);
		res.setServerName(ServerConfig.SERVER_NAME);
		res.setServerMode(serverMode);
		res.setServerVendor(ServerConfig.VENDOR);
		res.setServerTime(TimeCalculation.getCurrentUnixTime());
		res.setServerDateTime(TimeCalculation.getUnixTimeToDate(res.getServerTime()));
		res.setBuild_date("2024-07-10-01");
		
		res.setAi_enabled(ServerConfig.AI_ENABLED);
		res.setUse_encryption(ServerConfig.USE_ENCRYPTION);
		res.setServer_inspection(ServerConfig.SERVER_INSPECTION);
		
		return res;
	}
}
