package com.android.hackregina;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

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
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hackregina.models.Checkin;
import com.android.hackregina.models.CurrentGoal;
import com.android.hackregina.utils.Logger;
import com.google.gson.Gson;

public class GoalActivity extends Activity implements NetworkImageTaskInterface {

	public static final String TAG = "### GoalActivity";
	
	private Integer objectId;
	private String latLngStr = "";
	private ProgressDialog progress;

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

	public void completeCheckin(View v) {
		String checkinJSON = "{\"ObjectId\":" + this.objectId + ", \"UserId\":\"" + this.getGoogleAccount() + "\"}";
		this.showLoadingSpinner();
		new CheckinTask().execute(new String[] { checkinJSON });
	}
	
	private void finishedCheckin(Checkin checkin) {
		this.hideLoadingSpinner();
		AlertDialog alert = new AlertDialog.Builder(this).create();
		alert.setTitle("Dora The Explorer!");
		alert.setMessage("You've checked in. That's awesome. More goals tomorrow!");
		alert.show();
		
		// set points
		Button goalButton = (Button) findViewById(R.id.goal_checkinButton);
		goalButton.setText("ACCOMPLISHED!");
		goalButton.setOnClickListener(null);
		goalButton.setActivated(false);
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
