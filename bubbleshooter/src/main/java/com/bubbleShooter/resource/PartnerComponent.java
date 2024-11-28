package com.bubbleShooter.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class PartnerComponent {
	@Autowired
	private StorageContext storageContext;
	
	private HashMap<Integer, PartnerResource> resource = new HashMap<>();
	private List<PartnerResource> partners = new ArrayList<>();
	
	public void LoadResource() throws Exception
	{
		List<PartnerResource> partnerRS = storageContext.<PartnerResource>getDataList(ConstantVal.STATIC_DB, "partner_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(partnerRS == null || partnerRS.isEmpty())
		{
			System.out.println("PartnerResource No data");
			System.exit(0);
		}
		
		for(PartnerResource data : partnerRS)
		{
			if(resource.containsKey(data.getId()))
			{
				String errorMsg = "PartnerResource Overlap data:" + data.getId();
				System.out.println(errorMsg);
				System.exit(0);
			}
			
			resource.put(data.getId(), data);
			partners.add(data);
		}
		System.out.println("PartnerResource Loading Complete");
	}
	
	public PartnerResource get(int id)
	{
		if(!resource.containsKey(id))
			return null;
		
		return resource.get(id);
	}
	
	public List<PartnerResource> getPartners()
	{
		return partners;
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("PartnerResource Verify Complete");
	}
}
