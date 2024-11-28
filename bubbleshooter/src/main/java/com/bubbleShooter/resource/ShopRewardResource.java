package com.bubbleShooter.resource;

public class ShopRewardResource {
	private int index;
	private int productId;
	private int itemId;
	private int itemType;
	private int itemCount;

	public ShopRewardResource(int index, int productId, int itemId, int itemType, int itemCount) {
		super();
		this.index = index;
		this.productId = productId;
		this.itemId = itemId;
		this.itemType = itemType;
		this.itemCount = itemCount;
	}

	public int getIndex() {
		return index;
	}

	public int getProductId() {
		return productId;
	}

	public int getItemId() {
		return itemId;
	}

	public int getItemType() {
		return itemType;
	}

	public int getItemCount() {
		return itemCount;
	}

}
