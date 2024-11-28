package com.bubbleShooter.controller.shop;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bubbleShooter.VO.RewardItemVO;
import com.bubbleShooter.VO.ShopVO;
import com.bubbleShooter.common.BubbleException;
import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.ErrorCodeInfo;
import com.bubbleShooter.common.GameResource;
import com.bubbleShooter.common.RepositoryService;
import com.bubbleShooter.domain.ShopPurchaseLog;
import com.bubbleShooter.domain.UserItem;
import com.bubbleShooter.domain.UserShopBuyLog;
import com.bubbleShooter.request.ReqComposeCharacter;
import com.bubbleShooter.request.ReqComposePartner;
import com.bubbleShooter.request.ReqShopBuyProduct;
import com.bubbleShooter.request.ReqStarPackRate;
import com.bubbleShooter.resource.ShopRandomResource;
import com.bubbleShooter.resource.ShopResource;
import com.bubbleShooter.resource.ShopRewardResource;
import com.bubbleShooter.resource.SynthesisitemResource;
import com.bubbleShooter.response.ResComposeCharacter;
import com.bubbleShooter.response.ResComposePartner;
import com.bubbleShooter.response.ResShopBuyProduct;
import com.bubbleShooter.response.ResShopInfo;
import com.bubbleShooter.response.ResStarPackRate;
import com.bubbleShooter.util.FindData;
import com.bubbleShooter.util.MapperVO;
import com.bubbleShooter.util.TimeCalculation;

@Service
public class ShopService {
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private GameResource gameResource;
	
	@Autowired
	private FindData findData;
	
	@Autowired
	private MapperVO mapperVO;
	
	public ResShopInfo ShopInfo(int userId) throws Exception
	{
		ResShopInfo res = new ResShopInfo();
		
		List<UserShopBuyLog> shopLogs = repositoryService.getUserShopBuyLog(userId, false);
		if(shopLogs == null)
			shopLogs = new ArrayList<>();
		
		List<ShopVO> shopVOs = getShopProductList(userId, shopLogs, false);
		if(shopVOs == null || shopVOs.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3112);
		
		res.shop = shopVOs;
		res.result = ConstantVal.DEFAULT_SUCCESS;
		return res;
	}
	
	public ResShopBuyProduct ShopBuyProduct(int userId, ReqShopBuyProduct req) throws Exception
	{
		ResShopBuyProduct res = new ResShopBuyProduct();
		res.rewards = new ArrayList<>();
		
		ShopResource shopRS = gameResource.getShop().get(req.productId);
		if(shopRS == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3113);
		
		// 상품 판매 기간인지 체크
		if(shopRS.getSellStartDate() > 0)
		{
			if(!TimeCalculation.checkTimeNow(shopRS.getSellStartDate(), shopRS.getSellEndDate()))
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3114);
		}
		
