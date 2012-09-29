package com.android.hackregina.models;

public class CurrentGoal {
	
	private Integer id;
	private Integer ObjectId;
	private String Date;
	private String Title;
	private String Address;
	private String Lat;
	private String Long;
	private String CurrentPointsAward;
	
	public Integer getObjectId() {
		return ObjectId;
	}
	
	public String toString() {
		return ObjectId + " " + Date + " " + Title + " " + Address + " " + Lat + " " + " " + Long + " " + CurrentPointsAward;
	}
}
