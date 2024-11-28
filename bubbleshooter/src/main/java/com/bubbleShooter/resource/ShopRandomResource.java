package com.bubbleShooter.resource;

public class ShopRandomResource {
	private int id;
	private int randomId;
	private int itemId;
	private int itemType;
	private int rate;
	private int minCount;
	private int maxCount;
	private byte heroGrade;
	private String desc;

	public ShopRandomResource(int id, int randomId, int itemId, int itemType, int rate, int minCount, int maxCount,
			byte heroGrade, String desc) {
		super();
		this.id = id;
		this.randomId = randomId;
		this.itemId = itemId;
		this.itemType = itemType;
		this.rate = rate;
		this.minCount = minCount;
		this.maxCount = maxCount;
		this.heroGrade = heroGrade;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public int getRandomId() {
		return randomId;
	}

	public int getItemId() {
		return itemId;
	}

	public int getItemType() {
		return itemType;
	}

	public int getRate() {
		return rate;
	}

	public int getMinCount() {
		return minCount;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public byte getHeroGrade() {
		return heroGrade;
	}

	public String getDesc() {
		return desc;
	}

}
