package com.bubbleShooter.response;

import java.util.List;

import com.bubbleShooter.VO.GachaRewardVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ResGachaList {
	public int result;
	public String message;
	public long remainTime;
	public List<GachaRewardVO> rewards;
}
