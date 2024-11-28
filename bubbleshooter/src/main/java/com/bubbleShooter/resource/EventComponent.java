package com.bubbleShooter.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class EventComponent {
	@Autowired
	private StorageContext storageContext;
	
	private HashMap<Integer, EventResource> resource = new HashMap<>();
	private List<EventResource> eventList = new ArrayList<>();
	
	public void LoadResource() throws Exception
	{
		resource.clear();
		
		List<EventResource> eventRS = storageContext.<EventResource>getDataList(ConstantVal.STATIC_DB, "event_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		{
			if(eventRS == null || eventRS.isEmpty())
			{
				System.out.println("EventResource No data");
				System.exit(0);
			}
		}
		
		for(EventResource event : eventRS)
		{
			if(resource.containsKey(event.getEventNo()))
			{
				String errorMsg = "EventResource Overlap data:" + event.getEventNo();
				System.out.println(errorMsg);
				System.exit(0);
			}
			
			resource.put(event.getEventNo(), event);
			eventList.add(event);
		}
		
		System.out.println("EventResource Loading Complete");
	}
	
	public EventResource get(int eventNo) throws Exception
	{
		if(!resource.containsKey(eventNo))
			return null;
		
		return resource.get(eventNo);
	}
	
	public List<EventResource> get() throws Exception
	{
		return eventList;
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("EventResource Verify Complete");
	}
}
