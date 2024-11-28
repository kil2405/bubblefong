package com.bubbleShooter.resource;

public class SkillPartnerResource {
	private int index;
	private int id;
	private int idTextInfo;
	private int idTextSummaryInfo;
	private int useSlot;
	private int initialValue1;
	private float growthValuePerLevel1;
	private int isPercent1;
	private int initialValue2;
	private float growthValuePerLevel2;
	private int isPercent2;
	private int initialValue3;
	private float growthValuePerLevel3;
	private int isPercent3;
	private String desc1;
	private String desc2;
	private String desc4;

	public SkillPartnerResource(int index, int id, int idTextInfo, int idTextSummaryInfo, int useSlot,
			int initialValue1, float growthValuePerLevel1, int isPercent1, int initialValue2,
			float growthValuePerLevel2, int isPercent2, int initialValue3, float growthValuePerLevel3, int isPercent3,
			String desc1, String desc2, String desc4) {
		super();
		this.index = index;
		this.id = id;
		this.idTextInfo = idTextInfo;
		this.idTextSummaryInfo = idTextSummaryInfo;
		this.useSlot = useSlot;
		this.initialValue1 = initialValue1;
		this.growthValuePerLevel1 = growthValuePerLevel1;
		this.isPercent1 = isPercent1;
		this.initialValue2 = initialValue2;
		this.growthValuePerLevel2 = growthValuePerLevel2;
		this.isPercent2 = isPercent2;
		this.initialValue3 = initialValue3;
		this.growthValuePerLevel3 = growthValuePerLevel3;
		this.isPercent3 = isPercent3;
		this.desc1 = desc1;
		this.desc2 = desc2;
		this.desc4 = desc4;
	}

	public int getIndex() {
		return index;
	}

	public int getId() {
		return id;
	}

	public int getIdTextInfo() {
		return idTextInfo;
	}

	public int getIdTextSummaryInfo() {
		return idTextSummaryInfo;
	}

	public int getUseSlot() {
		return useSlot;
	}

	public int getInitialValue1() {
		return initialValue1;
	}

	public float getGrowthValuePerLevel1() {
		return growthValuePerLevel1;
	}

	public int getIsPercent1() {
		return isPercent1;
	}

	public int getInitialValue2() {
		return initialValue2;
	}

	public float getGrowthValuePerLevel2() {
		return growthValuePerLevel2;
	}

	public int getIsPercent2() {
		return isPercent2;
	}

	public int getInitialValue3() {
		return initialValue3;
	}

	public float getGrowthValuePerLevel3() {
		return growthValuePerLevel3;
	}

	public int getIsPercent3() {
		return isPercent3;
	}

	public String getDesc1() {
		return desc1;
	}

	public String getDesc2() {
		return desc2;
	}

	public String getDesc4() {
		return desc4;
	}

}
