package com.android.hackregina.models;

public class LeaderPerson {
	
	private Integer id;
	private String UserId;
	private String UserName;
	private Integer TotalCheckins;
	private Integer Points;
	
	public String getUserName() {
		return UserName;
	}
	
	public String getUserId() {
		return UserId;
	}
	
	public Integer getPoints() {
		return Points;
	}
	
	public Integer getTotalCheckins() {
		return TotalCheckins;
	}
	
	public String toString() {
		return id + " " + UserId + " " + UserName + " " + TotalCheckins + " " + Points;
	}
}
