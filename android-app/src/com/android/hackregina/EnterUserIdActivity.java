package com.android.hackregina;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.hackregina.utils.Logger;
import com.android.hackregina.utils.SharedPreferencesUtil;

public class EnterUserIdActivity extends Activity {

	private static final String TAG = "### EnterUserIdActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_user_id);
	}
	
	public void saveUserId(View v) {
		EditText userIdField = (EditText) findViewById(R.id.enterUserIdField);
		
		String userId = userIdField.getText().toString();
		Logger.log(TAG, "Saving user: " + userId);
		
		if ("".equalsIgnoreCase(userId) == false) {
			SharedPreferencesUtil.storeSharedPreference(getApplicationContext(), SharedPreferencesUtil.KEY_USER_ID, userId);
			finish();
		}
		else {
			AlertDialog alert = new AlertDialog.Builder(this).create();
			alert.setTitle("Oops!");
			alert.setMessage("Make sure this is your email, and it's not empty!");
			alert.show();
		}
	}
}
