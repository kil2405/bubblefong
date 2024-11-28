package com.bubbleShooter.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class UserExpComponent {
	@Autowired
	private StorageContext storageContext;
	
	private HashMap<Integer, UserExpResource> resource = new HashMap<>();
	private List<UserExpResource> expList = new ArrayList<>();
	
	public void LoadResource() throws Exception
	{
		resource.clear();
		
		List<UserExpResource> userExpRS = storageContext.<UserExpResource>getDataList(ConstantVal.STATIC_DB, "user_exp_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(userExpRS == null || userExpRS.isEmpty())
		{
			System.out.println("UserExpResource No data");
			System.exit(0);
		}
		
		for(UserExpResource data : userExpRS)
		{
			if(resource.containsKey(data.getNumber()))
			{
				String errorMsg = "UserExpResource Overlap data:" + data.getNumber();
				System.out.println(errorMsg);
				System.exit(0);
			}
			
			if(data.getNumber() <= ConstantVal.DEFAULT_VALUE)
				continue;
			
			// resource에 키값에 따른 데이터 넣어주기
			resource.put(data.getNumber(), data);
			expList.add(data);
		}
		
		System.out.println("UserExpResource Loading Complete");
	}
	
	public UserExpResource get(int level)
	{
		if(!resource.containsKey(level))
			return null;
		
		return resource.get(level);
	}
	
	public UserExpResource calculation(int exp)
	{
		if(exp < ConstantVal.DEFAULT_ZERO)
			return null;
		
		UserExpResource prevExpRS = null;
		for(UserExpResource e : expList)
		{
			if(e.getNumber() >= ConstantVal.USER_ACCOUNT_MAX_LEVEL)
				return e;
			
			if(exp >= e.getValue())
				prevExpRS = e;
			else
				break;
		}
		
		return prevExpRS;
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("UserExpResource Verify Complete");
	}
}
