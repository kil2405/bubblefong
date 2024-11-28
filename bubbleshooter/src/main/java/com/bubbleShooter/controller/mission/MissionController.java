package com.bubbleShooter.controller.mission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bubbleShooter.common.BaseSessionClass;
import com.bubbleShooter.request.ReqMissionReward;
import com.bubbleShooter.request.ReqMissionStart;

@RestController
@RequestMapping("api/")
public class MissionController {
	@Autowired
	private MissionService missionService;
	
	@Autowired
	private BaseSessionClass baseSessionClass;
	
	@RequestMapping(value = "client-secure/mission/info", method = RequestMethod.POST)
	public Object MissionInfo() throws Exception {
		return missionService.MissionInfo(baseSessionClass.getUserId());
	}
	
	@RequestMapping(value = "client-secure/mission/start", method = RequestMethod.POST)
	public Object MissionStart(@RequestBody ReqMissionStart req) throws Exception {
		return missionService.MissionStart(baseSessionClass.getUserId(), req);
	}
	
	@RequestMapping(value = "client-secure/mission/reward", method = RequestMethod.POST)
	public Object MissionReward(@RequestBody ReqMissionReward req) throws Exception {
		return missionService.MissionReward(baseSessionClass.getUserId(), req);
	}
}
