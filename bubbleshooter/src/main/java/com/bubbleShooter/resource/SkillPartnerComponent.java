package com.bubbleShooter.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class SkillPartnerComponent {
	@Autowired
	private StorageContext storageContext;
	
	private HashMap<Integer, SkillPartnerResource> resource = new HashMap<>();
	private HashMap<Integer, List<Integer>> useSlotSkills = new HashMap<>();
	
	public void LoadResource() throws Exception
	{
		resource.clear();
		
		List<SkillPartnerResource> partnerSkillRS = storageContext.<SkillPartnerResource>getDataList(ConstantVal.STATIC_DB, "skill_partner_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(partnerSkillRS == null || partnerSkillRS.isEmpty())
		{
			System.out.println("SkillPartnerResource No data");
			System.exit(0);
		}
		
		for(SkillPartnerResource data : partnerSkillRS)
		{
			if(data.getId() <= ConstantVal.DEFAULT_VALUE)
				continue;
			
			if(resource.containsKey(data.getId()))
			{
				String errorMsg = "SkillPartnerResource Overlap data:" + data.getId();
				System.out.println(errorMsg);
				System.exit(0);
			}
			resource.put(data.getId(), data);
			
			if(useSlotSkills.containsKey(data.getUseSlot()))
			{
				List<Integer> value = useSlotSkills.get(data.getUseSlot());
				value.add(data.getId());
			}
			else
			{
				List<Integer> value = new ArrayList<>();
				value.add(data.getId());
				useSlotSkills.put(data.getUseSlot(), value);
			}
		}
		System.out.println("SkillPartnerResource Loading Complete");
	}
	
	public SkillPartnerResource get(int id)
	{
		if(!resource.containsKey(id))
			return null;
		
		return resource.get(id);
	}
	
	public HashMap<Integer, List<Integer>> getSlotPartnerSkills()
	{
		HashMap<Integer, List<Integer>> destMap = new HashMap<>();
		
		List<Integer> slot1 = useSlotSkills.get(ConstantVal.CARD_GRADE_R);
		List<Integer> skill1 = new ArrayList<>();
		for(Integer i : slot1)
			skill1.add(i);
		
		destMap.put(ConstantVal.CARD_GRADE_R, skill1);
		
		List<Integer> slot2 = useSlotSkills.get(ConstantVal.CARD_GRADE_SR);
		List<Integer> skill2 = new ArrayList<>();
		for(Integer i : slot2)
			skill2.add(i);
		
		destMap.put(ConstantVal.CARD_GRADE_SR, skill2);
		
		List<Integer> slot3 = useSlotSkills.get(ConstantVal.CARD_GRADE_SSR);
		List<Integer> skill3 = new ArrayList<>();
		for(Integer i : slot3)
			skill3.add(i);
		
		destMap.put(ConstantVal.CARD_GRADE_SSR, skill3);
		
		return destMap;
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("SkillPartnerResource Verify Complete");
	}
}
