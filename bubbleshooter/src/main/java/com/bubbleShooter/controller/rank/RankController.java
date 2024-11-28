package com.bubbleShooter.controller.rank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bubbleShooter.common.BaseSessionClass;
@RestController
@RequestMapping("api/")
public class RankController {

	@Autowired
	private RankService rankService;
	
	@Autowired
	private BaseSessionClass baseSessionClass;
	
	@RequestMapping(value = "client-secure/ranking/user-list", method = RequestMethod.POST)
	public Object getRankingUserList() throws Exception
	{
		return rankService.getRankingUserList(baseSessionClass.getUserId());
	}
	
	@RequestMapping(value = "client-secure/season/refresh", method = RequestMethod.POST)
	public Object SeasonRefresh() throws Exception
	{
		return rankService.seasonRefresh(baseSessionClass.getUserId());
	}
}
