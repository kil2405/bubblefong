package com.bubbleShooter.response;

import java.util.List;

import com.bubbleShooter.VO.CharacterVO;
import com.bubbleShooter.VO.ItemVO;
import com.bubbleShooter.VO.MailVO;
import com.bubbleShooter.VO.PartnerVO;
import com.bubbleShooter.VO.RewardItemVO;

public class ResMailOpen {

	public int result;
	public List<RewardItemVO> rewards;
	public List<ItemVO> items;
	public List<CharacterVO> characters;
	public List<PartnerVO> partners;
	public List<MailVO> mails;
}
