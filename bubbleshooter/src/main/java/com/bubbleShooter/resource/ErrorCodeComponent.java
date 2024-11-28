package com.bubbleShooter.resource;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class ErrorCodeComponent {
	@Autowired
	private StorageContext storageContext;
	
	private HashMap<Integer, ErrorCodeResource> resource = new HashMap<>();
	
	public void LoadResource() throws Exception
	{
		resource.clear();
		
		List<ErrorCodeResource> errorCodeRS = storageContext.<ErrorCodeResource>getDataList(ConstantVal.STATIC_DB, "error_code_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(errorCodeRS == null || errorCodeRS.isEmpty())
		{
			System.out.println("ErrorCodeResource No data");
			System.exit(0);
		}
		
		for(ErrorCodeResource data : errorCodeRS)
		{
			if(resource.containsKey(data.getErrorCode()))
			{
				String errorMsg = "ErrorCodeResource Overlap data:" + data.getErrorCode();
				System.out.println(errorMsg);
				System.exit(0);
			}
			
			resource.put(data.getErrorCode(), data);
		}
		System.out.println("ErrorCodeResource Loading Complete");
	}
	
	public ErrorCodeResource get(int errorCode)
	{
		if(!resource.containsKey(errorCode))
			return null;
		
		return resource.get(errorCode);
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("ErrorCodeResource Verify Complete");
	}
}
