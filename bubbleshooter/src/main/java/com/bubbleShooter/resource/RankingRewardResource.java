package com.bubbleShooter.resource;

public class RankingRewardResource {
	private byte korCheck;
	private int rankingSeasonId;
	private int rewardType;
	private int fromTier;
	private int toTier;
	private int itemId;
	private int itemType;
	private int itemCount;

	public RankingRewardResource(byte korCheck, int rankingSeasonId, int rewardType, int fromTier, int toTier, int itemId,
			int itemType, int itemCount) {
		super();
		this.korCheck = korCheck;
		this.rankingSeasonId = rankingSeasonId;
		this.rewardType = rewardType;
		this.fromTier = fromTier;
		this.toTier = toTier;
		this.itemId = itemId;
		this.itemType = itemType;
		this.itemCount = itemCount;
	}

	public byte getKorCheck() {
		return korCheck;
	}

	public int getRankingSeasonId() {
		return rankingSeasonId;
	}

	public int getRewardType() {
		return rewardType;
	}

	public int getFromTier() {
		return fromTier;
	}

	public int getToTier() {
		return toTier;
	}

	public int getItemId() {
		return itemId;
	}

	public int getItemType() {
		return itemType;
	}

	public int getItemCount() {
		return itemCount;
	}

}
