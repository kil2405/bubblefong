package com.bubbleShooter.resource;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class SynthesisitemComponent
{
    @Autowired
    private StorageContext storageContext;

    private HashMap<Integer, SynthesisitemResource> resource = new HashMap<>();

    private int CreateKey(int itemId, int count)
    {
        return (itemId << 16) | count;
    }

    public void LoadResource() throws Exception
    {
        resource.clear();

        List<SynthesisitemResource> synthesisitems = storageContext.<SynthesisitemResource>getDataList(ConstantVal.STATIC_DB, "synthesisitem_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
        {
            if(synthesisitems == null || synthesisitems.isEmpty())
            {
                System.out.println("SynthesisitemResource No data");
                System.exit(0);
            }
        }

        for(SynthesisitemResource synthesisitem : synthesisitems)
        {
            int key = CreateKey(synthesisitem.getItemId(), synthesisitem.getCount());
            if(resource.containsKey(key))
            {
                String errorMsg = "SynthesisitemResource Overlap data:" + key;
                System.out.println(errorMsg);
                System.exit(0);
            }

            resource.put(key, synthesisitem);
        }

        System.out.println("EventResource Loading Complete");
    }

    public SynthesisitemResource get(int itemId, int count) throws Exception
    {
        int key = CreateKey(itemId, count);
        if(!resource.containsKey(key))
            return null;

        return resource.get(key);
    }

    public void VerifyResource() throws Exception
	{
		System.out.println("EventResource Verify Complete");
	}
}
