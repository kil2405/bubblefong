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
public class ShopRandomComponent {
	@Autowired
	private StorageContext storageContext;
	
	@Autowired
	private FindData findData;
	
	private HashMap<Integer, List<ShopRandomResource>> resource = new HashMap<>();
	private HashMap<Integer, Integer> accrueRateResource = new HashMap<>();
	
	public void LoadResource() throws Exception
	{
		resource.clear();
		
		List<ShopRandomResource> shopRandomRS = storageContext.<ShopRandomResource>getDataList(ConstantVal.STATIC_DB, "shop_random_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(shopRandomRS == null || shopRandomRS.isEmpty())
		{
			System.out.println("ShopRandomResource No data");
			System.exit(0);
		}
		
		for(ShopRandomResource rand : shopRandomRS)
		{
			if(resource.containsKey(rand.getRandomId()))
			{
				List<ShopRandomResource> value = resource.get(rand.getRandomId());
				if(value == null || value.isEmpty())
				{
					String errorMsg = "ShopRandomResource empty data - key:" + rand.getRandomId();
					System.out.println(errorMsg);
					System.exit(0);
				}
				
				value.add(rand);
				accrueRateResource.put(rand.getRandomId(), accrueRateResource.get(rand.getRandomId()) + rand.getRate());
			}
			else
			{
				List<ShopRandomResource> value = new ArrayList<>();
				value.add(rand);
				resource.put(rand.getRandomId(), value);
				
				accrueRateResource.put(rand.getRandomId(), rand.getRate());
			}
		}
		System.out.println("ShopRandomResource Loading Complete");
	}
	
	public List<ShopRandomResource> get(int randomId)
	{
		List<ShopRandomResource> value = resource.get(randomId);
		if(value == null || value.isEmpty())
			return null;
		
		return value;
	}
	
	public int getAccrueRate(int randomId)
	{
		if(!accrueRateResource.containsKey(randomId))
			return ConstantVal.DEFAULT_VALUE;
		
		return accrueRateResource.get(randomId);
	}
	
	public RewardItemVO OpenRandomBox(int randomId)
	{
		List<ShopRandomResource> randomRSList = get(randomId);
		if(randomRSList == null || randomRSList.isEmpty())
			return null;
		
		int accureRate = getAccrueRate(randomId);
		if(accureRate <= ConstantVal.DEFAULT_ZERO)
			return null;
		
		RewardItemVO rewardVo = null;
		int randomRate = findData.getRandInt(accureRate) + 1;
		
		int accure = 0;
		for(ShopRandomResource rs : randomRSList)
		{
			accure += rs.getRate();
			
			if(randomRate > accure)
				continue;
			
			rewardVo = new RewardItemVO();
			rewardVo.itemType = rs.getItemType();
			rewardVo.itemId = rs.getItemId();
			rewardVo.itemCount = findData.getRandInt(rs.getMinCount(), rs.getMaxCount());
			
			break;
		}
		
		return rewardVo;
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("ShopRandomResource Verify Complete");
	}
}
