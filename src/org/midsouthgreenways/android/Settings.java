package org.midsouthgreenways.android;

import org.midsouthgreenways.android.android.map.MapViewerActivity;

import android.content.SharedPreferences;

public class Settings {

	private static final String PREFS_NAME = "RGreenwayPrefs";
	private MapViewerActivity mainActivity;
	
	public Settings(MapViewerActivity activity) {
		mainActivity = activity;
	}
	
	public void putBoolean(String key, boolean value) {
		SharedPreferences settings = mainActivity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("voiceFeedback", value);
		editor.commit();
	}
		
	public boolean getBoolean(String key) {
		SharedPreferences settings = mainActivity.getSharedPreferences(PREFS_NAME, 0);
	    return settings.getBoolean("voiceFeedback", true);
	}
}
