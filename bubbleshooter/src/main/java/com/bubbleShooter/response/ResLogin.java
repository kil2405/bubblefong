package com.bubbleShooter.response;

import java.util.List;

import com.bubbleShooter.VO.AccountVO;
import com.bubbleShooter.VO.AttendanceEventVO;
import com.bubbleShooter.VO.AttendanceVO;
import com.bubbleShooter.VO.CharacterVO;
import com.bubbleShooter.VO.ItemVO;
import com.bubbleShooter.VO.MissionVO;
import com.bubbleShooter.VO.PartnerVO;
import com.bubbleShooter.VO.PresetVO;
import com.bubbleShooter.VO.ProfileVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ResLogin {
	public Integer result;
	public AccountVO account;
	public List<ItemVO> items;
	public List<CharacterVO> characters;
	public List<PartnerVO> partners;
	public PresetVO preset;
	public String patchHost;
	public String encryption;
	public Boolean dayChanged;
	public Boolean isEncryption;
	public AttendanceVO attendance;
	public List<AttendanceEventVO> events;
	public List<MissionVO> missions;
	public List<ProfileVO> profiles;
	public Byte isNewUser;
	public String masterServerIp;
	public Integer masterServerPort;
}
