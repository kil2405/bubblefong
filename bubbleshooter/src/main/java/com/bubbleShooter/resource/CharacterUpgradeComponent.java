package com.bubbleShooter.resource;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class CharacterUpgradeComponent {
	@Autowired
	private StorageContext storageContext;
	
	private HashMap<Integer, CharacterUpgradeResource> resource = new HashMap<>();
	
	public void LoadResource() throws Exception
	{
		resource.clear();
		
		List<CharacterUpgradeResource> characterUpgradeRS = storageContext.<CharacterUpgradeResource>getDataList(ConstantVal.STATIC_DB, "character_upgrade_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(characterUpgradeRS == null || characterUpgradeRS.isEmpty())
		{
			System.out.println("CharacterUpgradeResource No data");
			System.exit(0);
		}
		
		for(CharacterUpgradeResource data : characterUpgradeRS)
		{
			if(resource.containsKey(data.getUpgradeLevel()))
			{
				String errorMsg = "CharacterUpgradeResource Overlap data:" + data.getUpgradeLevel();
				System.out.println(errorMsg);
				System.exit(0);
			}
			
			resource.put(data.getUpgradeLevel(), data);
		}
		System.out.println("AttendanceDailyResource Loading Complete");
	}
	
	public CharacterUpgradeResource get(int level)
	{
		if(!resource.containsKey(level))
			return null;
		
		return resource.get(level);
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("AttendanceDailyResource Verify Complete");
	}
}
