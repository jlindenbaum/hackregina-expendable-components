package com.android.hackregina;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.hackregina.models.Checkin;
import com.android.hackregina.models.CurrentGoal;
import com.android.hackregina.models.Listing;
import com.android.hackregina.utils.Logger;
import com.google.gson.Gson;

public class GoalActivity extends Activity implements NetworkImageTaskInterface {

	public static final String TAG = "### GoalActivity";

	private Integer objectId;
	private String latLngStr = "";
	private String lat = "";
	private String lon = "";
	private String address = "";
	private ProgressDialog progress;
	protected ArrayList<Listing> merchants = new ArrayList<Listing>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goal);

		Logger.log(TAG, "Starting...");
		showLoadingSpinner();
		new GoalActivityTask().execute(new String[] { Settings.URL_CURRENT_GOAL });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void showLoadingSpinner() {
		this.progress = new ProgressDialog(this);
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.setCancelable(false);
		progress.setMessage("Loading...");
		progress.show();
	}

	private void hideLoadingSpinner() {
		this.progress.hide();
	}

	public void refreshComplete(CurrentGoal goal) {
		Logger.log(TAG, "DONE");
		renderCurrentGoal(goal);
		this.hideLoadingSpinner();
	}

	public void completeYelpIntent(View v) {
		String yelpURI = "http://www.yelp.com/search?find_desc=restaurant&find_loc=" + this.address + "regina";
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(yelpURI));
		startActivity(intent);
	}

	public void completeCheckin(View v) {
		String checkinJSON = "{\"ObjectId\":" + this.objectId + ", \"UserId\":\"" + this.getGoogleAccount() + "\"}";
		this.showLoadingSpinner();
		new CheckinTask().execute(new String[] { checkinJSON });
	}

	private void finishedCheckin(Checkin checkin) {

		// set points
		Button goalButton = (Button) findViewById(R.id.goal_checkinButton);
		goalButton.setText("ACCOMPLISHED!");
		goalButton.setOnClickListener(null);
		goalButton.setActivated(false);

		// show checkout
		LinearLayout checkout = (LinearLayout) findViewById(R.id.goal_checkoutLayout);
		checkout.setVisibility(View.VISIBLE);
		LinearLayout mapLayout = (LinearLayout) findViewById(R.id.goal_mapLayout);
		mapLayout.setVisibility(View.GONE);

		// start yellow api requests
		String what = "restaurants";
		String where = String.format("cZ{%s},{%s}", this.lon, this.lat);
		Logger.log(TAG, Uri.encode(where));
		new YellowTask(what, Uri.encode(where)).execute();
		
		this.hideLoadingSpinner();
		AlertDialog alert = new AlertDialog.Builder(this).create();
		alert.setTitle("Dora The Explorer!");
		alert.setMessage("You've checked in. That's awesome. Checkout what's around!");
		alert.show();
	}

	private void finishYellowTask() {
		YellowListingAdapter adapter = new YellowListingAdapter(getApplicationContext(), R.layout.listing_item, merchants);
		ListView listView = (ListView) findViewById(R.id.goal_yellowList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				TextView address = (TextView) arg1.findViewById(R.id.businessAddress);
				String mapUri = "http://maps.google.com/maps?q=" + address.getText();
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUri));
				startActivity(intent);
			}
		});
	}

	private String getGoogleAccount() {
		AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
		Account[] list = manager.getAccounts();
		String gmail = null;

		for (Account account : list) {
			if (account.type.equalsIgnoreCase("com.google")) {
				return account.name;
			}
		}

		return gmail;
	}

	private void renderCurrentGoal(CurrentGoal goal) {
		this.objectId = goal.getObjectId();
		this.latLngStr = goal.getLat() + "," + goal.getLong();
		this.address = goal.getAddress();
		this.lat = goal.getLat();
		this.lon = goal.getLong();
		// goal name
		TextView goalName = (TextView) findViewById(R.id.goal_goalName);
		goalName.setText(goal.getTitle());

		// set points
		Button goalButton = (Button) findViewById(R.id.goal_checkinButton);
		goalButton.setText("CHECK IN FOR " + goal.getCurrentPointsAward() + " POINTS");

		// map image
		String imageUri = String.format(Settings.URL_GMAPS_STATIC, goal.getLat(), goal.getLong(), goal.getLat(), goal.getLong());
		new NetworkImageTask().setActivity(this).execute(new String[] { imageUri });
	}

	@Override
	public void loadBitmapComplete(Bitmap bm) {
		Logger.log(TAG, "Finished loading bitmap");
		ImageView mapImage = (ImageView) findViewById(R.id.goal_mapImage);
		mapImage.setImageBitmap(bm);
		mapImage.setClickable(true);
		mapImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String query = "http://maps.google.com/maps?q=" + latLngStr;
				Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(query));
				startActivity(mapIntent);
			}
		});
	}

	class CheckinTask extends AsyncTask<String, Void, Checkin> {

		@Override
		protected Checkin doInBackground(String... jsons) {

			for (String json : jsons) {
				DefaultHttpClient client = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(Settings.URL_CHECKIN);
				httpPost.setHeader("X-ZUMO-APPLICATION", Settings.X_ZUMO_KEY);
				httpPost.setHeader("Content-Type", "application/json");
				Logger.log(TAG, json);
				try {
					httpPost.setEntity(new StringEntity(json));

					HttpResponse response = client.execute(httpPost);
					InputStream ips = response.getEntity().getContent();
					BufferedReader buf = new BufferedReader(new InputStreamReader(ips, "UTF-8"));

					StringBuilder sb = new StringBuilder();
					String s;
					while (true) {
						s = buf.readLine();
						if (s == null || s.length() == 0) {
							break;
						}
						sb.append(s);
					}
					buf.close();
					ips.close();

					Logger.log(TAG, "Result: " + sb.toString());

					Gson gson = new Gson();
					Checkin checkin = gson.fromJson(sb.toString(), Checkin.class);
					return checkin;
				} catch (Exception e) {
					// nothing
					e.printStackTrace();
				}
			}

			return null;
		}

		@Override
		public void onPostExecute(Checkin checkin) {
			Logger.log(TAG, "Finished checkin");
			Logger.log(TAG, "checkin: " + checkin.toString());
			finishedCheckin(checkin);
		}

	}

	class YellowTask extends AsyncTask<Void, Void, Void> {
		
		private String what;
		private String where;
		
		public YellowTask(String what, String where) {
			this.what = what;
			this.where = where;
		}
		
		@Override
		protected Void doInBackground(Void... voids) {
			try {
				YellowAPI api = new YellowAPIImpl("en", Settings.YellowAPIKey, "discovr", true);
				JSONObject response = api.findBusiness(this.what, this.where, 1, 40, 0);
				Logger.log(TAG, response.toString());
				// Make sure the response contains results
				if (response.isNull("listings") == false) {
					JSONArray listings = response.getJSONArray("listings");

					for (int i = 0; i < listings.length(); ++i) {
						merchants.add(new Listing(listings.getJSONObject(i)));
						Logger.log(TAG, merchants.get(i).toString());
					}
				}
			} catch (Exception e) {
				Log.e("MerchantFeedError", "Error loading JSON", e);
			}

			return null;
		}
		
		@Override
		protected void onPostExecute(Void nothing) {
			finishYellowTask();
		}
	}

	class GoalActivityTask extends AsyncTask<String, Void, CurrentGoal> {

		@Override
		protected CurrentGoal doInBackground(String... urls) {

			for (String url : urls) {
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				httpGet.setHeader("X-ZUMO-APPLICATION", Settings.X_ZUMO_KEY);

				try {
					// read response into string
					HttpResponse response = client.execute(httpGet);
					InputStream ips = response.getEntity().getContent();
					BufferedReader buf = new BufferedReader(new InputStreamReader(ips, "UTF-8"));

					StringBuilder sb = new StringBuilder();
					String s;
					while (true) {
						s = buf.readLine();
						if (s == null || s.length() == 0)
							break;
						sb.append(s);

					}
					buf.close();
					ips.close();

					// parse json
					Gson gson = new Gson();
					CurrentGoal[] goals = gson.fromJson(sb.toString(), CurrentGoal[].class);
					Logger.log(TAG, goals[0].toString());
					return goals[0];
				} catch (ClientProtocolException e) {
					Logger.log(TAG, e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
					Logger.log(TAG, e.getMessage());
					e.printStackTrace();
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(CurrentGoal goal) {
			refreshComplete(goal);
		}

	}
}
