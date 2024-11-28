package com.bubbleShooter.domain;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.util.TimeCalculation;

public class UserProfileLog {
    private byte ptn_month;
    private int ptn_day;
    private long log_date;
    private int user_id;
    private int profile_id;
    private int item_type;
    private int character_id;
    private byte is_new;
    private byte is_have;
    private byte is_use;

    public byte getPnt_month() {
        return ptn_month;
    }

    public void setPnt_month(byte pnt_month) {
        this.ptn_month = pnt_month;
    }

    public int getPnt_day() {
        return ptn_day;
    }

    public void setPnt_day(int pnt_day) {
        this.ptn_day = pnt_day;
    }

    public long getLog_date() {
        return log_date;
    }

    public void setLog_date(long log_date) {
        this.log_date = log_date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(int profile_id) {
        this.profile_id = profile_id;
    }

    public int getItem_type() {
        return item_type;
    }

    public void setItem_type(int item_type) {
        this.item_type = item_type;
    }

    public int getCharacter_id() {
        return character_id;
    }

    public void setCharacter_id(int character_id) {
        this.character_id = character_id;
    }

    public byte getIs_new() {
        return is_new;
    }

    public void setIs_new(byte is_new) {
        this.is_new = is_new;
    }

    public byte getIs_have() {
        return is_have;
    }

    public void setIs_have(byte is_have) {
        this.is_have = is_have;
    }

    public byte getIs_use() {
        return is_use;
    }

    public void setIs_use(byte is_use) {
        this.is_use = is_use;
    }
    
    public UserProfileLog Set(Profile profile)
    {
        this.setPnt_month((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_MONTH, 0));
        this.setPnt_day(TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_DAY, 0));
        this.setLog_date(TimeCalculation.getCurrentUnixTime());
        this.setUser_id(profile.getUserId());
        this.setProfile_id(profile.getProfileId());
        this.setItem_type(profile.getItemType());
        this.setCharacter_id(profile.getCharacterId());
        this.setIs_new(profile.getIsNew());
        this.setIs_have(profile.getIsHave());
        this.setIs_use(profile.getIsUse());

        return this;
    }
}
