package com.bubbleShooter.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class RewardItemVO {
	public int itemId;
	public int itemType;
	public int itemCount;
	public Integer grade = null;
	public Integer level = null;
	public Integer upGrade = null;
	public Integer skill1 = null;
	public Integer skill2 = null;
	public Integer skill3 = null;
}
