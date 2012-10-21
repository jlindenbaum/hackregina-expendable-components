package com.android.hackregina;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;

import com.android.hackregina.adapters.YelpListingAdapter;
import com.android.hackregina.interfaces.YelpTaskCallback;
import com.android.hackregina.models.YelpListing;
import com.android.hackregina.tasks.YelpTask;
import com.android.hackregina.utils.Logger;

public class YelpActivity extends Activity implements YelpTaskCallback {

	private static final String TAG = "### YelpActivity";
	private ProgressDialog progress;

	@Override
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.activity_yelp_listing);
		
		this.progress = new ProgressDialog(this);
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.setCancelable(false);
		progress.setMessage("Loading...");
		progress.show();

		Bundle extras = getIntent().getExtras();
		String lat = extras.getString("lat");
		String lng = extras.getString("lng");

		executeYelpRefresh(lat, lng);
	}

	private void executeYelpRefresh(String lat, String lng) {

		new YelpTask(this, lat, lng).execute(null, null, null);
	}

	@Override
	public void yelpTaskComplete(ArrayList<YelpListing> listingData) {
		Logger.log(TAG, "" + listingData);

		if (listingData != null && listingData.size() > 0) {
			ListView yelpListView = (ListView) findViewById(R.id.yelp_listView);
			yelpListView.setAdapter(new YelpListingAdapter(this, R.layout.yelp_item, listingData));
		}
		
		this.progress.dismiss();
	}
}
