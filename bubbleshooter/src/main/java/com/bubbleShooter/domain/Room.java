package com.bubbleShooter.domain;

public class Room {
	private String uuid;
	private int createdAt;
	private int gameStartedAt;
	private String users;
	private int expriedAt;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(int createdAt) {
		this.createdAt = createdAt;
	}

	public int getGameStartedAt() {
		return gameStartedAt;
	}

	public void setGameStartedAt(int gameStartedAt) {
		this.gameStartedAt = gameStartedAt;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public int getExpriedAt() {
		return expriedAt;
	}

	public void setExpriedAt(int expriedAt) {
		this.expriedAt = expriedAt;
	}

}
