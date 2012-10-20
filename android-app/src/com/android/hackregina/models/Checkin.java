package com.android.hackregina.models;

public class Checkin {
	
	private Integer id;
	private Integer ObjectId;
	private String UserId;
	private Integer Points;
	
	public Integer getPoints() {
		return Points;
	}
	
	public String toString() {
		return ObjectId + " " + UserId + " " + Points;
	}
}
