package com.bubbleShooter.resource;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class SkillPartnerGroupComponent {
	@Autowired
	private StorageContext storageContext;
	
	private HashMap<Integer, SkillPartnerGroupResource> resource = new HashMap<>();
	
	public void LoadResource() throws Exception
	{
		resource.clear();
		
		List<SkillPartnerGroupResource> partnerGroupRS = storageContext.<SkillPartnerGroupResource>getDataList(ConstantVal.STATIC_DB, "skill_partner_group_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(partnerGroupRS == null || partnerGroupRS.isEmpty())
		{
			System.out.println("SkillPartnerGroupResource No data");
			System.exit(0);
		}
		
		for(SkillPartnerGroupResource data : partnerGroupRS)
		{
			if(resource.containsKey(data.getIdCharacter()))
			{
				String errorMsg = "SkillPartnerGroupResource Overlap data:" + data.getIdCharacter();
				System.out.println(errorMsg);
				System.exit(0);
			}
			
			// resource에 키값에 따른 데이터 넣어주기
			resource.put(data.getIdCharacter(), data);
		}
		
		System.out.println("SkillPartnerGroupResource Loading Complete");
	}
	
	public SkillPartnerGroupResource get(int characterId)
	{
		if(!resource.containsKey(characterId))
			return null;
		
		return resource.get(characterId);
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("SkillPartnerGroupResource Verify Complete");
	}
}
