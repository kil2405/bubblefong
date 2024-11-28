package com.bubbleShooter.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class SkillCharacterActiveComponent {
	@Autowired
	private StorageContext storageContext;
	
	private HashMap<Integer, List<SkillCharacterActiveResource>> resource = new HashMap<>();
	
	public void LoadResource() throws Exception
	{
		resource.clear();
		
		List<SkillCharacterActiveResource> activeRS = storageContext.<SkillCharacterActiveResource>getDataList(ConstantVal.STATIC_DB, "skill_character_active_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(activeRS == null || activeRS.isEmpty())
		{
			System.out.println("SkillCharacterActiveResource No data");
			System.exit(0);
		}
		
		for(SkillCharacterActiveResource data : activeRS)
		{
			if(resource.containsKey(data.getId()))
			{
				List<SkillCharacterActiveResource> value = resource.get(data.getId());
				if(value == null || value.isEmpty())
				{
					System.out.println("SkillCharacterActiveResource No data");
					System.exit(0);
				}
				
				value.add(data);
				resource.put(data.getId(), value);
			}
			else
			{
				List<SkillCharacterActiveResource> value = new ArrayList<>();
				value.add(data);
				resource.put(data.getId(), value);
			}
		}
		
		System.out.println("SkillCharacterActiveResource Loading Complete");
	}
	
	public SkillCharacterActiveResource get(int id, int level)
	{
		if(!resource.containsKey(id))
			return null;
		
		List<SkillCharacterActiveResource> value = resource.get(id);
		if(value == null || value.isEmpty())
			return null;
		
		for(SkillCharacterActiveResource data : value)
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
		System.out.println("SkillCharacterActiveResource Verify Complete");
	}
}
