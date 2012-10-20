package com.android.hackregina.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.hackregina.Settings;

public class SharedPreferencesUtil {

	private static final String TAG = "### SharedPreferencesUtil";
	private static final String PREF_NAME = "discovr_settings";

	public static final String KEY_USER_ID = "user_id";

	/**
	 * Store a key:value pair in the PREF_NAME shared preferences. Use the keys provided.
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void storeSharedPreference(Context context, String key, Object value) {
		SharedPreferences settings = context.getSharedPreferences(PREF_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();

		if (Settings.DEBUG) {
			Log.d(TAG, "Storing: " + key + " value: " + value);
		}

		if (value instanceof String) {
			editor.putString(key, (String) value);
		} else if (value instanceof Integer) {
			editor.putInt(key, (Integer) value);
		}

		editor.commit();
	}

	/**
	 * Read the key from PREF_NAME shared preferences.
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static Object readSharedPreference(Context context, String key) {
		SharedPreferences settings = context.getSharedPreferences(PREF_NAME, 0);

		if (KEY_USER_ID.equalsIgnoreCase(key)) {
			return settings.getString(KEY_USER_ID, null);
		}

		return null;
	}
}
