package com.bubbleShooter.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class MissionComponent {
	@Autowired
	private StorageContext storageContext;
	
	private HashMap<Integer, MissionResource> resource = new HashMap<>();
	private List<MissionResource> missionList = new ArrayList<>();
	
	public void LoadResource() throws Exception
	{
		resource.clear();
		
		List<MissionResource> missionRS = storageContext.<MissionResource>getDataList(ConstantVal.STATIC_DB, "mission_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(missionRS == null || missionRS.isEmpty())
		{
			System.out.println("MissionResource No data");
			System.exit(0);
		}
		
		for(MissionResource mission : missionRS)
		{
			if(resource.containsKey(mission.getMissionId()))
			{
				String errorMsg = "MissionResource Overlap id:" + mission.getMissionId();
				System.out.println(errorMsg);
				System.exit(0);
			}
			
			resource.put(mission.getMissionId(), mission);
			missionList.add(mission);
		}
		
		System.out.println("MissionResource Loading Complete");
	}
	
	public MissionResource get(int missionId)
	{
		if(!resource.containsKey(missionId))
			return null;
		
		return resource.get(missionId);
	}
	
	public List<Integer> getAllMissionIds()
	{
		List<Integer> destList = new ArrayList<>();
		
		for(MissionResource mission : missionList)
		{
			destList.add(mission.getMissionId());
		}
		
		return destList;
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("MissionResource Verify Complete");
	}
}
