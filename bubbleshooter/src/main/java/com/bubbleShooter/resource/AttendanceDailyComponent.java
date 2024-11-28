package com.bubbleShooter.resource;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class AttendanceDailyComponent {
	@Autowired
	private StorageContext storageContext;
	
	private HashMap<Integer, AttendanceDailyResource> resource = new HashMap<>();
	private int count = 0;
	
	public void LoadResource() throws Exception
	{
		resource.clear();
		
		List<AttendanceDailyResource> attendanceDailyRS = storageContext.<AttendanceDailyResource>getDataList(ConstantVal.STATIC_DB, "attendance_daily_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(attendanceDailyRS == null || attendanceDailyRS.isEmpty())
		{
			System.out.println("AttendanceDailyResource No data");
			System.exit(0);
		}
		
		for(AttendanceDailyResource data : attendanceDailyRS)
		{
			if(resource.containsKey(data.getDays()))
			{
				String errorMsg = "AttendanceDailyResource Overlap data:" + data.getDays();
				System.out.println(errorMsg);
				System.exit(0);
			}
			
			// resource에 키값에 따른 데이터 넣어주기
			resource.put(data.getDays(), data);
			
			if(data.getDays() != 0)
				count++;
		}
		
		System.out.println("AttendanceDailyResource Loading Complete");
	}
	
	public AttendanceDailyResource get(int day)
	{
		if(!resource.containsKey(day))
			return null;
		
		return resource.get(day);
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("AttendanceDailyResource Verify Complete");
	}
	
	public int getAttendanceMaxCount()
	{
		return count;
	}
}
