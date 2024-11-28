package com.bubbleShooter.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class ShopComponent {
	@Autowired
	private StorageContext storageContext;
	
	private HashMap<Integer, ShopResource> resource = new HashMap<>();
	private List<ShopResource> shopList = new ArrayList<>();

	
	public void LoadResource() throws Exception
	{
		resource.clear();
		
		List<ShopResource> shopRS = storageContext.<ShopResource>getDataList(ConstantVal.STATIC_DB, "shop_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(shopRS == null || shopRS.isEmpty())
		{
			System.out.println("ShopResource No data");
			System.exit(0);
		}
		
		for(ShopResource data : shopRS)
		{
			if(resource.containsKey(data.getProductId()))
			{
				String errorMsg = "ShopResource Overlap data:" + data.getProductId();
				System.out.println(errorMsg);
				System.exit(0);
			}
			
			// resource에 키값에 따른 데이터 넣어주기
			resource.put(data.getProductId(), data);
			shopList.add(data);
		}
		
		System.out.println("ShopResource Loading Complete");
	}
	
	public ShopResource get(int productId)
	{
		if(!resource.containsKey(productId))
			return null;
		
		return resource.get(productId);
	}
	
	public List<ShopResource> get()
	{
		return shopList;
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("ShopResource Verify Complete");
	}
}
