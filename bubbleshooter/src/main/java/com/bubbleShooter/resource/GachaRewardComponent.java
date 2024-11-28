package com.bubbleShooter.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.VO.RewardItemVO;
import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;
import com.bubbleShooter.util.FindData;

@Component
public class GachaRewardComponent {
	@Autowired
	private StorageContext storageContext;
	
	@Autowired
	private FindData findData;
	
	private HashMap<Integer, List<GachaRewardResource>> resource = new HashMap<>();
	
	public void LoadResource() throws Exception
	{
		resource.clear();
		
		List<GachaRewardResource> gachaRewardRS = storageContext.<GachaRewardResource>getDataList(ConstantVal.STATIC_DB, "gacha_reward_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(gachaRewardRS == null || gachaRewardRS.isEmpty())
		{
			System.out.println("GachaRewardResource No data");
			System.exit(0);
		}
		
		for(GachaRewardResource reward : gachaRewardRS)
		{
			if(resource.containsKey(reward.getGrade()))
			{
				List<GachaRewardResource> value = resource.get(reward.getGrade());
				if(value == null || value.isEmpty())
				{
					String errorMsg = "GachaRewardResource empty data - key:" + reward.getGrade();
					System.out.println(errorMsg);
					System.exit(0);
				}
				
				value.add(reward);
			}
			else
			{
				List<GachaRewardResource> value = new ArrayList<>();
				value.add(reward);
				
				resource.put(reward.getGrade(), value);
			}
		}
		System.out.println("GachaRewardResource Loading Complete");
	}
	
	public List<GachaRewardResource> get(int grade)
	{
		List<GachaRewardResource> value = resource.get(grade);
		if(value == null || value.isEmpty())
			return null;
		
		return value;
	}
	
	public RewardItemVO getGachaRewardItem(int grade)
	{
		List<GachaRewardResource> value = resource.get(grade);
		if(value == null || value.isEmpty())
			return null;
		
		int rewardIndex = findData.getRandInt(0, (value.size() - 1));
		GachaRewardResource reward = value.get(rewardIndex);
		if(reward == null)
			return null;
		
		RewardItemVO vo = new RewardItemVO();
		vo.itemType = reward.getItemType();
		vo.itemId = reward.getItemId();
		vo.itemCount = reward.getItemCount();
		vo.grade = (int)reward.getHeroGrade();
		
		return vo;
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("GachaRewardResource Verify Complete");
	}
}
