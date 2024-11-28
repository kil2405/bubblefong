package com.bubbleShooter.relation;

import java.util.List;

import com.bubbleShooter.VO.RewardItemVO;

public class MailObject {
	public int mailId;
	public byte mailType;
	public int userId;
	public int mailIdVerify;
	public String title;
	public String description;
	public List<RewardItemVO> items;
	public int state;
	public long expiredTime;
	public long openTime;
	public long receiveTime;
	public long remainTime;
	public byte is_expired;
	public byte is_new;
}
