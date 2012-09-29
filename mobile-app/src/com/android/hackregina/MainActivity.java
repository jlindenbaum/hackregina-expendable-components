package com.android.hackregina;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	// protected String getGoogleAccount() {
	// AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
	// Account[] list = manager.getAccounts();
	// String gmail = null;
	//
	// for (Account account : list) {
	// if (account.type.equalsIgnoreCase("com.google")) {
	// return account.name;
	// }
	// }
	//
	// return gmail;
	// }
}
