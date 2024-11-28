package com.bubbleShooter.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class ShopRewardComponent {
	@Autowired
	private StorageContext storageContext;
	
private HashMap<Integer, List<ShopRewardResource>> resource = new HashMap<>();
	
	public void LoadResource() throws Exception
	{
		resource.clear();
		
		List<ShopRewardResource> shopRewardRS = storageContext.<ShopRewardResource>getDataList(ConstantVal.STATIC_DB, "shop_reward_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(shopRewardRS == null || shopRewardRS.isEmpty())
		{
			System.out.println("ShopRewardResource No data");
			System.exit(0);
		}
		
		for(ShopRewardResource data : shopRewardRS)
		{
			if(resource.containsKey(data.getProductId()))
			{
				List<ShopRewardResource> value = resource.get(data.getProductId());
				if(value == null || value.isEmpty())
				{
					System.out.println("ShopRewardResource No data");
					System.exit(0);
				}
				
				value.add(data);
			}
			else
			{
				List<ShopRewardResource> value = new ArrayList<>();
				value.add(data);
				resource.put(data.getProductId(), value);
			}
		}
		
		System.out.println("ShopRewardResource Loading Complete");
	}
	
	public List<ShopRewardResource> get(int productId)
	{
		if(!resource.containsKey(productId))
			return null;
		
		return resource.get(productId);
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("ShopRewardResource Verify Complete");
	}
}
