package com.bubbleShooter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bubbleShooter.common.StorageContext;

@RestController
@RequestMapping("api/")
public class ServerInfomationController {
	
	@Autowired
	private ServerInfomationService severInfomationService;
	
	@Autowired
	private StorageContext storageContext;
	
	@RequestMapping(value = "client/web/version", method = RequestMethod.GET)
	public Object ServerInfo() throws Exception {
		return severInfomationService.GetInfo();
	}
	
	@RequestMapping(value = "client/web/server/check", method = RequestMethod.GET)
	public Object ServerCheck() throws Exception {
		return storageContext.CheckDBConnection();
	}
}
