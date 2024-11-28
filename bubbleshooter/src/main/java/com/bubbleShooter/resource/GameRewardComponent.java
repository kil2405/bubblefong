package com.bubbleShooter.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class GameRewardComponent {
	@Autowired
	private StorageContext storageContext;
	
	private List<GameRewardResource> resource = new ArrayList<>();
	
	public void LoadResource() throws Exception
	{
		resource.clear();
		
		List<GameRewardResource> rewardRS = storageContext.<GameRewardResource>getDataList(ConstantVal.STATIC_DB, "game_reward_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(rewardRS == null || rewardRS.isEmpty())
		{
			System.out.println("GameRewardResource No data");
			System.exit(0);
		}
		
		for(GameRewardResource data : rewardRS)
		{
			if(data != null)
				resource.add(data);
		}
		
		System.out.println("GameRewardResource Loading Complete");
	}
	
	public GameRewardResource get(int gameMode, int ranking)
	{
		for(GameRewardResource data : resource)
		{
			if(data == null)
				continue;
			
			if(data.getRankFrom() <= ranking && data.getRankTo() >= ranking)
				return data;
		}
		return null;
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("GameRewardResource Verify Complete");
	}
}
