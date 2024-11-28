package com.bubbleShooter.resource;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class TextComponent {
	@Autowired
	private StorageContext storageContext;
	
	private HashMap<Integer, TextResource> resource = new HashMap<>();
	
	public void LoadResource() throws Exception
	{
		resource.clear();
		
		List<TextResource> textRS = storageContext.<TextResource>getDataList(ConstantVal.STATIC_DB, "text_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(textRS == null || textRS.isEmpty())
		{
			System.out.println("TextResource No data");
			System.exit(0);
		}
		
		for(TextResource data : textRS)
		{
			if(resource.containsKey(data.getId()))
			{
				String errorMsg = "TextResource Overlap data:" + data.getId();
				System.out.println(errorMsg);
				System.exit(0);
			}
			
			// resource에 키값에 따른 데이터 넣어주기
			resource.put(data.getId(), data);
		}
		
		System.out.println("TextResource Loading Complete");
	}
	
	public TextResource get(int id)
	{
		if(!resource.containsKey(id))
			return null;
		
		return resource.get(id);
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("TextResource Verify Complete");
	}
}
