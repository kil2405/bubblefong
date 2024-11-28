package com.bubbleShooter.resource;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class CharacterLevelComponent {
	@Autowired
	private StorageContext storageContext;
	
	private HashMap<Integer, CharacterLevelResource> resource = new HashMap<>();
	
	public void LoadResource() throws Exception
	{
		resource.clear();
		
		List<CharacterLevelResource> characterLevelRS = storageContext.<CharacterLevelResource>getDataList(ConstantVal.STATIC_DB, "character_level_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(characterLevelRS == null || characterLevelRS.isEmpty())
		{
			System.out.println("CharacterLevelResource No data");
			System.exit(0);
		}
		
		for(CharacterLevelResource data : characterLevelRS)
		{
			int key = createKey(data.getIsNft(), data.getLevel(), data.getGrade());
			
			if(resource.containsKey(key))
			{
				String errorMsg = "CharacterLevelResource Overlap data:" + key;
				System.out.println(errorMsg);
				System.exit(0);
			}
			
			resource.put(key, data);
		}
		
		System.out.println("CharacterLevelResource Loading Complete");
	}
	
	private int createKey(byte isNft, int level, byte grade)
	{
		return (isNft * 1000) + (grade * 100) + level;
	}
	
	public CharacterLevelResource get(byte isNft, int level, byte grade)
	{
		int key = createKey(isNft, level, grade);
		
		return resource.get(key);
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("CharacterLevelResource Verify Complete");
	}
}
