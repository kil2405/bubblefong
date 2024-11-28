package com.bubbleShooter.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class PartnerLevelComponent {
	@Autowired
	private StorageContext storageContext;
	
	private HashMap<Integer, List<PartnerLevelResource>> resource = new HashMap<>();
	
	public void LoadResource() throws Exception
	{
		List<PartnerLevelResource> partnerLevelRS = storageContext.<PartnerLevelResource>getDataList(ConstantVal.STATIC_DB, "partner_level_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(partnerLevelRS == null || partnerLevelRS.isEmpty())
		{
			System.out.println("PartnerLevelResource No data");
			System.exit(0);
		}
		
		for(PartnerLevelResource data : partnerLevelRS)
		{
			int key = createKey(data.getGrade(), data.getLevel());
			
			if(resource.containsKey(key))
			{
				List<PartnerLevelResource> level = resource.get(key);
				if(level == null || level.isEmpty())
				{
					System.out.println("PartnerLevelResource No data");
					System.exit(0);
				}
				
				PartnerLevelResource temp = new PartnerLevelResource(data.getIndex(), data.getLevel(), data.getGrade(), data.getItemId0(), data.getItemType0(), data.getItemCount0(), data.getItemId1(), data.getItemType1(), data.getItemCount1());
				level.add(temp);
				
				resource.put(key, level);
			}
			else
			{
				List<PartnerLevelResource> level = new ArrayList<>();
				
				PartnerLevelResource temp = new PartnerLevelResource(data.getIndex(), data.getLevel(), data.getGrade(), data.getItemId0(), data.getItemType0(), data.getItemCount0(), data.getItemId1(), data.getItemType1(), data.getItemCount1());
				level.add(temp);
				
				resource.put(key, level);
			}
		}
		System.out.println("PartnerLevelResource Loading Complete");
	}
	
	private int createKey(int grade, int level)
	{
		return (grade * 100) + level;
	}
	
	public PartnerLevelResource get(int grade, int level)
	{
		int key = createKey(grade, level);
		if(!resource.containsKey(key))
			return null;
		
		List<PartnerLevelResource> partnerLevel = resource.get(key);
		if(partnerLevel == null || partnerLevel.isEmpty())
			return null;
		
		for(PartnerLevelResource data : partnerLevel)
		{
			if(data == null)
				continue;
			
			if(data.getLevel() == level)
				return data;
		}
		
		return null;
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("PartnerLevelResource Verify Complete");
	}
}
