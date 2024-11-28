package com.bubbleShooter.resource;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class CharacterDiaComponent {
	@Autowired
	private StorageContext storageContext;
	
	private HashMap<Integer, CharacterDiaResource> resource = new HashMap<>();
	
	public void LoadResource() throws Exception
	{
		resource.clear();
		
		List<CharacterDiaResource> characterDiaRS = storageContext.<CharacterDiaResource>getDataList(ConstantVal.STATIC_DB, "character_dia_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(characterDiaRS == null || characterDiaRS.isEmpty())
		{
			System.out.println("CharacterDiaResource No data");
			System.exit(0);
		}
		
		for(CharacterDiaResource data : characterDiaRS)
		{
			if(resource.containsKey(data.getEnchantLevel()))
			{
				String errorMsg = "CharacterDiaResource Overlap data:" + data.getEnchantLevel();
				System.out.println(errorMsg);
				System.exit(0);
			}
			
			resource.put(data.getEnchantLevel(), data);
		}
		
		System.out.println("CharacterDiaResource Loading Complete");
	}
	
	public CharacterDiaResource get(int level)
	{
		if(!resource.containsKey(level))
			return null;
		
		return resource.get(level);
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("CharacterDiaResource Verify Complete");
	}
}
