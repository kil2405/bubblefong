package com.bubbleShooter.resource;

public class ProfileResource {
    private int index;
    private int id;
    private int itemType;
    private int idCharacter;
    private String iconSpineProfile;
    private int idTextName;
    private int idTextIntro;
    private int sortNumber;
    private int startDate;
    private int endDate;

    public ProfileResource(int index, int id, int itemType, int idCharacter, String iconSpineProfile, int idTextName, int idTextIntro, int sortNumber, int startDate, int endDate) {
        super();
        this.index = index;
        this.id = id;
        this.itemType = itemType;
        this.idCharacter = idCharacter;
        this.iconSpineProfile = iconSpineProfile;
        this.idTextName = idTextName;
        this.idTextIntro = idTextIntro;
        this.sortNumber = sortNumber;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getIndex() {
        return index;
    }

    public int getId() {
        return id;
    }

    public int getItemType() {
        return itemType;
    }

    public int getIdCharacter() {
        return idCharacter;
    }

    public String getIconSpineProfile() {
        return iconSpineProfile;
    }

    public int getIdTextName() {
        return idTextName;
    }

    public int getIdTextIntro() {
        return idTextIntro;
    }

    public int getSortNumber() {
        return sortNumber;
    }

    public int getStartDate() {
        return startDate;
    }

    public int getEndDate() {
        return endDate;
    }
}
