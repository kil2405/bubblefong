package com.bubbleShooter.response;

import java.util.List;

import com.bubbleShooter.VO.CharacterVO;
import com.bubbleShooter.VO.PartnerVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ResNftCardExport {
	public String result;
	public String code;
	public String message;
	public String unique;
	public String hash;
	public List<CharacterVO> characters = null;
	public List<PartnerVO> partners = null;
}
