package com.android.hackregina.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

import com.android.hackregina.Settings;
import com.android.hackregina.interfaces.GetGoalTaskCallback;
import com.android.hackregina.models.CurrentGoal;
import com.android.hackregina.utils.Logger;
import com.google.gson.Gson;

public class GetGoalTask extends AsyncTask<String, Void, CurrentGoal> {
	
	private static final String TAG = "### GoalActivityTask";
	private GetGoalTaskCallback callback;

	public GetGoalTask(GetGoalTaskCallback callback) {
		this.callback = callback;
	}
	
	@Override
	protected CurrentGoal doInBackground(String... urls) {

		for (String url : urls) {
			Logger.log(TAG, "Loading: " + url);
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("X-ZUMO-APPLICATION", Settings.X_ZUMO_KEY);
			httpGet.setHeader("Accept", "application/json");

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
				Logger.log(TAG, "Response: " + sb.toString());
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
		if (this.callback != null) {
			this.callback.goalUpdateTaskComplete(goal);
		}
	}

}
