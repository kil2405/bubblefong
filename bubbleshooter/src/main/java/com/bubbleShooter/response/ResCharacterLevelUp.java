package com.bubbleShooter.response;

import java.util.List;

import com.bubbleShooter.VO.CharacterVO;
import com.bubbleShooter.VO.ItemVO;

public class ResCharacterLevelUp {
	public int result;
	public String characterUid;
	public List<CharacterVO> characters;
	public List<ItemVO> items;
}
