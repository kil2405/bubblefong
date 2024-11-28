package com.bubbleShooter.response;

import java.util.List;

import com.bubbleShooter.VO.GachaRewardVO;
import com.bubbleShooter.VO.ItemVO;
import com.bubbleShooter.VO.MailVO;
import com.bubbleShooter.VO.RewardItemVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ResGachaPick {
	public int result;								//결과 100 : 성공, 이외에 Exception
	public String message;
	public int rewardGrade;							//뽑기 당첨 등수 (1 ~ 5등)
	public RewardItemVO rewards;					//뽑기 당첨아이템 (itemType, itemId, itemCount, grade)
	public List<GachaRewardVO> gachaRewardBoard;	//뽑기 보드판
	public long remainTime;							//뽑기이벤트 남은시간
	public List<ItemVO> items;						//유저 아이템 정보
	public List<MailVO> mails;						//유저 메일 정보
	
}
