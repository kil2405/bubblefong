package com.bubbleShooter.controller.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bubbleShooter.common.BaseSessionClass;
import com.bubbleShooter.request.ReqContentsAdmission;
import com.bubbleShooter.request.ReqGameResult;
import com.bubbleShooter.request.ReqMatchStart;
import com.bubbleShooter.request.ReqMiniGameResult;
import com.bubbleShooter.request.ReqRoomCreate;
import com.bubbleShooter.request.ReqRoomExpired;

@RestController
@RequestMapping("api/")
public class MatchController {
	@Autowired
	private MatchService matchService;
	
	@Autowired
	private BaseSessionClass baseSessionClass;
	
	@RequestMapping(value = "pvp-server/room-create", method = RequestMethod.POST)
	public Object matchStart(@RequestBody ReqRoomCreate req) throws Exception {
		return matchService.RoomCreate(req);
	}
	
	@RequestMapping(value = "pvp-server/game-start", method = RequestMethod.POST)
	public Object matchStart(@RequestBody ReqMatchStart req) throws Exception {
		return matchService.MatchStart(req);
	}
	
	@RequestMapping(value = "pvp-server/room-expired", method = RequestMethod.POST)
	public Object RoomExpired(@RequestBody ReqRoomExpired req) throws Exception {
		return matchService.RoomExpired(req);
	}
	
	@RequestMapping(value = "pvp-server/user-game-result", method = RequestMethod.POST)
	public Object GameResult(@RequestBody ReqGameResult req) throws Exception {
		return matchService.GameResult(req);
	}
	
	@RequestMapping(value = "pvp-server/minigame/room-create", method = RequestMethod.POST)
	public Object MiniGameRoomCreate(@RequestBody ReqRoomCreate req) throws Exception {
		return matchService.MinigameRoomCreate(req);
	}
	
	@RequestMapping(value = "pvp-server/minigame/game-start", method = RequestMethod.POST)
	public Object MiniGameStart(@RequestBody ReqMatchStart req) throws Exception {
		return matchService.MiniGameStart(req);
	}
	
	@RequestMapping(value = "pvp-server/minigame/room-expired", method = RequestMethod.POST)
	public Object MiniGameRoomExpired(@RequestBody ReqRoomExpired req) throws Exception {
		return matchService.MiniGameRoomExpired(req);
	}
	
	@RequestMapping(value = "pvp-server/minigame/user-game-result", method = RequestMethod.POST)
	public Object MiniGameGameResult(@RequestBody ReqMiniGameResult req) throws Exception {
		return matchService.MiniGameGameResult(req);
	}
	
	@RequestMapping(value = "client-secure/contents/admission", method = RequestMethod.POST)
	public Object ContentsAdmission(@RequestBody ReqContentsAdmission req) throws Exception {
		return matchService.ContentsAdmission(baseSessionClass.getUserId(), req);
	}
}
