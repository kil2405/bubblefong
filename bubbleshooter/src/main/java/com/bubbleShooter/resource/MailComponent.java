package com.bubbleShooter.resource;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;
import com.bubbleShooter.relation.MailTitle;

@Component
public class MailComponent {
	@Autowired
	private StorageContext storageContext;
	
	private HashMap<Integer, MailResource> resource = new HashMap<>();
	
	public void LoadResource() throws Exception
	{
		resource.clear();
		
		List<MailResource> mailRS = storageContext.<MailResource>getDataList(ConstantVal.STATIC_DB, "mail_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(mailRS == null || mailRS.isEmpty())
		{
			System.out.println("MailResource No data");
			System.exit(0);
		}
		
		for(MailResource data : mailRS)
		{
			if(resource.containsKey(data.getIndex()))
			{
				String errorMsg = "MailResource Overlap data:" + data.getIndex();
				System.out.println(errorMsg);
				System.exit(0);
			}
			
			resource.put(data.getIndex(), data);
		}
		System.out.println("MailResource Loading Complete");
	}
	
	public MailTitle get(int index, String language)
	{
		if(!resource.containsKey(index))
			return null;

		MailResource mailResource = resource.get(index);

		if(language.equals("Korean"))
			return new MailTitle(mailResource.getMailTitleKr(), mailResource.getMailContentsKr());
		else
			return new MailTitle(mailResource.getMailTitleEn(), mailResource.getMailContentsEn());
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("MailResource Verify Complete");
	}
}
