package com.bubbleShooter.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class ItemComponent {
	@Autowired
	private StorageContext storageContext;
	
	private HashMap<Integer, ItemResource> resource = new HashMap<>();
	private List<ItemResource> items = new ArrayList<>();
	
	public void LoadResource() throws Exception
	{
		resource.clear();
		
		List<ItemResource> itemRS = storageContext.<ItemResource>getDataList(ConstantVal.STATIC_DB, "item_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		{
			if(itemRS == null || itemRS.isEmpty())
			{
				System.out.println("ItemResource No data");
				System.exit(0);
			}
		}
		
		for(ItemResource data : itemRS)
		{
			if(resource.containsKey(data.getItemId()))
			{
				String errorMsg = "ItemResource Overlap data:" + data.getItemId();
				System.out.println(errorMsg);
				System.exit(0);
			}
			
			resource.put(data.getItemId(), data);
			items.add(data);
		}
		
		System.out.println("ItemResource Loading Complete");
	}
	
	public ItemResource get(int id)
	{
		if(!resource.containsKey(id))
			return null;
		
		return resource.get(id);
	}
	
	public List<ItemResource> getItemList()
	{
		if(items.size() <= 0)
			return null;
		
		return items;
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("ItemResource Verify Complete");
	}
}
