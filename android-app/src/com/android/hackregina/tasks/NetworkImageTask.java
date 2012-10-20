package com.android.hackregina.tasks;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.android.hackregina.interfaces.NetworkImageTaskCallback;

public class NetworkImageTask extends AsyncTask<String, Void, Bitmap> {

	public static final String TAG = "### NetworkImageTask";

	private NetworkImageTaskCallback callback;

	public NetworkImageTask(NetworkImageTaskCallback callback) {
		this.callback = callback;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		for (String url : params) {
			try {
				URL aURL = new URL(url);
				URLConnection conn = aURL.openConnection();
				conn.connect();
				InputStream is = conn.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				Bitmap bm = BitmapFactory.decodeStream(bis);
				bis.close();
				is.close();
				return bm;
			} catch (Exception e) {
				Log.e(TAG, "Error in bitmap: " + e.getMessage());
				e.printStackTrace();
			}
		}

		return null;
	}

	@Override
	protected void onPostExecute(Bitmap bm) {
		if (this.callback != null) {
			this.callback.loadBitmapComplete(bm);
		}
	}

}
