package com.bubbleShooter.resource;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class PartnerUpgradeComponent {
	@Autowired
	private StorageContext storageContext;
	
	private HashMap<Integer, PartnerUpgradeResource> resource = new HashMap<>();
	
	public void LoadResource() throws Exception
	{
		resource.clear();
		
		List<PartnerUpgradeResource> partnerUpgradeRS = storageContext.<PartnerUpgradeResource>getDataList(ConstantVal.STATIC_DB, "partner_upgrade_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(partnerUpgradeRS == null || partnerUpgradeRS.isEmpty())
		{
			System.out.println("PartnerUpgradeResource No data");
			System.exit(0);
		}
		
		for(PartnerUpgradeResource data : partnerUpgradeRS)
		{
			if(resource.containsKey(data.getUpgradeLevel()))
			{
				String errorMsg = "PartnerUpgradeResource Overlap data:" + data.getUpgradeLevel();
				System.out.println(errorMsg);
				System.exit(0);
			}
			
			resource.put(data.getUpgradeLevel(), data);
		}
		System.out.println("PartnerUpgradeResource Loading Complete");
	}
	
	public PartnerUpgradeResource get(int level)
	{
		if(!resource.containsKey(level))
			return null;
		
		return resource.get(level);
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("PartnerUpgradeResource Verify Complete");
	}
}
