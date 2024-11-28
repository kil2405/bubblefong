package com.bubbleShooter.domain;

public class Profile {
    private int userId;
    private int profileId;
    private int itemType;
    private int characterId;
    private byte isNew;
    private byte isHave;
    private byte isUse;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public byte getIsNew() {
        return isNew;
    }

    public void setIsNew(byte isNew) {
        this.isNew = isNew;
    }

    public byte getIsHave() {
        return isHave;
    }

    public void setIsHave(byte isHave) {
        this.isHave = isHave;
    }

    public byte getIsUse() {
        return isUse;
    }

    public void setIsUse(byte isUse) {
        this.isUse = isUse;
    }
}
