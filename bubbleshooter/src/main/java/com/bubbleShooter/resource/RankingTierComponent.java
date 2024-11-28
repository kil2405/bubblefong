package com.bubbleShooter.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class RankingTierComponent {
	@Autowired
	private StorageContext storageContext;
	
	private HashMap<Integer, List<RankingTierResource>> resource = new HashMap<>();
	
	public void LoadResource() throws Exception
	{
		resource.clear();
		
		List<RankingTierResource> rankingTierRS = storageContext.<RankingTierResource>getDataList(ConstantVal.STATIC_DB, "ranking_tier_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(rankingTierRS == null || rankingTierRS.isEmpty())
		{
			System.out.println("RankingTierResource No data");
			System.exit(0);
		}
		
		for(RankingTierResource data : rankingTierRS)
		{
			if(resource.containsKey(data.getRankingSeasonId()))
			{
				List<RankingTierResource> tier = resource.get(data.getRankingSeasonId());
				if(tier == null || tier.isEmpty())
				{
					System.out.println("RankingTierResource No data");
					System.exit(0);
				}
				
				tier.add(data);
				resource.put(data.getRankingSeasonId(), tier);
			}
			else
			{
				List<RankingTierResource> tier = new ArrayList<>();
				
				tier.add(data);
				resource.put(data.getRankingSeasonId(), tier);
			}
		}
		
		System.out.println("RankingTierResource Loading Complete");
	}
	
	
	public RankingTierResource get(int season, int tier)
	{
		if(!resource.containsKey(season))
			season = ConstantVal.DEFAULT_SEASON;
		
		List<RankingTierResource> tierList = resource.get(season);
		if(tierList == null || tierList.isEmpty())
			return null;
		
		for(RankingTierResource data : tierList)
		{
			if(data == null)
				continue;
			
			if(data.getRankingTier() == tier)
				return data;
		}
		
		return null;
	}
	
	public RankingTierResource calculationRankTier(int seasonId, int rpPoint)
	{
		if(!resource.containsKey(seasonId))
			seasonId = ConstantVal.DEFAULT_SEASON;
		
		List<RankingTierResource> tierList = resource.get(seasonId);
		if(tierList == null || tierList.isEmpty())
			return null;
		
		RankingTierResource prevTier = null;
		for(RankingTierResource tier : tierList)
		{
			if(rpPoint >= tier.getRankingTierStandardPoint())
				prevTier = tier;
			else
				break;
		}
		
		return prevTier;
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("RankingTierResource Verify Complete");
	}
}
