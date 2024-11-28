package com.bubbleShooter.response;

import java.util.List;

import com.bubbleShooter.VO.CharacterVO;
import com.bubbleShooter.VO.MissionVO;
import com.bubbleShooter.VO.PartnerVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ResMissionStart {
	public int result;
	public String message;
	public List<CharacterVO> characters;
	public List<PartnerVO> partners;
	public List<MissionVO> missions;
}
