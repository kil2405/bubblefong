package com.bubbleShooter.resource;

public class PartnerResource {
	private int index;
	private int itemType;
	private int id;
	private String spineName;
	private String iconInvenName;
	private String iconPresetName;
	private int idTextName;
	private int idTextInfo;
	private int ingamePosition;
	private byte isNft;
	private int sortNumber;
	private String desc;

	public PartnerResource(int index, int itemType, int id, String spineName, String iconInvenName,
			String iconPresetName, int idTextName, int idTextInfo, int ingamePosition, byte isNft, int sortNumber,
			String desc) {
		super();
		this.index = index;
		this.itemType = itemType;
		this.id = id;
		this.spineName = spineName;
		this.iconInvenName = iconInvenName;
		this.iconPresetName = iconPresetName;
		this.idTextName = idTextName;
		this.idTextInfo = idTextInfo;
		this.ingamePosition = ingamePosition;
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

	public String getSpineName() {
		return spineName;
	}

	public String getIconInvenName() {
		return iconInvenName;
	}

	public String getIconPresetName() {
		return iconPresetName;
	}

	public int getIdTextName() {
		return idTextName;
	}

	public int getIdTextInfo() {
		return idTextInfo;
	}

	public int getIngamePosition() {
		return ingamePosition;
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
