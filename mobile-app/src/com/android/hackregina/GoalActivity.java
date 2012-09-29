package com.android.hackregina;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;

import com.android.hackregina.models.CurrentGoal;
import com.android.hackregina.utils.Logger;
import com.google.gson.Gson;

public class GoalActivity extends Activity {

	public static final String TAG = "### GoalActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goal);

		Logger.log(TAG, "Starting...");
		new GoalActivityTask().execute(new String[] { Settings.URL_CURRENT_GOAL });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void refreshComplete(CurrentGoal goal) {
		Logger.log(TAG, "DONE");
		renderCurrentGoal(goal);
	}
	
	private void renderCurrentGoal(CurrentGoal goal) {
		
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