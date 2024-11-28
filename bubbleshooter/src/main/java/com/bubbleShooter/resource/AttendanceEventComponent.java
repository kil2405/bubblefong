package com.bubbleShooter.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.VO.AttendanceRewardItemVO;
import com.bubbleShooter.VO.RewardItemVO;
import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;
import com.bubbleShooter.util.MapperVO;

@Component
public class AttendanceEventComponent {
	@Autowired
	private StorageContext storageContext;
	
	@Autowired
	private MapperVO mapperVO;
	
	private HashMap<Integer, List<AttendanceEventResource>> resource = new HashMap<>();
	private HashMap<Integer, List<AttendanceEventResource>> eventMap = new HashMap<>();
	
	public void LoadResource() throws Exception
	{
		resource.clear();
		
		List<AttendanceEventResource> attendanceEventRS = storageContext.<AttendanceEventResource>getDataList(ConstantVal.STATIC_DB, "attendance_event_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(attendanceEventRS == null || attendanceEventRS.isEmpty())
		{
			System.out.println("AttendanceEventResource No data");
			System.exit(0);
		}
		
		for(AttendanceEventResource data : attendanceEventRS)
		{
			int key = createKey(data.getEventNo(), data.getDay());
			if(resource.containsKey(key))
			{
				List<AttendanceEventResource> days = resource.get(key);
				if(days == null || days.isEmpty())
				{
					System.out.println("AttendanceEventResource No data");
					System.exit(0);
				}
				
				days.add(data);
				resource.put(key, days);
			}
			else
			{
				List<AttendanceEventResource> days = new ArrayList<>();
				days.add(data);
				resource.put(key, days);
			}
			
			if(eventMap.containsKey(data.getEventNo()))
			{
				List<AttendanceEventResource> value = eventMap.get(data.getEventNo());
				value.add(data);
			}
			else
			{
				List<AttendanceEventResource> value = new ArrayList<>();
				value.add(data);
				
				eventMap.put(data.getEventNo(), value);
			}
		}
		
		System.out.println("AttendanceEventResource Loading Complete");
	}
	
	private int createKey(int eventNo, int day)
	{
		return (eventNo * 100) + day;
	}
	
	public List<AttendanceEventResource> get(int eventNo, int day)
	{
		int key = createKey(eventNo, day);
		if(!resource.containsKey(key))
			return null;
		
		return resource.get(key);
	}
	
	public List<AttendanceRewardItemVO> AttendanceEventRewards(int eventNo)
	{
		List<AttendanceRewardItemVO> vos = new ArrayList<>();
		
		if(!eventMap.containsKey(eventNo))
			return null;
		
		HashMap<Integer, List<RewardItemVO>> rewards = new HashMap<>();
		
		List<AttendanceEventResource> events = eventMap.get(eventNo);
		
		for(AttendanceEventResource event : events)
		{
			if(rewards.containsKey(event.getDay()))
			{
				List<RewardItemVO> value = rewards.get(event.getDay());
				value.add(mapperVO.makeRewardItemVO(event.getItemType(), event.getItemId(), event.getItemCount()));
			}
			else
			{
				List<RewardItemVO> value = new ArrayList<>();
				value.add(mapperVO.makeRewardItemVO(event.getItemType(), event.getItemId(), event.getItemCount()));
				
				rewards.put(event.getDay(), value);
			}
		}
		
		for(Integer key : rewards.keySet())
		{
			AttendanceRewardItemVO itemsVO = new AttendanceRewardItemVO();
			itemsVO.rewards = new ArrayList<>();
			
			itemsVO.rewards.addAll(rewards.get(key));
			vos.add(itemsVO);
		}
		
		return vos;
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("AttendanceEventResource Verify Complete");
	}
}
