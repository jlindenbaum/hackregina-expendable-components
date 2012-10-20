package com.android.hackregina;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// initialize tabs
		TabHost tabHost = getTabHost();
		TabSpec tabSpec;
		Intent tabIntent;

		// current goal
		tabIntent = new Intent("com.android.hackregina.GoalActivity");
		tabSpec = tabHost.newTabSpec("current_goal").setIndicator("Goal").setContent(tabIntent);
		tabHost.addTab(tabSpec);
		
		// leader board
		tabIntent = new Intent("com.android.hackregina.LeaderBoardActivity");
		tabSpec = tabHost.newTabSpec("leader_board").setIndicator("Scores").setContent(tabIntent);
		tabHost.addTab(tabSpec);
	}
}
