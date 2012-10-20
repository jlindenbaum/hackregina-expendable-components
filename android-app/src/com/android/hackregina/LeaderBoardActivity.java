package com.android.hackregina;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.hackregina.models.LeaderPerson;
import com.android.hackregina.utils.Logger;
import com.google.gson.Gson;

public class LeaderBoardActivity extends Activity {

	public static final String TAG = "### LeaderBoardActivity";
	private ProgressDialog progress;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leaderboard);
		
		refreshContent();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_leaderboard, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			refreshContent();
			return true;
		}
		return false;
	}
	
	private void refreshContent() {
		showLoadingSpinner();
		new LeaderBoardTask().execute(Settings.URL_LEADERBOARD);
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

	public void refreshComplete(LeaderPerson[] people) {
		Logger.log(TAG, "DONE");
		ArrayList<LeaderPerson> arrayPeople = new ArrayList<LeaderPerson>();
		for (int i = 0; i < people.length; i++) {
			arrayPeople.add(people[i]);
		}

		LeaderBoardAdapter adapter = new LeaderBoardAdapter(this, R.id.leaderboardItem_name, arrayPeople);
		ListView listView = (ListView) findViewById(R.id.leaderboard_listView);
		listView.setAdapter(adapter);
		hideLoadingSpinner();
	}

	class LeaderBoardTask extends AsyncTask<String, Void, LeaderPerson[]> {

		public static final String TAG = "### LeaderBoardTask";

		@Override
		protected LeaderPerson[] doInBackground(String... urls) {

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
					LeaderPerson[] people = gson.fromJson(sb.toString(), LeaderPerson[].class);
					Logger.log(TAG, "Count: " + people.length);
					return people;
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
		protected void onPostExecute(LeaderPerson[] people) {
			refreshComplete(people);
		}

	}
}
