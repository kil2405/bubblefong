package com.bubbleShooter.VO;

import java.util.List;

public class AttendanceEventVO {
	public int index;
	public int startDay;
	public int endDay;
	public int attendanceDay;
	public int eventTitle;
	public byte uiType;
	public List<AttendanceRewardItemVO> attendanceBoard;
	public boolean isReward = false;
}
