package com.android.hackregina.tasks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

import com.android.hackregina.Settings;
import com.android.hackregina.interfaces.CheckinTaskCallback;
import com.android.hackregina.models.Checkin;
import com.android.hackregina.utils.Logger;
import com.google.gson.Gson;

public class CheckinTask extends AsyncTask<String, Void, Checkin> {
	
	private static final String TAG = "### CheckinTask";
	private CheckinTaskCallback callback;

	public CheckinTask(CheckinTaskCallback callback) {
		this.callback = callback;
	}
	
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
		if (this.callback != null) {
			this.callback.checkinTaskComplete(checkin);
		}
	}

}