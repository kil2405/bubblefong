package com.bubbleShooter.resource;

public class CharacterResource {
	private int index;
	private int itemType;
	private int id;
	private String gameSpineName;
	private String gameSpineSkin;
	private String menuSpineName;
	private String iconInvenName;
	private String iconMatchingName;
	private String iconRankingName;
	private String voicePackName;
	private String voicePackNameKr;
	private int idTextName;
	private int idTextIntro;
	private int idCharacterActive;
	private byte isNft;
	private int sortNumber;
	private String desc;

	public CharacterResource(int index, int itemType, int id, String gameSpineName, String gameSpineSkin,
			String menuSpineName, String iconInvenName, String iconMatchingName, String iconRankingName,
			String voicePackName, String voicePackNameKr, int idTextName, int idTextIntro, int idCharacterActive, byte isNft, int sortNumber,
			String desc) {
		super();
		this.index = index;
		this.itemType = itemType;
		this.id = id;
		this.gameSpineName = gameSpineName;
		this.gameSpineSkin = gameSpineSkin;
		this.menuSpineName = menuSpineName;
		this.iconInvenName = iconInvenName;
		this.iconMatchingName = iconMatchingName;
		this.iconRankingName = iconRankingName;
		this.voicePackName = voicePackName;
		this.voicePackNameKr = voicePackNameKr;
		this.idTextName = idTextName;
		this.idTextIntro = idTextIntro;
		this.idCharacterActive = idCharacterActive;
		this.isNft = isNft;
		this.sortNumber = sortNumber;
		this.desc = desc;
	}

	public int getIndex() {
		return index;
	}

	public int getItemType() {
		return itemType;
	}

	public int getId() {
		return id;
	}

	public String getGameSpineName() {
		return gameSpineName;
	}

	public String getGameSpineSkin() {
		return gameSpineSkin;
	}

	public String getMenuSpineName() {
		return menuSpineName;
	}

	public String getIconInvenName() {
		return iconInvenName;
	}

	public String getIconMatchingName() {
		return iconMatchingName;
	}

	public String getIconRankingName() {
		return iconRankingName;
	}

	public String getVoicePackName() {
		return voicePackName;
	}

	public String getVoicePackNameKr() {
		return voicePackNameKr;
	}

	public int getIdTextName() {
		return idTextName;
	}

	public int getIdTextIntro() {
		return idTextIntro;
	}

	public int getIdCharacterActive() {
		return idCharacterActive;
	}

	public byte getIsNft() {
		return isNft;
	}

	public int getSortNumber() {
		return sortNumber;
	}

	public String getDesc() {
		return desc;
	}

}
