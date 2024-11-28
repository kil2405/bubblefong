package com.bubbleShooter.resource;

public class GameRewardResource {
	private int index;
	private byte gameMode;
	private int rankFrom;
	private int rankTo;
	private int itemId0;
	private int itemType0;
	private int itemCount0;

	public GameRewardResource(int index, byte gameMode, int rankFrom, int rankTo, int itemId0, int itemType0,
			int itemCount0) {
		super();
		this.index = index;
		this.gameMode = gameMode;
		this.rankFrom = rankFrom;
		this.rankTo = rankTo;
		this.itemId0 = itemId0;
		this.itemType0 = itemType0;
		this.itemCount0 = itemCount0;
	}

	public int getIndex() {
		return index;
	}

	public byte getGameMode() {
		return gameMode;
	}

	public int getRankFrom() {
		return rankFrom;
	}

	public int getRankTo() {
		return rankTo;
	}

	public int getItemId0() {
		return itemId0;
	}

	public int getItemType0() {
		return itemType0;
	}

	public int getItemCount0() {
		return itemCount0;
	}

}
