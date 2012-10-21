package com.android.hackregina.tasks;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import android.os.AsyncTask;
import android.util.Log;

import com.android.hackregina.Settings;
import com.android.hackregina.YelpV2API;
import com.android.hackregina.interfaces.YelpTaskCallback;
import com.android.hackregina.models.YelpListing;
import com.android.hackregina.utils.Logger;

public class YelpTask extends AsyncTask<Void, Void, ArrayList<YelpListing>> {
	private static final String TAG = "### YellowTask";

	private YelpTaskCallback callback;
	private String lat;
	private String lon;

	public YelpTask(YelpTaskCallback callback, String lat, String lon) {
		this.callback = callback;
		this.lat = lat;
		this.lon = lon;
	}

	@Override
	protected ArrayList<YelpListing> doInBackground(Void... voids) {
		ArrayList<YelpListing> listings = new ArrayList<YelpListing>();
		
		// oauth request to yelp backend
		OAuthService service = new ServiceBuilder().provider(YelpV2API.class).apiKey(Settings.YelpConsumerKey).apiSecret(Settings.YelpConsumerSecret).build();
		Token accessToken = new Token(Settings.YelpToken, Settings.YelpTokenSecret);
		OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
		request.addQuerystringParameter("ll", this.lat + "," + this.lon);
		request.addQuerystringParameter("category", "restaurants");
		service.signRequest(accessToken, request);
		Response response = request.send();
		String rawData = response.getBody();
		
		try {
			JSONObject jsonObject = new JSONObject(rawData);
			JSONArray businesses = jsonObject.getJSONArray("businesses");
			for (int i = 0; i < businesses.length(); i++) {
				JSONObject business = businesses.getJSONObject(i);
				YelpListing yelpListing = new YelpListing();
				yelpListing.setMobileUrl(business.getString("mobile_url"));
				yelpListing.setBusinessName(business.getString("name"));
				yelpListing.setSmallRatingImage(business.getString("rating_img_url_small"));
				yelpListing.setDistance(business.getString("distance"));
				listings.add(yelpListing);
			}
		} catch (Exception e) {
			Log.e(TAG, "Error during yelp parsing: " + e.getMessage());
			e.printStackTrace();
		}

		Logger.log(TAG, rawData);

		return listings;
	}

	@Override
	protected void onPostExecute(ArrayList<YelpListing> listingData) {
		if (this.callback != null) {
			this.callback.yelpTaskComplete(listingData);
		}
	}
}