package com.android.hackregina.interfaces;

import java.util.ArrayList;

import com.android.hackregina.models.YelpListing;

public interface YelpTaskCallback {

	public void yelpTaskComplete(ArrayList<YelpListing> listingData);
}