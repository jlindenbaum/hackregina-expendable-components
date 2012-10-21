package com.android.hackregina;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.hackregina.interfaces.YellowTaskCallback;
import com.android.hackregina.tasks.YellowTask;
import com.android.hackregina.utils.Logger;
import com.android.hackregina.yellowbooks.Listing;
import com.android.hackregina.yellowbooks.YellowListingAdapter;

public class YellowActivity extends Activity implements YellowTaskCallback {
	public static final String TAG = "### YellowActivity";
	private ProgressDialog progress;

	@Override
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.activity_yellow_listing);

		this.progress = new ProgressDialog(this);
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.setCancelable(false);
		progress.setMessage("Loading...");
		progress.show();

		Bundle extras = getIntent().getExtras();
		String lat = extras.getString("lat");
		String lng = extras.getString("lng");

		loadYellowResults(lat, lng);
	}

	public void loadYellowResults(String lat, String lng) {
		// start yellow api requests
		String what = "restaurants";
		String where = String.format("cZ{%s},{%s}", lng, lat);
		Logger.log(TAG, Uri.encode(where));
		new YellowTask(this, what, Uri.encode(where)).execute();
	}

	@Override
	public void yellowTaskComplete(final ArrayList<Listing> listingData) {
		YellowListingAdapter adapter = new YellowListingAdapter(getApplicationContext(), R.layout.listing_item, listingData);
		ListView listView = (ListView) findViewById(R.id.yellow_listView);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				String mapUri = "http://maps.google.com/maps?q=" + listingData.get(arg2).getAddress();
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUri));
				startActivity(intent);
			}
		});
		this.progress.dismiss();
	}

}
