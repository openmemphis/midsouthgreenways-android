package org.midsouthgreenways.android;

import org.midsouthgreenways.android.android.map.MapViewerActivity;

import KMLParser.JSONParser;
import KMLParser.KMLDocument;
import Weather.WeatherSet;
import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;

public class MapInitializationTask extends AsyncTask<String, Void, KMLDocument> {

	private MapViewerActivity mainActivity;
	
	public MapInitializationTask(MapViewerActivity activity) {
		mainActivity = activity;
	}

	@Override
	protected KMLDocument doInBackground(String... arg0) {
		long startTime = System.currentTimeMillis();
		
		JSONParser jsonParser = new JSONParser(mainActivity); 
		KMLDocument doc = jsonParser.parseJSON(arg0[0]);
		
		//KMLDocument doc = mainActivity.loadKMLDocument();
		long endTime = System.currentTimeMillis() - startTime;
		//String myTime = String.format("%d", endTime)d
		//Log.i("Timer", String.valueOf(endTime/1000));
		return doc;
	}
	
	// Update the map
	@Override
	protected void onPostExecute(KMLDocument result) {
    	//mainActivity.MapInitPostExecute(result);
	}
}
