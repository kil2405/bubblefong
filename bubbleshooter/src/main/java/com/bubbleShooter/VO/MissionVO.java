package com.bubbleShooter.VO;

import java.util.List;

public class MissionVO {
	public int missionId;
	public int difficulty;
	public int missionTitleId;
	public int missionDetail;
	public byte heroType;
	public int heroId;
	public int heroGrade;
	public int heroLevel;
	public int time;
	public List<RewardItemVO> basicReward;
	public List<RewardItemVO> grandReward;
	public byte status; //0:기본상태, 1: 진행중, 2:보상받을 수 있는 상태
	public byte useHeroType;
	public String uuid;
	public int remainTime;
}
