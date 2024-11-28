package com.bubbleShooter.resource;

public class RankingSeasonResource {
	private int index;
	private int rankingSeasonId;
	private int previousSeason;
	private long seasonStart;
	private long seasonEnd;
	private String seasonDesc;

	public RankingSeasonResource(int index, int rankingSeasonId, int previousSeason, long seasonStart, long seasonEnd,
			String seasonDesc) {
		super();
		this.index = index;
		this.rankingSeasonId = rankingSeasonId;
		this.previousSeason = previousSeason;
		this.seasonStart = seasonStart;
		this.seasonEnd = seasonEnd;
		this.seasonDesc = seasonDesc;
	}

	public int getIndex() {
		return index;
	}

	public int getRankingSeasonId() {
		return rankingSeasonId;
	}

	public int getPreviousSeason() {
		return previousSeason;
	}

	public long getSeasonStart() {
		return seasonStart;
	}

	public long getSeasonEnd() {
		return seasonEnd;
	}

	public String getSeasonDesc() {
		return seasonDesc;
	}

}
