package com.bubbleShooter.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class GachaListComponent {
	@Autowired
	private StorageContext storageContext;
	
	private HashMap<Integer, List<GachaListResource>> resource = new HashMap<>();
	
	public void LoadResource() throws Exception
	{
		resource.clear();
		
		List<GachaListResource> gachaListRS = storageContext.<GachaListResource>getDataList(ConstantVal.STATIC_DB, "gacha_list_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(gachaListRS == null || gachaListRS.isEmpty())
		{
			System.out.println("GachaListResource No data");
			System.exit(0);
		}
		
		for(GachaListResource data : gachaListRS)
		{
			if(resource.containsKey(data.getGachaId()))
			{
				List<GachaListResource> list = resource.get(data.getGachaId());
				if(list == null)
				{
					System.out.println("GachaListResource No data");
					System.exit(0);
				}
				
				list.add(data);
			}
			else
			{
				List<GachaListResource> list = new ArrayList<>();
				list.add(data);
				
				resource.put(data.getGachaId(), list);
			}
		}
		System.out.println("GachaListResource Loading Complete");
	}
	
	public List<GachaListResource> get(int gachaId)
	{
		List<GachaListResource> value = resource.get(gachaId);
		if(value == null || value.isEmpty())
			return null;
		
		return value;
	}
	
	public List<Integer> getGradeIds(int gachaId)
	{
		List<GachaListResource> value = resource.get(gachaId);
		if(value == null || value.isEmpty())
			return null;
		
		List<Integer> gradeIds = new ArrayList<>();
		
		for(GachaListResource data : value)
		{
			for(int i = 0; i < data.getCount(); i++)
				gradeIds.add(data.getGrade());
		}
		
		return gradeIds;
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("GachaListResource Verify Complete");
	}
}
