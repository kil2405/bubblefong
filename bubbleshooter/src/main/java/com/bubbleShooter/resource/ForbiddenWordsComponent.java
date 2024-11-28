package com.bubbleShooter.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class ForbiddenWordsComponent {
	@Autowired
	private StorageContext storageContext;
	
	private HashMap<String, List<ForbiddenWordsResource>> resource = new HashMap<>();
	
	public void LoadResource() throws Exception
	{
		resource.clear();
		
		List<ForbiddenWordsResource> wordsRS = storageContext.<ForbiddenWordsResource>getDataList(ConstantVal.STATIC_DB, "forbidden_words_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		{
			if(wordsRS == null || wordsRS.isEmpty())
			{
				System.out.println("ForbiddenWordsResource No data");
				System.exit(0);
			}
		}
		
		for(ForbiddenWordsResource word : wordsRS)
		{
			if(resource.containsKey(word.getLanguage()))
			{
				List<ForbiddenWordsResource> value = resource.get(word.getLanguage());
				if(value == null || value.isEmpty())
				{
					String errorMsg = "ForbiddenWordsResource Empty data:" + word.getLanguage();
					System.out.println(errorMsg);
					System.exit(0);
				}
				
				value.add(word);
				resource.put(word.getLanguage(), value);
			}
			else
			{
				List<ForbiddenWordsResource> value = new ArrayList<>();
				value.add(word);
				resource.put(word.getLanguage(), value);
			}
		}
		
		System.out.println("ForbiddenWordsResource Loading Complete");
	}
	
	public List<ForbiddenWordsResource> get(String language)
	{
		if(!resource.containsKey(language))
			return null;
		
		return resource.get(language);
	}
	
	/*금칙어 확인
	language : 사용자 언어
	message : 금칙어 체크 단어
	flag : 해당 단어가 문조건 문자일 경우 true */
	public boolean IsValidString(String language, String message, boolean flag) throws Exception
	{
		StringBuilder str = new StringBuilder();
        for (int i = 0; i < message.length(); ++i) 
        {
            char c = message.charAt(i);
            
            // 숫자, 영어(대문자, 소문자)가 아니라면 확인
            if (c < 48 || (c > 57 && c < 65) || (c > 90 && c < 97) || c > 122)
            {
            	if(language.equals("Korean"))
            	{
            		// 한국어
            		if (!(c >= 0xAC00 && c<= 0xD7AF))
                        continue;
            	}
            }
            
            str.append(c);
        }
        
        // 금칙어가 비어 있는지 여부에 따른 검사. 
        if(str.length() == 0)
        {
        	// flag가  true라면 무조건 문자로 이루어진 금칙어가 있어야 한다. (닉네임 같은 필수 이름)
        	if(flag)
        		return false;
        	
        	return true;
        }
        
        // 해당 언어의 금칙어 확인 - 금칙어가 없으면 검사하지 않는다.
        List<ForbiddenWordsResource> forbiddenWords = resource.get(language.toLowerCase());
        if(forbiddenWords != null)
        {
        	for(ForbiddenWordsResource data : forbiddenWords)
            {
                if(data == null)
                    return false;
                
                if(str.toString().toLowerCase().contains(data.getText().toLowerCase()))
                	return false;
            }
        }
        
        // 기본 언어 금칙어 확인
        if(!language.equals("en"))
        {
        	forbiddenWords = resource.get("en");
        	if(forbiddenWords != null)
        	{
        		for(ForbiddenWordsResource data : forbiddenWords)
                {
        			if(data.getText() == null)
        				continue;
        			
                    if(str.toString().toLowerCase().contains(data.getText().toLowerCase()))
                    	return false;
                }
        	}
        }
        
        return true;
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("ForbiddenWordsResource Verify Complete");
	}
}
