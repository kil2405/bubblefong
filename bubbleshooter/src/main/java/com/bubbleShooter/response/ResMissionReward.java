package com.bubbleShooter.response;

import java.util.List;

import com.bubbleShooter.VO.CharacterVO;
import com.bubbleShooter.VO.MailVO;
import com.bubbleShooter.VO.MissionVO;
import com.bubbleShooter.VO.PartnerVO;
import com.bubbleShooter.VO.RewardItemVO;

public class ResMissionReward {
	public int result;
	public List<CharacterVO> characters;
	public List<PartnerVO> partners;
	public List<MissionVO> missions;
	public List<RewardItemVO> basicRewards;
	public List<RewardItemVO> grandRewards;
	public List<MailVO> mails;
}
