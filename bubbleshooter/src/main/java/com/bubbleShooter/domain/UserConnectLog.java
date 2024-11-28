package com.bubbleShooter.domain;

import java.util.List;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.util.TimeCalculation;

public class UserConnectLog {
	private byte ptn_month;
	private byte ptn_day;
	private long log_date;
	private long user_id;
	private String uuid;
	private int level;
	private int gold;
	private int ruby;
	private int diamond;
	private String version;
	private int rank_tier;
	private int rank_point;
	private String region;
	private byte is_new; // 0:기존유저, 1: 신규유저, 2: 복귀유저

	public byte getPtn_month() {
		return ptn_month;
	}

	public void setPtn_month(byte ptn_month) {
		this.ptn_month = ptn_month;
	}

	public byte getPtn_day() {
		return ptn_day;
	}

	public void setPtn_day(byte ptn_day) {
		this.ptn_day = ptn_day;
	}

	public long getLog_date() {
		return log_date;
	}

	public void setLog_date(long log_date) {
		this.log_date = log_date;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getRuby() {
		return ruby;
	}

	public void setRuby(int ruby) {
		this.ruby = ruby;
	}

	public int getDiamond() {
		return diamond;
	}

	public void setDiamond(int diamond) {
		this.diamond = diamond;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getRank_tier() {
		return rank_tier;
	}

	public void setRank_tier(int rank_tier) {
		this.rank_tier = rank_tier;
	}

	public int getRank_point() {
		return rank_point;
	}

	public void setRank_point(int rank_point) {
		this.rank_point = rank_point;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public byte getIs_new() {
		return is_new;
	}

	public void setIs_new(byte is_new) {
		this.is_new = is_new;
	}

	public UserConnectLog Set(User user, List<UserItem> items, String version, String region)
	{
		this.setPtn_month((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_MONTH, 0));
		this.setPtn_day((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_DAY, 0));
		this.setLog_date(TimeCalculation.getCurrentUnixTime());
		this.setUser_id(user.getId());
		this.setUuid(user.getUuid());
		this.setLevel(user.getLevel());
		this.setGold(items.get(ConstantVal.ITEM_TYPE_GOLD).getItemCount());
		this.setRuby(items.get(ConstantVal.ITEM_TYPE_RUBY).getItemCount());
		this.setDiamond(items.get(ConstantVal.ITEM_TYPE_DIAMOND).getItemCount());
		this.setVersion(version);
		this.setRank_tier(user.getTier());
		this.setRank_point(user.getRankPoint());
		this.setRegion(region);

		return this;
	}
}
