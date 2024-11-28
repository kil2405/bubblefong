package com.bubbleShooter.controller.match;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bubbleShooter.common.BubbleException;
import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.ErrorCodeInfo;
import com.bubbleShooter.common.GameResource;
import com.bubbleShooter.common.RepositoryService;
import com.bubbleShooter.controller.rank.RankService;
import com.bubbleShooter.domain.Character;
import com.bubbleShooter.domain.ContentsAdminssion;
import com.bubbleShooter.domain.MiniGameRoom;
import com.bubbleShooter.domain.MinigamePlayLog;
import com.bubbleShooter.domain.Preset;
import com.bubbleShooter.domain.Rank;
import com.bubbleShooter.domain.Room;
import com.bubbleShooter.domain.User;
import com.bubbleShooter.domain.UserItem;
import com.bubbleShooter.request.ReqContentsAdmission;
import com.bubbleShooter.request.ReqGameResult;
import com.bubbleShooter.request.ReqMatchStart;
import com.bubbleShooter.request.ReqMiniGameResult;
import com.bubbleShooter.request.ReqRoomCreate;
import com.bubbleShooter.request.ReqRoomExpired;
import com.bubbleShooter.resource.GameRewardResource;
import com.bubbleShooter.resource.RankingTierResource;
import com.bubbleShooter.resource.UserExpResource;
import com.bubbleShooter.response.BaseResponse;
import com.bubbleShooter.response.ResContentsAdmission;
import com.bubbleShooter.response.ResGameResult;
import com.bubbleShooter.response.ResMiniGameResult;
import com.bubbleShooter.response.ResRoomCreate;
import com.bubbleShooter.util.FindData;
import com.bubbleShooter.util.TimeCalculation;

@Service
public class MatchService {
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private FindData findData;
	
	@Autowired
	private GameResource gameResource;

	@Autowired
	private RankService rankService;
	
	
	public ResRoomCreate RoomCreate(ReqRoomCreate req) throws Exception
	{
		ResRoomCreate res = new ResRoomCreate();

		String uuid = "";
		Room room = null;

		while (true)
		{
			uuid = UUID.randomUUID().toString().replace("-", "");

			room = repositoryService.getRoom(uuid, false);
			if(room != null)
				continue;
			
			break;
		}

		room = new Room();
		room.setUuid(uuid);
		room.setCreatedAt(TimeCalculation.getCurrentUnixTime());
		room.setGameStartedAt(ConstantVal.DEFAULT_ZERO);
		room.setUsers("");
		room.setExpriedAt(ConstantVal.DEFAULT_ZERO);
		
		repositoryService.setRoom(uuid, room);
		
		res.uuid = uuid;
		res.result = ConstantVal.DEFAULT_SUCCESS;

		return res;
	}

	public BaseResponse MatchStart(ReqMatchStart req) throws Exception
	{
		BaseResponse res = new BaseResponse();
		
		Room room = repositoryService.getRoom(req.uuid, false);
		if(room == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1723);
		
		// 룸이 만료되었거나, 이미 시작되어 있다면, 에러!!
		if(room.getGameStartedAt() > ConstantVal.DEFAULT_ZERO || room.getExpriedAt() > ConstantVal.DEFAULT_ZERO)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1724);
		
		StringBuilder sb = new StringBuilder();
		for(Integer userId : req.userList)
		{
			sb.append(userId).append(",");
		}
		
		sb.deleteCharAt(sb.length() - 1);
		
		room.setGameStartedAt(TimeCalculation.getCurrentUnixTime());
		room.setUsers(sb.toString());
		
		repositoryService.setRoom(req.uuid, room);
		
