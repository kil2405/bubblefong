package com.bubbleShooter.resource;

public class CharacterUpgradeResource {
	private int index;
	private int upgradeLevel;
	private int upgradeUnlock;

	public CharacterUpgradeResource(int index, int upgradeLevel, int upgradeUnlock) {
		super();
		this.index = index;
		this.upgradeLevel = upgradeLevel;
		this.upgradeUnlock = upgradeUnlock;
	}

	public int getIndex() {
		return index;
	}

	public int getUpgradeLevel() {
		return upgradeLevel;
	}

	public int getUpgradeUnlock() {
		return upgradeUnlock;
	}
	
}
