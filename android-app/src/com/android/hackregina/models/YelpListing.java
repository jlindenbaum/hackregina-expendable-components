package com.android.hackregina.models;

public class YelpListing {

	private String mobileUrl = "";
	private String businessName = "";
	private String rating = "";
	private String ratingImageSmall = "";
	private String ratingImageLarge = "";
	private String distance = "";

	public String getDistance(boolean round) {
		if (round) {
			Double doubleValue = Double.valueOf(this.distance);
			Integer intValue = Integer.valueOf("" + Math.round(doubleValue));
			return intValue.toString() + " m";
		}

		return this.distance + " m";
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getSmallRatingImage() {
		return this.ratingImageSmall;
	}

	public void setSmallRatingImage(String ratingImage) {
		this.ratingImageSmall = ratingImage;
	}

	public String getLargeRatingImage() {
		return this.ratingImageLarge;
	}

	public void setLargeRatingImage(String ratingImage) {
		this.ratingImageLarge = ratingImage;
	}

	public String getRating() {
		return this.rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getBusinessName() {
		return this.businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getMobileUrl() {
		return this.mobileUrl;
	}

	public void setMobileUrl(String mobileUrl) {
		this.mobileUrl = mobileUrl;
	}
}