		res.result = ConstantVal.DEFAULT_SUCCESS;
		return res;
	}
	
	
	public BaseResponse RoomExpired(ReqRoomExpired req) throws Exception
	{
		BaseResponse res = new BaseResponse();
		
		Room room = repositoryService.getRoom(req.uuid, false);
		if(room == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1725);
		
		//이미 종료된 방입니다.
		if(room.getExpriedAt() > ConstantVal.DEFAULT_ZERO)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1726);
		
		room.setExpriedAt(TimeCalculation.getCurrentUnixTime());
		
		repositoryService.setRoom(req.uuid, room);
		
		res.result = ConstantVal.DEFAULT_SUCCESS;
		return res;
	}
	
	public ResGameResult GameResult(ReqGameResult req) throws Exception
	{
		ResGameResult res = new ResGameResult();
		
		if(req.userId <= ConstantVal.DEFAULT_ZERO)
		{
			res.result = ConstantVal.DEFAULT_SUCCESS;
			return res;
		}
		
		User user = repositoryService.getUser(req.userId, false);
		if(user == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1727);
		
		List<Character> characters = repositoryService.getCharacters(user.getId(), false);
		if(characters == null || characters.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1728);
		
		int index = findData.findCharacterIndex(characters, req.characterUid);
		if(index <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1729);
		
		List<UserItem> userItems = repositoryService.getUserItems(user.getId(), false);
		if(userItems == null || userItems.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1733);
		
		int itemIndex = findData.findUserItemIndex(ConstantVal.ITEM_TYPE_GOLD, 0, userItems);
		if(itemIndex <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1734);
		
		int diaIndex = findData.findUserItemIndex(ConstantVal.ITEM_TYPE_RUBY, 1, userItems);
		if(diaIndex <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1704);
		
		int blueDiaIndex = findData.findUserItemIndex(ConstantVal.ITEM_TYPE_DIAMOND, 2, userItems);
		if(blueDiaIndex <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1743);
		
		Room room = repositoryService.getRoom(req.uuid, false);
		if(room == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1735);
		
		if(room.getExpriedAt() > 0)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1705);
		
		if(room.getUsers().isEmpty())
		{
			res.result = ConstantVal.DEFAULT_SUCCESS;
			return res;
		}
		
		String[] userArry = room.getUsers().split(",");
		boolean isRoomUser = false;
		for(String id : userArry)
		{
			if(user.getId() == Integer.valueOf(id))
			{
				isRoomUser = true;
				break;
			}
		}
		
		//해당 룸에서 플레이를 하지 않았던 유저이다.
		if(!isRoomUser)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1736);
		
		//루아(None NFT)캐릭터는 다이아 보상을 받을 수 없다.
		if(characters.get(index).getIsNft() < ConstantVal.IS_TRUE)
		{
			res.dia = 0;
			req.diaAmount = 0;
		}
		
		if(req.userId < 100000)
		{
			res.dia = 0;
			req.diaAmount = 0;
			req.rpAmount = 0;
		}
		
		if(user.getLevel() < ConstantVal.USER_ACCOUNT_MAX_LEVEL)
		{
			//다음레벨 경험치 테이블을 얻어온다.
			UserExpResource nextExpRS = gameResource.getUserExp().get(user.getLevel() + 1);
			if(req.expAmount > nextExpRS.getNeed())
				req.expAmount = nextExpRS.getNeed();
		}
		else
		{
			req.expAmount = 0;
		}
		
		int preExp = user.getExp();
		int preRp = user.getRankPoint();
		int preTier = user.getTier();
		
		//경험치 계산
		user.setExp(user.getExp() + req.expAmount);
		
		//경험치 계산 후 내 레벨과 다르면 레벨 업!
		UserExpResource expRS = gameResource.getUserExp().calculation(user.getExp());
		if(user.getLevel() != expRS.getNumber())
		{
			res.isLevelUp = true;
			res.preLevel = user.getLevel();
			user.setLevel(expRS.getNumber());
		}
		
		float goldWeight = 1.0f;
		//랭킹 포인트 랭킹 추가
		int curSeason = gameResource.getRankingSeason().getSeason();
		if(curSeason > ConstantVal.DEFAULT_VALUE && user.getSeason() == curSeason)
		{
			RankingTierResource tierRS = gameResource.getRankingTier().get(curSeason, user.getTier());
			if(tierRS == null)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1708);
			
			if(req.rpAmount > tierRS.getRankingMaxRp())
				req.rpAmount = tierRS.getRankingMaxRp();
			
			if(req.rpAmount < tierRS.getRankingMinRp())
				req.rpAmount = tierRS.getRankingMinRp();
			
			if(user.getRankPoint() + req.rpAmount <= ConstantVal.DEFAULT_ZERO)
				user.setRankPoint(ConstantVal.DEFAULT_ZERO);
			else
				user.setRankPoint(user.getRankPoint() + req.rpAmount);				
			
			RankingTierResource myTierRS = gameResource.getRankingTier().calculationRankTier(curSeason, user.getRankPoint());
			if(myTierRS == null)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1737);
			
			//티어 계산 후 내 티어랑 다르면 티어 업!!!
			if(user.getTier() != myTierRS.getRankingTier())
			{
				res.isTierChange = true;
				res.preTier = user.getTier();
				user.setTier(myTierRS.getRankingTier());
			}
			
			if(req.rpAmount != 0)
				rankService.setUserRanking(user);
		}
		else
		{
			req.rpAmount = ConstantVal.DEFAULT_ZERO;
			goldWeight = 1.2f;
		}
		
		
		//캐릭터 일일 루비 추가
		if(req.diaAmount > 0)
		{
			int remainRuby = characters.get(index).getDailyearnlimit() - characters.get(index).getDailyearn();
			int result = Math.min(req.diaAmount, remainRuby);
			res.dia = Math.min(result, req.diaAmount);
			
			//최종 계산된 다이아를 넣어준다.
			characters.get(index).setDailyearn(characters.get(index).getDailyearn() + res.dia);
			
			//다이아 지급
			if(!repositoryService.setChangedItem(user.getId(), diaIndex, userItems, res.dia, ConstantVal.LOG_TYPE_GET_GAME_REWARD, false))
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1709);
		}
		
		GameRewardResource topRewardRS = gameResource.getGameReward().get(ConstantVal.GAME_MODE_COMPETITION, 1);
		if(topRewardRS == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1713);
		
		GameRewardResource lowRewardRS = gameResource.getGameReward().get(ConstantVal.GAME_MODE_COMPETITION, 101);
		if(lowRewardRS == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1713);
		
		if(req.goldAmount > topRewardRS.getItemCount0() && req.goldAmount < lowRewardRS.getItemCount0())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1713);
		
		req.goldAmount = (int)(req.goldAmount * goldWeight);
		
		//골드 지급
		if(!repositoryService.setChangedItem(user.getId(), itemIndex, userItems, req.goldAmount, ConstantVal.LOG_TYPE_GET_GAME_REWARD, false))
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1738);
		
		//다이아 지급
		if(req.isTicket > ConstantVal.IS_FALSE)
		{
			int ticketIndex = findData.findUserItemIndex(ConstantVal.ITEM_TYPE_ITEM, ConstantVal.COMPETITION_TICKET_ID, userItems);
			if(repositoryService.setChangedItem(user.getId(), ticketIndex, userItems, -1, ConstantVal.LOG_TYPE_USE_COMPETITION_TICKET, true))
			{
				if(req.blueDiaAmount <= 0)
				{
					int randomTicket = findData.findUserItemIndex(ConstantVal.ITEM_TYPE_ITEM, ConstantVal.RANDOM_BOX_TICKET_ID, userItems);
					if(randomTicket <= ConstantVal.DEFAULT_VALUE)
						throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1745);
					
					if(!repositoryService.setChangedItem(user.getId(), randomTicket, userItems, 1, ConstantVal.LOG_TYPE_GET_GAME_REWARD, true))
						throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1746);
				}
				else
				{
					if(!repositoryService.setChangedItem(user.getId(), blueDiaIndex, userItems, req.blueDiaAmount, ConstantVal.LOG_TYPE_GET_GAME_REWARD, false))
						throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1744);
					
					res.blueDia = req.blueDiaAmount;
				}
			}
			else
			{
				res.blueDia = 0;
			}
		}
		
		Preset preset = repositoryService.getPreset(user.getId(), false);
		if(preset == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1747);
		
		//Rankdb에 추가
		Rank rank = new Rank();
		rank.setRoomUid(req.uuid);
		rank.setUserId(user.getId());
		rank.setUserUuid(user.getUuid());
		rank.setSeason(curSeason);
		rank.setPreviousRankPoint(preRp);
		rank.setAcquiredRankPoint(req.rpAmount);
		rank.setTotalRankPoint(user.getRankPoint());
		rank.setPreviousExpPoint(preExp);
		rank.setAcquiredExpPoint(req.expAmount);
		rank.setTotalExpPoint(user.getExp());
		rank.setRank(req.rank);
		rank.setTicketRank(req.ticketRank);
		rank.setGold(req.goldAmount);
		rank.setRuby(res.dia);
		rank.setDiamond(res.blueDia);
		rank.setCharacterUid(req.characterUid);
		rank.setPartnerUid1(preset.getPartner1Uid());
		rank.setPartnerUid2(preset.getPartner2Uid());
		rank.setPartnerUid3(preset.getPartner3Uid());
		
		//DB set
		repositoryService.setRank(user.getId(), rank);
		repositoryService.setUser(user.getId(), user);
		repositoryService.setCharacter(user.getId(), index, characters);
		repositoryService.setUserItem(user.getId(), itemIndex, userItems);
		repositoryService.setUserItem(user.getId(), diaIndex, userItems);
		repositoryService.setUserItem(user.getId(), blueDiaIndex, userItems);
		
		//결과값 처리
		res.result = ConstantVal.DEFAULT_SUCCESS;
		res.gold = req.goldAmount;
		res.preExp = preExp;
		res.totalExp = user.getExp();
		res.acqExp = req.expAmount;
		res.userLevel = user.getLevel();
		res.preRp = preRp;
		res.totalRp = user.getRankPoint();
		res.acqRp = req.rpAmount;
		res.tier = user.getTier();
		res.preTier = preTier;
		res.season = gameResource.getRankingSeason().getSeason();
		
		return res;
	}
	
	
	public ResRoomCreate MinigameRoomCreate(ReqRoomCreate req) throws Exception
	{
		ResRoomCreate res = new ResRoomCreate();

		String uuid = "";
		MiniGameRoom room = null;

		while (true)
		{
			uuid = UUID.randomUUID().toString().replace("-", "");
			
			room = repositoryService.getMinigameRoom(uuid, false);
			if(room != null)
				continue;
			
			break;
		}
		
		room = new MiniGameRoom();
		room.setUuid(uuid);
		room.setGameId(req.mode);
		room.setCreatedAt(TimeCalculation.getCurrentUnixTime());
		room.setGameStartedAt(ConstantVal.DEFAULT_ZERO);
		room.setUsers("");
		room.setExpriedAt(ConstantVal.DEFAULT_ZERO);
		
		repositoryService.setMiniGameRoom(uuid, room);
		
		res.uuid = uuid;
		res.result = ConstantVal.DEFAULT_SUCCESS;
		return res;
	}
	
	public BaseResponse MiniGameStart(ReqMatchStart req) throws Exception
	{
		BaseResponse res = new BaseResponse();
		
		MiniGameRoom room = repositoryService.getMinigameRoom(req.uuid, false);
		if(room == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1749);
		
		// 룸이 만료되었거나, 이미 시작되어 있다면, 에러!!
		if(room.getGameStartedAt() > ConstantVal.DEFAULT_ZERO || room.getExpriedAt() > ConstantVal.DEFAULT_ZERO)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1750);
		
		StringBuilder sb = new StringBuilder();
		for(Integer userId : req.userList)
		{
			sb.append(userId).append(",");
		}
		
		sb.deleteCharAt(sb.length() - 1);
		
		room.setGameStartedAt(TimeCalculation.getCurrentUnixTime());
		room.setUsers(sb.toString());
		
		repositoryService.setMiniGameRoom(req.uuid, room);
		
		res.result = ConstantVal.DEFAULT_SUCCESS;
		return res;
	}
	
	public BaseResponse MiniGameRoomExpired(ReqRoomExpired req) throws Exception
	{
		BaseResponse res = new BaseResponse();
		
		MiniGameRoom room = repositoryService.getMinigameRoom(req.uuid, false);
		if(room == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1751);
		
		//이미 종료된 방입니다.
		if(room.getExpriedAt() > ConstantVal.DEFAULT_ZERO)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1752);
		
		room.setExpriedAt(TimeCalculation.getCurrentUnixTime());
		
		repositoryService.setMiniGameRoom(req.uuid, room);
		
		res.result = ConstantVal.DEFAULT_SUCCESS;
		return res;
	}
	
	public ResMiniGameResult MiniGameGameResult(ReqMiniGameResult req) throws Exception
	{
		ResMiniGameResult res = new ResMiniGameResult();
		
		if(req.userId <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1753);
		
		// 유저 정보를 찾는다.
		User user = repositoryService.getUser(req.userId, false);
		if(user == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1754);
		
		// 미니게임 룸 정보를 찾는다.
		MiniGameRoom room = repositoryService.getMinigameRoom(req.uuid, false);
		if(room == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1755);
		
		//이미 만료된 방은 보상을 받을 수 없다.(룸 안에 있을 때 보상처리가 이루어진다)
		if(room.getExpriedAt() > ConstantVal.DEFAULT_ZERO)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1756);
		
		if(room.getUsers().isEmpty())
		{
			res.result = ConstantVal.DEFAULT_SUCCESS;
			return res;
		}
		
		// 해당 룸에서 게임을 진행했던 유저정보를 찾아서, 실제 게임을 했던 유저인지 확인.
		String[] userArry = room.getUsers().split(",");
		boolean isRoomUser = false;
		for(String id : userArry)
		{
			if(user.getId() == Integer.valueOf(id))
			{
				isRoomUser = true;
				break;
			}
		}
		
		//해당 룸에서 플레이를 하지 않았던 유저이다.
		if(!isRoomUser)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1757);
		
		// 보상 받은 기록이 있는지 체크
		MinigamePlayLog log = repositoryService.getMinigamePlayLog(user.getId(), room.getUuid());
		if(log != null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1758);
		
		//rank == 3과 같다면 무승부라 아이템 처리를 하지 않는다.
		if(req.rank < 3)
		{
			// 유저 아이템 정보 얻어온다.
			List<UserItem> userItems = repositoryService.getUserItems(user.getId(), false);
			if(userItems == null || userItems.isEmpty())
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1759);
			
			// 유저 아이템 리스트에서 미니게임 티켓 index를 찾는다.
			int ticketIndex = findData.findUserItemIndex((int)ConstantVal.ITEM_TYPE_ITEM, ConstantVal.MINIGAME_TICKET_ID, userItems);
			if(ticketIndex <= ConstantVal.DEFAULT_VALUE)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1760);
			
			// 티켓을 사용했다면,
			if(req.isTicket > ConstantVal.IS_FALSE)
			{
				// 티켓 개수 체크
				if(userItems.get(ticketIndex).getItemCount() <= ConstantVal.DEFAULT_ZERO)
					throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1761);
				
				// 미니게임 티켓을 1장 소모시켜 준다.
				if(!repositoryService.setChangedItem(user.getId(), ticketIndex, userItems, -1, ConstantVal.LOG_TYPE_USE_MINIGAME_TICKET, false))
					throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1762);
			}
			
			int rubyIndex = findData.findUserItemIndex(ConstantVal.ITEM_TYPE_RUBY, ConstantVal.ITEM_TYPE_RUBY, userItems);
			if(rubyIndex <= ConstantVal.DEFAULT_VALUE)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1763);
			
			if(req.diaAmount > 0)
			{
				if(!repositoryService.setChangedItem(user.getId(), rubyIndex, userItems, req.diaAmount, ConstantVal.LOG_TYPE_GET_MINIGAME_REWARD, false))
					throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1764);
			}
			
			//db set
			repositoryService.setUserItem(user.getId(), ticketIndex, userItems);
			repositoryService.setUserItem(user.getId(), rubyIndex, userItems);
		}
		
		// log 추가
		MiniGamePlayLog(user.getId(), room.getGameId(), room.getUuid(), req.rank, req.diaAmount, req.isTicket);
		
		res.result = ConstantVal.DEFAULT_SUCCESS;
		res.dia = req.diaAmount;
		
		return res;
	}
	
	
	public ResContentsAdmission ContentsAdmission(int userId, ReqContentsAdmission req) throws Exception
	{
		ResContentsAdmission res = new ResContentsAdmission();
		
		ContentsAdminssion admission = repositoryService.getContentsAdmission(req.contentsId);
		if(admission == null)
		{
			admission = new ContentsAdminssion();
			admission.setContentsId(req.contentsId);
			admission.setValue(false);
		}
		
		if(!admission.isValue())
			res.result = ConstantVal.DEFAULT_SUCCESS;
		else
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1765); //점검 중입니다. 에러메시지 추가
		
		if(req.isTicket == ConstantVal.IS_TRUE)
		{
			List<UserItem> items = repositoryService.getUserItems(userId, false);
			if(items == null || items.isEmpty())
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1766); // 유저의 아이템 정보를 찾을 수 없습니다.
			
			int index = ConstantVal.DEFAULT_VALUE;
			if(req.contentsId == ConstantVal.GAME_MODE_COMPETITION)
			{
				index = findData.findUserItemIndex(ConstantVal.ITEM_TYPE_ITEM, ConstantVal.COMPETITION_TICKET_ID, items);
				if(index <= ConstantVal.DEFAULT_VALUE)
					throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1767); // 아이템을 찾을 수 없습니다.
				
				if(items.get(index).getItemCount() <= ConstantVal.DEFAULT_ZERO)
					throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1768); // 경쟁전 티켓이 부족합니다.
			}
			else
			{
				index = findData.findUserItemIndex(ConstantVal.ITEM_TYPE_ITEM, ConstantVal.MINIGAME_TICKET_ID, items);
				if(index <= ConstantVal.DEFAULT_ZERO)
					throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1769); // 아이템을 찾을 수 없습니다.
				
				if(items.get(index).getItemCount() <= ConstantVal.DEFAULT_ZERO)
					throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MATCH_1770); // 미니게임 티켓이 부족합니다.
			}
		}
		
		return res;
	}
	
	private void MiniGamePlayLog(int userId, int gameId, String roomId, int rank, int ruby, byte isTicket) throws Exception
	{
		MinigamePlayLog log = new MinigamePlayLog();
		
		log.setPtn_month((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_MONTH, 0));
		log.setPtn_day((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_DAY, 0));
		log.setLog_date(TimeCalculation.getCurrentUnixTime());
		log.setRoom_uid(roomId);
		log.setUser_id(userId);
		log.setGame_id(gameId);
		log.setRank(rank);
		log.setRuby(ruby);
		log.setIs_ticket(isTicket);
		
		repositoryService.setMiniGamePlayLog(log);
	}

}
