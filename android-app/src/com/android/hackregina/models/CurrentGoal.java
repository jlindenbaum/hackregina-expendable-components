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
	
	public String getTitle() {
		return Title;
	}
	
	public String getCurrentPointsAward() {
		return CurrentPointsAward;
	}
	
	public String getLat() {
		return Lat;
	}
	public String getLong() {
		return Long;
	}
	public String getAddress() {
		return Address;
	}
	
	public String toString() {
		return id + " " + ObjectId + " " + Date + " " + Title + " " + Address + " " + Lat + " " + " " + Long + " " + CurrentPointsAward;
	}
}
