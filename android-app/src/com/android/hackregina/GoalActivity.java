package com.android.hackregina;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hackregina.interfaces.CheckinTaskCallback;
import com.android.hackregina.interfaces.GetGoalTaskCallback;
import com.android.hackregina.interfaces.NetworkImageTaskCallback;
import com.android.hackregina.models.Checkin;
import com.android.hackregina.models.CurrentGoal;
import com.android.hackregina.tasks.CheckinTask;
import com.android.hackregina.tasks.GetGoalTask;
import com.android.hackregina.tasks.NetworkImageTask;
import com.android.hackregina.utils.Logger;
import com.android.hackregina.utils.SharedPreferencesUtil;

public class GoalActivity extends Activity implements NetworkImageTaskCallback, GetGoalTaskCallback, CheckinTaskCallback {

	public static final String TAG = "### GoalActivity";

	private Integer objectId;
	private String latLngStr = "";
	private String lat = "";
	private String lon = "";
	private String userId;
	private ProgressDialog progress;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goal);
	}

	@Override
	public void onResume() {
		super.onResume();

		// get user id from shared pref
		this.userId = (String) SharedPreferencesUtil.readSharedPreference(getApplicationContext(), SharedPreferencesUtil.KEY_USER_ID);

		if (this.userId == null) {
			// start user id activity
			Intent intent = new Intent("com.android.hackregina.EnterUserIdActivity");
			startActivity(intent);
		} else {
			Logger.log(TAG, "Starting...");
			// get the current goal task
			showLoadingSpinner();
			new GetGoalTask(this).execute(new String[] { Settings.URL_CURRENT_GOAL });
		}
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

	public void refreshCurrentGoalComplete(CurrentGoal goal) {
		Logger.log(TAG, "DONE");
		renderCurrentGoal(goal);
		this.hideLoadingSpinner();
	}

	public void startYelpActivity(View v) {
		Intent intent = new Intent("com.android.hackregina.YelpActivity");
		intent.putExtra("lat", this.lat);
		intent.putExtra("lng", this.lon);
		startActivity(intent);
	}

	public void startYellowActivity(View v) {
		Intent intent = new Intent("com.android.hackregina.YellowActivity");
		intent.putExtra("lat", this.lat);
		intent.putExtra("lng", this.lon);
		startActivity(intent);
	}

	public void completeCheckin(View v) {
		String checkinJSON = "{\"ObjectId\":" + this.objectId + ", \"UserId\":\"" + this.userId + "\"}";
		this.showLoadingSpinner();
		new CheckinTask(this).execute(new String[] { checkinJSON });
	}

	private void finishedCheckin(Checkin checkin) {
		if (checkin == null) {
			AlertDialog alert = new AlertDialog.Builder(this).create();
			alert.setTitle("Oops!");
			alert.setMessage("That checkin didn't work, try again later.");
			alert.show();
			return;
		}

		// set points
		Button goalButton = (Button) findViewById(R.id.goal_checkinButton);
		goalButton.setText("ACCOMPLISHED!");
		goalButton.setOnClickListener(null);
		goalButton.setActivated(false);

		this.hideLoadingSpinner();
		AlertDialog alert = new AlertDialog.Builder(this).create();
		alert.setTitle("Dora The Explorer!");
		alert.setMessage("You've checked in. That's awesome. Checkout what's around!");
		alert.show();
	}

	private void renderCurrentGoal(CurrentGoal goal) {
		if (goal == null) {
			AlertDialog alert = new AlertDialog.Builder(this).create();
			alert.setTitle("Oops");
			alert.setMessage("Seems something went wrong. Please try again later.");
			alert.show();
			return;
		}
		this.objectId = goal.getObjectId();
		this.latLngStr = goal.getLat() + "," + goal.getLong();
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
		new NetworkImageTask(this).execute(new String[] { imageUri });
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

	@Override
	public void goalUpdateTaskComplete(CurrentGoal goal) {
		refreshCurrentGoalComplete(goal);
	}

	@Override
	public void checkinTaskComplete(Checkin checkin) {
		finishedCheckin(checkin);
	}

}
