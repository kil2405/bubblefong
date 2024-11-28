package com.bubbleShooter.resource;

public class RankingTierResource {
	private int id;
	private int rankingSeasonId;
	private int rankingTier;
	private int tierName;
	private String tierIcon;
	private String seasonRewardSpine;
	private int regulateTier;
	private int rankingTierStandardPoint;
	private int rankingMaxRp;
	private int rankingMinRp;

	public RankingTierResource(int id, int rankingSeasonId, int rankingTier, int tierName, String tierIcon,
			String seasonRewardSpine, int regulateTier, int rankingTierStandardPoint, int rankingMaxRp,
			int rankingMinRp) {
		super();
		this.id = id;
		this.rankingSeasonId = rankingSeasonId;
		this.rankingTier = rankingTier;
		this.tierName = tierName;
		this.tierIcon = tierIcon;
		this.seasonRewardSpine = seasonRewardSpine;
		this.regulateTier = regulateTier;
		this.rankingTierStandardPoint = rankingTierStandardPoint;
		this.rankingMaxRp = rankingMaxRp;
		this.rankingMinRp = rankingMinRp;
	}

	public int getId() {
		return id;
	}

	public int getRankingSeasonId() {
		return rankingSeasonId;
	}

	public int getRankingTier() {
		return rankingTier;
	}

	public int getTierName() {
		return tierName;
	}

	public String getTierIcon() {
		return tierIcon;
	}

	public String getSeasonRewardSpine() {
		return seasonRewardSpine;
	}

	public int getRegulateTier() {
		return regulateTier;
	}

	public int getRankingTierStandardPoint() {
		return rankingTierStandardPoint;
	}

	public int getRankingMaxRp() {
		return rankingMaxRp;
	}

	public int getRankingMinRp() {
		return rankingMinRp;
	}

}
