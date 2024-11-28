package com.bubbleShooter.domain;

public class UserVersion {
	private int major;
	private int minor;
	private int patch;
	private int market;
	private String country;


	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public int getPatch() {
		return patch;
	}

	public void setPatch(int patch) {
		this.patch = patch;
	}

	public int getMarket() {
		return market;
	}

	public void setMarket(int market) {
		this.market = market;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public void setVersion(int market, String region, String version) {
		String[] versions = version.split("\\.");
		major = Integer.parseInt(versions[0]);
		minor = Integer.parseInt(versions[1]);
		patch = Integer.parseInt(versions[2]);

		this.market = market;
		this.country = region;
	}
}
