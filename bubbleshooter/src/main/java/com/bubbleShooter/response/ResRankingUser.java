package com.bubbleShooter.response;

import java.util.List;

import com.bubbleShooter.VO.RankerVO;
import com.bubbleShooter.VO.RankingRewardVO;

public class ResRankingUser {
	public int result;
	public int season;								//시즌이면 현재시즌, 비시즌이면 이전 시즌정보
	public boolean isSeason;						//시즌중이면 true, 비시즌이면 false
	public long seasonRemainTime;					// 시즌중이면, 남은시간, 비시즌이면, 다음시즌 시작까지 남은시간
	public RankerVO userRecord;
	public List<RankerVO> rankingList;
	public List<RankingRewardVO> tierRewards;		//비시즌이면, 이전시즌 보상값
	public List<RankingRewardVO> rankingRewards;	//비시즌이면, 이전시즌 보상값
}
