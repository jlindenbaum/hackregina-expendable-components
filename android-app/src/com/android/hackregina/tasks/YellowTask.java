package com.android.hackregina.tasks;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.android.hackregina.Settings;
import com.android.hackregina.YellowAPI;
import com.android.hackregina.YellowAPIImpl;
import com.android.hackregina.interfaces.YellowTaskCallback;
import com.android.hackregina.models.Listing;
import com.android.hackregina.utils.Logger;

public class YellowTask extends AsyncTask<Void, Void, ArrayList<Listing>> {
	private static final String TAG = "### YellowTask";
	
	private YellowTaskCallback callback;
	private String what;
	private String where;

	public YellowTask(YellowTaskCallback callback, String what, String where) {
		this.callback = callback;
		this.what = what;
		this.where = where;
	}

	@Override
	protected ArrayList<Listing> doInBackground(Void... voids) {
		ArrayList<Listing> listingData = new ArrayList<Listing>();
		try {
			YellowAPI api = new YellowAPIImpl("en", Settings.YellowAPIKey, "discovr", true);
			JSONObject response = api.findBusiness(this.what, this.where, 1, 40, 0);
			Logger.log(TAG, response.toString());
			// Make sure the response contains results
			if (response.isNull("listings") == false) {
				JSONArray listings = response.getJSONArray("listings");

				for (int i = 0; i < listings.length(); ++i) {
					listingData.add(new Listing(listings.getJSONObject(i)));
					Logger.log(TAG, listingData.get(i).toString());
				}
			}
		} catch (Exception e) {
			Log.e("MerchantFeedError", "Error loading JSON", e);
		}

		return listingData;
	}

	@Override
	protected void onPostExecute(ArrayList<Listing> listingData) {
		if (this.callback != null) {
			this.callback.yellowTaskComplete(listingData);
		}
	}
}