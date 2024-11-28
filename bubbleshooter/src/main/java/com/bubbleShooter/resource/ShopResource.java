package com.bubbleShooter.resource;

public class ShopResource {
	private int id;
	private int productId;
	private int category;
	private int productName;
	private String productIcon;
	private String tag;
	private int isTicket;
	private int ticketId;
	private int priceItemType;
	private int priceItemId;
	private int price;
	private int discountRate;
	private int discountPrice;
	private int sellType;
	private int buyLimit;
	private long sellStartDate;
	private long sellEndDate;
	private String gachaType;
	private int sortNumber;
	private String desc;

	public ShopResource(int id, int productId, int category, int productName, String productIcon, String tag,
			int isTicket, int ticketId, int priceItemType, int priceItemId, int price, int discountRate,
			int discountPrice, int sellType, int buyLimit, long sellStartDate, long sellEndDate, String gachaType,
			int sortNumber, String desc) {
		super();
		this.id = id;
		this.productId = productId;
		this.category = category;
		this.productName = productName;
		this.productIcon = productIcon;
		this.tag = tag;
		this.isTicket = isTicket;
		this.ticketId = ticketId;
		this.priceItemType = priceItemType;
		this.priceItemId = priceItemId;
		this.price = price;
		this.discountRate = discountRate;
		this.discountPrice = discountPrice;
		this.sellType = sellType;
		this.buyLimit = buyLimit;
		this.sellStartDate = sellStartDate;
		this.sellEndDate = sellEndDate;
		this.gachaType = gachaType;
		this.sortNumber = sortNumber;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public int getProductId() {
		return productId;
	}

	public int getCategory() {
		return category;
	}

	public int getProductName() {
		return productName;
	}

	public String getProductIcon() {
		return productIcon;
	}

	public String getTag() {
		return tag;
	}

	public int getIsTicket() {
		return isTicket;
	}

	public int getTicketId() {
		return ticketId;
	}

	public int getPriceItemType() {
		return priceItemType;
	}

	public int getPriceItemId() {
		return priceItemId;
	}

	public int getPrice() {
		return price;
	}

	public int getDiscountRate() {
		return discountRate;
	}

	public int getDiscountPrice() {
		return discountPrice;
	}

	public int getSellType() {
		return sellType;
	}

	public int getBuyLimit() {
		return buyLimit;
	}

	public long getSellStartDate() {
		return sellStartDate;
	}

	public long getSellEndDate() {
		return sellEndDate;
	}

	public String getGachaType() {
		return gachaType;
	}

	public int getSortNumber() {
		return sortNumber;
	}

	public String getDesc() {
		return desc;
	}
}
