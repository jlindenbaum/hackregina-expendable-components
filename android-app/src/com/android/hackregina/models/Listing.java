package com.android.hackregina.models;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.maps.GeoPoint;

public class Listing {
	private String id;
	private String parentId;
	private boolean parent;
	
	private String name;
	
	//public GeoPoint geoPoint = null;
	private double distance = -1.0;
	private Address address;
	
	public Listing(JSONObject json) throws JSONException {
		this.id = json.getString("id");
		this.parentId = json.getString("parentId");
		this.parent = json.getBoolean("isParent");
		
		this.name = json.getString("name");
		
//		if (!json.isNull("geoCode")) {
//			JSONObject geoCode = json.getJSONObject("geoCode");
//			
//			this.geoPoint = new GeoPoint((int) (geoCode.getDouble("latitude") * 1000000), (int) (geoCode.getDouble("longitude") * 1000000));
//		}
		
		if (json.getString("distance").length() > 0) {
			this.distance = json.getDouble("distance");
		}
		
		if (!json.isNull("address")) {
			this.address = new Address(json.getJSONObject("address"));
		}
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getParentId() {
		return this.parentId;
	}
	
	public boolean isParent() {
		return this.parent;
	}
	
	public String getName() {
		return this.name;
	}
	
	public GeoPoint getGeoPoint() {
		return null;
		//return this.geoPoint;
	}
	
	public double getDistance() {
		return this.distance;
	}
	
	public Address getAddress() {
		return this.address;
	}
}
