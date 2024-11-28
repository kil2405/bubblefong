package com.bubbleShooter.VO;

public class NoticeVO
{
	public String noticeTitle;
	public int type;			//type 0 : url , type 1 : 일반 text
	public String text;    
	public String imageAddress;
	public long remainTime; //공지게시까지 남은시간
	public int sortNumber;
}
