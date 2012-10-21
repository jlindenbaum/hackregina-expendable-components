package com.android.hackregina;

public class Settings {

	public static final boolean DEBUG = true;
	
	// azure access key
	public static final String X_ZUMO_KEY = "EKcZFrVKadjfiDEsGCxoAyvFIYhCPn15";
	
	// azure api calls
	public static final String URL_CURRENT_GOAL = "http://discovr.azure-mobile.net/tables/CurrentGoal";
	public static final String URL_LEADERBOARD = "http://discovr.azure-mobile.net/tables/Leaderboard";
	public static final String URL_CHECKIN = "http://discovr.azure-mobile.net/tables/Checkin";
	
	// gmaps static api key: AIzaSyAUZjvOAGb8CU2ljzONGaC8wqbShNwHNRM
	public static final String URL_GMAPS_STATIC = "http://maps.googleapis.com/maps/api/staticmap?sensor=false&center=%s,%s&zoom=15&markers=color:red|%s,%s&size=500x300&key=AIzaSyAUZjvOAGb8CU2ljzONGaC8wqbShNwHNRM";
	
	// yelp oauth parameters
	public static final String YelpConsumerKey = "eVsEmnd20q9AGw1-hghsUQ";
	public static final String YelpConsumerSecret = "jfd8754F2B8MK56KRHSMIoXiyXE";
	public static final String YelpToken = "cs7piuUlegUhF0UFvTvAJKw2sD55Cr5n";
	public static final String YelpTokenSecret = "rcP7SWP-amOoWeiWGJOj8ClgcqI";
	
	// yellow books api key
	public static final String YellowAPIKey = "mzpcne2fjv8qszj5v627ra9b";
	public static final String YellowAPIAppName = "discovr";
}
