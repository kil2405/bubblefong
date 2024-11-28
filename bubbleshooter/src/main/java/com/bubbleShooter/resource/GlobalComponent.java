package com.bubbleShooter.resource;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class GlobalComponent {
	@Autowired
	private StorageContext storageContext;
	
	private HashMap<Integer, GlobalResource> resource = new HashMap<>();
	
	public void LoadResource() throws Exception
	{
		List<GlobalResource> globalRS = storageContext.<GlobalResource>getDataList(ConstantVal.STATIC_DB, "global_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(globalRS == null || globalRS.isEmpty())
		{
			System.out.println("GlobalResource No data");
			System.exit(0);
		}
		
		for(GlobalResource data : globalRS)
		{
			if(resource.containsKey(data.getId()))
			{
				String errorMsg = "GlobalResource Overlap data:" + data.getId();
				System.out.println(errorMsg);
				System.exit(0);
			}
			
			resource.put(data.getId(), data);
		}
		
		System.out.println("GlobalResource Loading Complete");
	}
	
	public GlobalResource get(int id)
	{
		if(!resource.containsKey(id))
			return null;
		
		return resource.get(id);
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("GlobalResource Verify Complete");
	}
}
