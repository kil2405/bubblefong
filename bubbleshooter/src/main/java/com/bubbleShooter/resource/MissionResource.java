package com.bubbleShooter.resource;

public class MissionResource {
	private int missionId;
	private int difficulty;
	private int missionTitle;
	private int missionDetail;
	private byte heroType;
	private int heroId;
	private int heroGrade;
	private int heroLevel;
	private int time;
	private int bType1;
	private int basicReward1;
	private int bCount1;
	private int bType2;
	private int basicReward2;
	private int bCount2;
	private int gType1;
	private int grandReward1;
	private int gCount1;
	private int gType2;
	private int grandReward2;
	private int gCount2;
	private int gType3;
	private int grandReward3;
	private int gCount3;

	public MissionResource(int missionId, int difficulty, int missionTitle, int missionDetail, byte heroType,
			int heroId, int heroGrade, int heroLevel, int time, int bType1, int basicReward1, int bCount1, int bType2,
			int basicReward2, int bCount2, int gType1, int grandReward1, int gCount1, int gType2, int grandReward2,
			int gCount2, int gType3, int grandReward3, int gCount3) {
		super();
		this.missionId = missionId;
		this.difficulty = difficulty;
		this.missionTitle = missionTitle;
		this.missionDetail = missionDetail;
		this.heroType = heroType;
		this.heroId = heroId;
		this.heroGrade = heroGrade;
		this.heroLevel = heroLevel;
		this.time = time;
		this.bType1 = bType1;
		this.basicReward1 = basicReward1;
		this.bCount1 = bCount1;
		this.bType2 = bType2;
		this.basicReward2 = basicReward2;
		this.bCount2 = bCount2;
		this.gType1 = gType1;
		this.grandReward1 = grandReward1;
		this.gCount1 = gCount1;
		this.gType2 = gType2;
		this.grandReward2 = grandReward2;
		this.gCount2 = gCount2;
		this.gType3 = gType3;
		this.grandReward3 = grandReward3;
		this.gCount3 = gCount3;
	}

	public int getMissionId() {
		return missionId;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public int getMissionTitle() {
		return missionTitle;
	}

	public int getMissionDetail() {
		return missionDetail;
	}

	public byte getHeroType() {
		return heroType;
	}

	public int getHeroId() {
		return heroId;
	}

	public int getHeroGrade() {
		return heroGrade;
	}

	public int getHeroLevel() {
		return heroLevel;
	}

	public int getTime() {
		return time;
	}

	public int getbType1() {
		return bType1;
	}

	public int getBasicReward1() {
		return basicReward1;
	}

	public int getbCount1() {
		return bCount1;
	}

	public int getbType2() {
		return bType2;
	}

	public int getBasicReward2() {
		return basicReward2;
	}

	public int getbCount2() {
		return bCount2;
	}

	public int getgType1() {
		return gType1;
	}

	public int getGrandReward1() {
		return grandReward1;
	}

	public int getgCount1() {
		return gCount1;
	}

	public int getgType2() {
		return gType2;
	}

	public int getGrandReward2() {
		return grandReward2;
	}

	public int getgCount2() {
		return gCount2;
	}

	public int getgType3() {
		return gType3;
	}

	public int getGrandReward3() {
		return grandReward3;
	}

	public int getgCount3() {
		return gCount3;
	}

}
