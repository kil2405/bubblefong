package com.bubbleShooter.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class CharacterComponent {
	@Autowired
	private StorageContext storageContext;
	
	private HashMap<Integer, CharacterResource> resource = new HashMap<>();
	private List<CharacterResource> characters = new ArrayList<>();
	
	public void LoadResource() throws Exception
	{
		resource.clear();
		
		List<CharacterResource> characterRS = storageContext.<CharacterResource>getDataList(ConstantVal.STATIC_DB, "character_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(characterRS == null || characterRS.isEmpty())
		{
			System.out.println("CharacterResource No data");
			System.exit(0);
		}
		
		for(CharacterResource data : characterRS)
		{
			if(data.getId() <= ConstantVal.DEFAULT_VALUE)
				continue;
			
			if(resource.containsKey(data.getId()))
			{
				String errorMsg = "CharacterResource Overlap data:" + data.getId();
				System.out.println(errorMsg);
				System.exit(0);
			}
			
			resource.put(data.getId(), data);
			characters.add(data);
		}
		System.out.println("CharacterResource Loading Complete");
	}
	
	public CharacterResource get(int id)
	{
		if(!resource.containsKey(id))
			return null;
		
		return resource.get(id);
	}
	
	public List<CharacterResource> getCharacters()
	{
		return characters;
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("CharacterResource Verify Complete");
	}
	
}
