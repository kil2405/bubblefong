package com.bubbleShooter.VO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ShopVO {
	public int productId;
	public byte category;
	public int productName;
	public String productIcon;
	public List<Byte> tag;
	public byte isTicket;
	public int ticketId;
	public byte priceItemType;
	public int priceItemId;
	public int price;
	public int discountRate;
	public int discountPrice;
	public byte sellType;
	public int buyLimit;
	public int buyCount;
	public long sellStartDate;
	public long sellEndDate;
	public long remainTime;
	public String gatchaType;
	public byte sortNumber;
	public List<RewardItemVO> rewards;
}
