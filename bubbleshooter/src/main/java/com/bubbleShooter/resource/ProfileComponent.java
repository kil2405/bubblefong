package com.bubbleShooter.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class ProfileComponent {
    @Autowired
    private StorageContext storageContext;

    private HashMap<Integer, ProfileResource> resource = new HashMap<>();
    
    public void LoadResource() throws Exception
    {
        resource.clear();
        
        List<ProfileResource> profileRS = storageContext.<ProfileResource>getDataList(ConstantVal.STATIC_DB, "profile_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
        if(profileRS == null || profileRS.isEmpty())
        {
            System.out.println("ProfileResource No data");
            System.exit(0);
        }
        
        for(ProfileResource data : profileRS)
        {
            if(resource.containsKey(data.getId()))
            {
                String errorMsg = "ProfileResource Overlap data:" + data.getId();
                System.out.println(errorMsg);
                System.exit(0);
            }
            
            resource.put(data.getId(), data);
        }

        System.out.println("ProfileResource Loading Complete");
    }
    
    public ProfileResource get(int id)
    {
        if(!resource.containsKey(id))
            return null;
        
        return resource.get(id);
    }

    public ProfileResource getCharacterId(int characterId)
    {
        Set<Integer> keySet = resource.keySet();
        for(Integer key : keySet)
        {
            ProfileResource profile = resource.get(key);
            if(profile.getIdCharacter() == characterId)
            {
                return profile;
            }
        }
        return null;
    }

    public List<ProfileResource> getProfileList()
    {
        return new ArrayList<>(resource.values());
    }

    public void VerifyResource() throws Exception
    {
        System.out.println("ProfileResource Verify Complete");
    }
}
