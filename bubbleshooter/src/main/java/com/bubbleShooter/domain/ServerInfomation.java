package com.bubbleShooter.domain;

public class ServerInfomation {
	private int serverId;
	private String serverIp;
	private String serverName;
	private String serverMode;
	private String serverVendor;
	private long serverTime;
	private String serverDateTime;
	private String build_date;
	private boolean ai_enabled;
	private boolean use_encryption;
	private boolean server_inspection;

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerMode() {
		return serverMode;
	}

	public void setServerMode(String serverMode) {
		this.serverMode = serverMode;
	}

	public String getServerVendor() {
		return serverVendor;
	}

	public void setServerVendor(String serverVendor) {
		this.serverVendor = serverVendor;
	}

	public long getServerTime() {
		return serverTime;
	}

	public void setServerTime(long serverTime) {
		this.serverTime = serverTime;
	}

	public String getServerDateTime() {
		return serverDateTime;
	}

	public void setServerDateTime(String serverDateTime) {
		this.serverDateTime = serverDateTime;
	}

	public String getBuild_date() {
		return build_date;
	}

	public void setBuild_date(String build_date) {
		this.build_date = build_date;
	}

	public boolean isAi_enabled() {
		return ai_enabled;
	}

	public void setAi_enabled(boolean ai_enabled) {
		this.ai_enabled = ai_enabled;
	}

	public boolean isUse_encryption() {
		return use_encryption;
	}

	public void setUse_encryption(boolean use_encryption) {
		this.use_encryption = use_encryption;
	}

	public boolean isServer_inspection() {
		return server_inspection;
	}

	public void setServer_inspection(boolean server_inspection) {
		this.server_inspection = server_inspection;
	}

}
