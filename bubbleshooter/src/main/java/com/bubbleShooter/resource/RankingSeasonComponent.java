package com.bubbleShooter.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;
import com.bubbleShooter.util.TimeCalculation;

@Component
public class RankingSeasonComponent {
	@Autowired
	private StorageContext storageContext;
	
	private HashMap<Integer, RankingSeasonResource> resource = new HashMap<>();
	private List<RankingSeasonResource> seasonList = new ArrayList<>();
	private RankingSeasonResource curSeason = null;
	
	public void LoadResource() throws Exception
	{
		if(curSeason != null)
		{
			if(TimeCalculation.checkTimeNow(curSeason.getSeasonStart(), curSeason.getSeasonEnd()))
				return;
		}
		
		resource = new HashMap<>();
		seasonList = new ArrayList<>();
		
		List<RankingSeasonResource> rankingSeasonRS = storageContext.<RankingSeasonResource>getDataList(ConstantVal.STATIC_DB, "ranking_season_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(rankingSeasonRS == null || rankingSeasonRS.isEmpty())
		{
			System.out.println("RankingSeasonResource No data");
			System.exit(0);
		}
		
		for(RankingSeasonResource data : rankingSeasonRS)
		{
			int key = createKey(data.getIndex(), data.getRankingSeasonId());
			
			if(resource.containsKey(key))
			{
				String errorMsg = "RankingSeasonResource Overlap index:" + data.getIndex() + "|seasonId: " + data.getRankingSeasonId();
				System.out.println(errorMsg);
				System.exit(0);
			}
			
			// resource에 키값에 따른 데이터 넣어주기
			resource.put(key, data);
			
			//실제 시즌만 넣어준다. (비시즌은 제거)
			if(data.getRankingSeasonId() > ConstantVal.DEFAULT_VALUE)
			{
				seasonList.add(data);
			}
			
			if(TimeCalculation.checkTimeNow(data.getSeasonStart(), data.getSeasonEnd()))
			{
				curSeason = data;
			}
		}
		
		System.out.println("RankingSeasonResource Loading Complete");
	}
	
	private int createKey(int index, int rankingSeasonId)
	{
		int key = index * rankingSeasonId;
		return key;
	}
	
	public int getPreSeason() throws Exception
	{
		LoadResource();
		return curSeason.getPreviousSeason();
	}
	
	public RankingSeasonResource get() throws Exception
	{
		LoadResource();
		
		if(curSeason != null)
			return curSeason;
		
		return null;
	}
	
	public int getSeason() throws Exception
	{
		LoadResource();
		
		return curSeason.getRankingSeasonId();
	}
	
	public RankingSeasonResource getSeason(int seasonId) throws Exception
	{
		if(seasonId > ConstantVal.DEFAULT_VALUE)
		{
			for(RankingSeasonResource data : seasonList)
			{
				if(data.getRankingSeasonId() == seasonId)
					return data;
			}
		}
		return null;
	}
	
	public int getSeasonDayCount() throws Exception
	{
		int dayCount = 0;
		for(RankingSeasonResource data : seasonList)
		{
			if(!TimeCalculation.checkTimeNow(data.getSeasonStart(), data.getSeasonEnd()))
				continue;
			
			dayCount = (int)TimeCalculation.diffOfDays(data.getSeasonStart(), data.getSeasonEnd());
			break;
		}
		
		return dayCount;
	}
	
	
	public void VerifyResource() throws Exception
	{
		System.out.println("RankingSeasonResource Verify Complete");
	}
}
