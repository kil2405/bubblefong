package com.bubbleShooter.VO;

import java.util.List;

public class MailVO {
	
	public int mailId;
	public byte mailType;
	public String title;
	public String description;
	public byte state;
	public List<RewardItemVO> mailItem;
	public long receiveTime;
	public long createTime;
	public long remainTime;
}
