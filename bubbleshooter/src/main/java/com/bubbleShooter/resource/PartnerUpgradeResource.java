package com.bubbleShooter.resource;

public class PartnerUpgradeResource {
	private int id;
	private int upgradeLevel;
	private int levelUnlock;

	public PartnerUpgradeResource(int id, int upgradeLevel, int levelUnlock) {
		super();
		this.id = id;
		this.upgradeLevel = upgradeLevel;
		this.levelUnlock = levelUnlock;
	}

	public int getId() {
		return id;
	}

	public int getUpgradeLevel() {
		return upgradeLevel;
	}

	public int getLevelUnlock() {
		return levelUnlock;
	}

}
