package com.bubbleShooter.resource;

public class SynthesisitemResource
{
    private int index;
    private byte itemType;
    private int itemId;
    private int count;
    private int itemRate;
    private byte rewardType;
    private int rewardItem;
    private int rewardCount;
    private int gRate;
    private byte gItemType;
    private int gItem;
    private int gCount;

    public SynthesisitemResource(int index, byte itemType, int itemId, int count, int itemRate, byte rewardType, int rewardItem, int rewardCount, int gRate, byte gItemType, int gItem, int gCount)
    {
        this.index = index;
        this.itemType = itemType;
        this.itemId = itemId;
        this.count = count;
        this.itemRate = itemRate;
        this.rewardType = rewardType;
        this.rewardItem = rewardItem;
        this.rewardCount = rewardCount;
        this.gRate = gRate;
        this.gItemType = gItemType;
        this.gItem = gItem;
        this.gCount = gCount;
    }

    public int getIndex()
    {
        return index;
    }

    public byte getItemType()
    {
        return itemType;
    }

    public int getItemId()
    {
        return itemId;
    }

    public int getCount()
    {
        return count;
    }

    public int getItemRate()
    {
        return itemRate;
    }

    public byte getRewardType()
    {
        return rewardType;
    }

    public int getRewardItem()
    {
        return rewardItem;
    }

    public int getRewardCount()
    {
        return rewardCount;
    }

    public int getgRate()
    {
        return gRate;
    }

    public byte getgItemType()
    {
        return gItemType;
    }

    public int getgItem()
    {
        return gItem;
    }

    public int getgCount()
    {
        return gCount;
    }
}
