package com.bubbleShooter.resource;

public class CharacterDiaResource {
	private int index;
	private int enchantLevel;
	private int rMax;
	private int srMax;
	private int ssrMax;

	public CharacterDiaResource(int index, int enchantLevel, int rMax, int srMax, int ssrMax) {
		super();
		this.index = index;
		this.enchantLevel = enchantLevel;
		this.rMax = rMax;
		this.srMax = srMax;
		this.ssrMax = ssrMax;
	}

	public int getIndex() {
		return index;
	}

	public int getEnchantLevel() {
		return enchantLevel;
	}

	public int getrMax() {
		return rMax;
	}

	public int getSrMax() {
		return srMax;
	}

	public int getSsrMax() {
		return ssrMax;
	}

}