		// 구매 제한 상품일 경우, 구매 개수체크
		if(shopRS.getBuyLimit() > 0)
		{
			// 유저 상품 구매 기록을 찾아온다.
			List<UserShopBuyLog> userShopBuyLogs = repositoryService.getUserShopBuyLog(userId, false);
			UserShopBuyLog log = getBuyUserBuyLog(shopRS.getProductId(), userShopBuyLogs);
			if(log != null)
			{
				//구매한 기록이 있을 경우, 구매 제한 기간 체크
				if(TimeCalculation.diffOfUnixTime(log.getLimitDate()) > 0)
				{
					// 구매 제한 기간일 경우, 구매한 개수 체크
					if(log.getBuyCount() >= shopRS.getBuyLimit())
						throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3115);
				}
			}
		}
		
		int buyPrice = shopRS.getPrice();
		// 구매하고자 하는 아이템의 할인율이 적용되어 있을 경우
		if(shopRS.getDiscountRate() > 0)
		{
			// 할인이 적용된 금액으로 구매한다.
			buyPrice = shopRS.getDiscountPrice();
		}
		
		// 구매
		// 유저 아이템 정보 얻어오기
		List<UserItem> items = repositoryService.getUserItems(userId, false);
		byte itemIndex = findData.findUserItemIndex(shopRS.getPriceItemType(), shopRS.getPriceItemId(), items);
		if(itemIndex <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3116);
		
		if(req.useTicket == ConstantVal.IS_TRUE)
		{
			int ticketId = shopRS.getTicketId();
			if(ticketId <= ConstantVal.DEFAULT_ZERO)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3117);
			
			itemIndex = findData.findUserItemIndex(ConstantVal.ITEM_TYPE_RANDOM_PACK_TICKET, ticketId, items);
			if(itemIndex <= ConstantVal.DEFAULT_VALUE)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3118);
			
			buyPrice = 1;
		}
		
		// 금액 체크 및 구매 금액 차감
		if(!repositoryService.setChangedItem(userId, itemIndex, items, -buyPrice, ConstantVal.LOG_TYPE_USE_BUY_SHOP, shopRS.getDesc(), false))
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3119);
		
		// 상점 보상 테이블을 얻어온다.
		List<ShopRewardResource> shopRewardRS = gameResource.getShopReward().get(shopRS.getProductId());
		if(shopRewardRS == null || shopRewardRS.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3123);
		
		// 상점 구매 아이템 보상목록을 만든다.
		List<RewardItemVO> rewardVO = new ArrayList<>();
		for(ShopRewardResource reward : shopRewardRS)
		{
			if(reward.getItemType() == ConstantVal.ITEM_TYPE_RANDOM_PACK_TICKET)
			{
				rewardVO.addAll(openBox(userId, reward.getItemId(), reward.getItemCount(), false));
			}
			else
			{
				rewardVO.add(mapperVO.makeRewardItemVO(reward.getItemType(), reward.getItemId(), reward.getItemCount()));
			}
		}
		
		if(rewardVO.size() <= ConstantVal.DEFAULT_ZERO)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3124);
		
		// 아이템 소모 처리
		repositoryService.setUserItem(userId, itemIndex, items);
		
		// 보상 지급 처리
		mapperVO.makeRewardResult(userId, items, rewardVO, ConstantVal.LOG_TYPE_GET_BUY_SHOP, shopRS.getDesc());
		
		ResShopInfo shopInfo = ShopInfo(userId);
		if(shopInfo == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3125);
		
		res.result = ConstantVal.DEFAULT_SUCCESS;
		res.rewards.addAll(rewardVO);
		res.items = mapperVO.makeItemVO(userId, items, false);
		res.characters = mapperVO.makeCharacterVO(userId, null, false);
		res.partners = mapperVO.makePartnerVO(userId, null, false);
		
		// 로그 추가
		UserShopBuyLog log = new UserShopBuyLog();
		log.setUserId(userId);
		log.setProductId(shopRS.getProductId());
		log.setBuyCount(1);
		log.setLimitDate(findData.getShopSellLimitTime(shopRS.getSellType()));
		log.setLastBuyTime(TimeCalculation.getCurrentUnixTime());
		
		ShopPurchaseLog purchaseLog = new ShopPurchaseLog();
		purchaseLog.setPtn_month((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_MONTH, 0));
		purchaseLog.setLog_date(TimeCalculation.getCurrentUnixTime());
		purchaseLog.setUser_id(userId);
		purchaseLog.setProduct_id(req.productId);
		purchaseLog.setPrice(buyPrice);
		
		repositoryService.setUserShopBuyLog(userId, log, purchaseLog);
		
		return res;
	}
	
	public List<ShopVO> getShopProductList(long userId, List<UserShopBuyLog> userShopBuyLogLst, boolean dbForceSearch) throws Exception
	{
		List<ShopVO> values = new ArrayList<>();
		
		List<ShopResource> shopProducts = gameResource.getShop().get();
		for(ShopResource data : shopProducts)
		{
			if(data.getSellStartDate() > 0)
			{
				long startDate = TimeCalculation.StringDateToUnixTime(String.valueOf(data.getSellStartDate())) / 1000;
				
				long endDate = 0;
				if(data.getSellEndDate() > 0)
					endDate = TimeCalculation.StringDateToUnixTime(String.valueOf(data.getSellEndDate())) / 1000;
				
				if(!TimeCalculation.checkTimeNow(startDate, endDate))
					continue;
			}
			
			boolean isLimit = false;
			UserShopBuyLog log = getBuyUserBuyLog(data.getProductId(), userShopBuyLogLst);
			if(log != null)
			{
				if(TimeCalculation.diffOfUnixTime(log.getLimitDate()) >= 0)
					isLimit = true;
			}
			
			ShopVO shopVO = mapperVO.makeShopVO(userId, data);
			shopVO.buyCount = isLimit ? log.getBuyCount() : ConstantVal.DEFAULT_ZERO;
			shopVO.remainTime = ConstantVal.DEFAULT_VALUE;
			
			if(isLimit)
			{
				//판매제한이 걸려있으면, 제한이 풀리기까지 남은 시간.
				shopVO.remainTime = log.getLimitDate() - TimeCalculation.getCurrentUnixTime();
				
				if(data.getSellType() == ConstantVal.SHOP_SELL_TYPE_ACCOUNT)
					shopVO.remainTime = ConstantVal.DEFAULT_VALUE;
			}
			else
			{
				//판매제한이 걸려있지 않으면, 판매종료까지 남은 시간.
				if(data.getSellEndDate() > 0)
					shopVO.remainTime = data.getSellEndDate() - TimeCalculation.getCurrentUnixTime();
			}
			
			List<ShopRewardResource> shopRewardRS = gameResource.getShopReward().get(data.getProductId());
			if(shopRewardRS == null)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3126);
			
			shopVO.rewards = new ArrayList<>();
			
			for(ShopRewardResource reward : shopRewardRS)
				shopVO.rewards.add(mapperVO.makeRewardItemVO(reward.getItemType(), reward.getItemId(), reward.getItemCount()));
			
			values.add(shopVO);
		}
		
		return values;
	}
	
	private UserShopBuyLog getBuyUserBuyLog(int productId, List<UserShopBuyLog> userShopBuyLogLst)
	{
		for(UserShopBuyLog log : userShopBuyLogLst)
		{
			if(log.getProductId() == productId)
				return log;
		}
		
		return null;
	}
	
	public ResStarPackRate RandomRate(long userId, ReqStarPackRate req) throws Exception
	{
		ResStarPackRate res = new ResStarPackRate();
		res.rateList = new ArrayList<>();

		List<ShopRandomResource> shopRandomRS = gameResource.getShopRandom().get(req.productId);
		if(shopRandomRS == null || shopRandomRS.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3127);
		
		for(ShopRandomResource data : shopRandomRS)
			res.rateList.add(mapperVO.makeShopProductRateVO(data));
		
		res.result = ConstantVal.DEFAULT_SUCCESS;
		return res;
	}
	
	//뽑기팩오픈
	public List<RewardItemVO> openBox(int userId, int randomId, int count, boolean dbUpdate) throws Exception
	{
		List<ShopRandomResource> randomRS = gameResource.getShopRandom().get(randomId);
		if(randomRS == null || randomRS.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3128);
		
		int accureRate = gameResource.getShopRandom().getAccrueRate(randomId);
		if(accureRate <= ConstantVal.DEFAULT_ZERO)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3129);
		
		List<RewardItemVO> rewards = new ArrayList<>();
		
		for(int i = 0; i < count; i++)
		{
			int accure = 0;
			int randomRate = findData.getRandInt(accureRate) + 1;
			
			for(ShopRandomResource rs : randomRS)
			{
				if(rs.getRate() <= ConstantVal.DEFAULT_VALUE)
				{
					RewardItemVO vo = new RewardItemVO();
					vo.itemType = rs.getItemType();
					vo.itemId = rs.getItemId();
					vo.itemCount = findData.getRandInt(rs.getMinCount(), rs.getMaxCount());
					
					rewards.add(vo);
					continue;
				}
				
				accure += rs.getRate();
				
				if(randomRate > accure)
					continue;
				
				RewardItemVO vo = new RewardItemVO();
				vo.itemType = rs.getItemType();
				vo.itemId = rs.getItemId();
				vo.itemCount = findData.getRandInt(rs.getMinCount(), rs.getMaxCount());
				if(rs.getHeroGrade() > 0)
					vo.grade = (int)rs.getHeroGrade();
				
				rewards.add(vo);
				break;
			}
		}
		
		if(dbUpdate)
		{
			List<RewardItemVO> newRewards = mapperVO.makeRewardResult(userId, null, rewards, ConstantVal.LOG_TYPE_GET_MAIL_ITEM);
			return newRewards;
		}
		
		return rewards;
	}
	
	public ResComposeCharacter ComposeCharacter(int userId, ReqComposeCharacter req) throws Exception
	{
		ResComposeCharacter res = new ResComposeCharacter();
		
		List<UserItem> items = repositoryService.getUserItems(userId, false);
		if(items == null || items.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3137);
		
		int ingredInext1 = findData.findUserItemIndex(ConstantVal.ITEM_TYPE_ITEM, req.ingredientItemId1, items);
		if(ingredInext1 <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3138);
		
		int ingredInext2 = findData.findUserItemIndex(ConstantVal.ITEM_TYPE_ITEM, req.ingredientItemId2, items);
		if(ingredInext2 <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3139);
		
		int resultItemIndex = findData.findUserItemIndex(ConstantVal.ITEM_TYPE_ITEM, req.resultItemId, items);
		if(resultItemIndex <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3140);
		
		//첫번째 재료 아이템 삭제 DB업데이트는 아직 안함
		if(!repositoryService.setChangedItem(userId, ingredInext1, items, -1, ConstantVal.LOG_TYPE_USE_COMPOSE_CHARACTER, false))
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3141);
		
		//두번째 재료 아이템 삭제 DB업데이트는 아직 안함
		if(!repositoryService.setChangedItem(userId, ingredInext2, items, -1, ConstantVal.LOG_TYPE_USE_COMPOSE_CHARACTER, false))
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3142);
		
		//최종 결과 아이템 지급
		if(!repositoryService.setChangedItem(userId, resultItemIndex, items, 1, ConstantVal.LOG_TYPE_GET_COMPOSE_CHARACTER, false))
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3143);
		
		repositoryService.setUserItem(userId, ingredInext1, items);
		repositoryService.setUserItem(userId, ingredInext2, items);
		repositoryService.setUserItem(userId, resultItemIndex, items);
		
		res.result = ConstantVal.DEFAULT_SUCCESS;
		res.resultItemId = req.resultItemId;
		res.items = mapperVO.makeItemVO(userId, items, false);
		
		return res;
	}
	
	public ResComposePartner ComposePartner(int userId, ReqComposePartner req) throws Exception
	{
		ResComposePartner res = new ResComposePartner();
		res.composeItems = new ArrayList<>();
		res.composeItems.add(mapperVO.makeRewardItemVO(ConstantVal.ITEM_TYPE_ITEM, req.itemId, req.useItemCount));
		
		List<UserItem> items = repositoryService.getUserItems(userId, false);
		if(items == null || items.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3144);
		
		int index = findData.findUserItemIndex(ConstantVal.ITEM_TYPE_ITEM, req.itemId, items);
		if(index <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3145);
		
		//아이템 개수가 2개보다 작거나, 5개보다 많으면 에러!!
		if(req.useItemCount < 2 || req.useItemCount > 5)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3146);

		SynthesisitemResource resource = gameResource.getSynthesisitem().get(req.itemId, req.useItemCount);
		if(resource == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3137);
		
		int percent = resource.getItemRate();
		boolean success = false;
		
		int randomRate = findData.getRandInt(ConstantVal.MAX_RATE);
		if(randomRate < percent)
			success = true;
		
		//재료 아이템 삭제
		if(!repositoryService.setChangedItem(userId, index, items, -req.useItemCount, ConstantVal.LOG_TYPE_USE_COMPOSE_PARTNER, false))
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3147);

		if(success)
		{
			List<RewardItemVO> rewards = new ArrayList<>();

			if(resource.getRewardType() == ConstantVal.ITEM_TYPE_ITEM)
			{
				int rewardIndex = findData.findUserItemIndex(ConstantVal.ITEM_TYPE_ITEM, resource.getRewardItem(), items);
				if(rewardIndex <= ConstantVal.DEFAULT_VALUE)
					throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3148);
				
				rewards.add(mapperVO.makeRewardItemVO(items.get(rewardIndex).getItemType(), items.get(rewardIndex).getItemId(), resource.getRewardCount()));
			}
			else if(resource.getRewardType() == ConstantVal.ITEM_TYPE_RANDOM_PACK_TICKET)
			{
				rewards.addAll(openBox(userId, resource.getRewardItem(), resource.getRewardCount(), false));
			}
			else
			{
				// error
			}

			// 재료 아이템 삭제(실제 DB업데이트)
			repositoryService.setUserItem(userId, index, items);

			// 보상 아이템 지급(실제 DB업데이트)
			for(RewardItemVO reward : rewards)
			{
				int type = findData.findUserItemIndex(reward.itemType, reward.itemId, items);
				if(!repositoryService.setChangedItem(userId, type, items, reward.itemCount, ConstantVal.LOG_TYPE_GET_COMPOSE_PARTNER, true))
					throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3148);
				
				res.composeItems = rewards;
			}
		}
		else
		{	
			//실패 시, 재료템 한개를 돌려준다.
			if(!repositoryService.setChangedItem(userId, index, items, 1, ConstantVal.LOG_TYPE_USE_COMPOSE_PARTNER, false))
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_SHOP_3150);
			
			repositoryService.setUserItem(userId, index, items);
		}
		
		res.result = ConstantVal.DEFAULT_SUCCESS;
		res.success = success;
		res.items = mapperVO.makeItemVO(userId, items, false);
		
		return res;
	}
}
