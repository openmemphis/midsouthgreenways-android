package org.midsouthgreenways.android;

import org.midsouthgreenways.android.android.map.MapViewerActivity;

import KMLParser.KMLDocument;
import android.location.Location;
import android.os.AsyncTask;

public class LocateCurrentGreenwayTask extends AsyncTask<CurrentGreenwayTaskParams, Void, String> {

	private MapViewerActivity mainActivity;
	//private PathManager pathManager;
	//private KMLDocument kmlDocument;
	//private boolean isExecuting = false;
	
	public LocateCurrentGreenwayTask(MapViewerActivity activity) {
		mainActivity = activity;
		//pathManager = manager;
		//kmlDocument = doc;
	}
	
	//public boolean isExecuting() {
	//	return isExecuting;
	//}
	
	@Override
	protected String doInBackground(CurrentGreenwayTaskParams... arg0) {
		//isExecuting = true;
		return arg0[0].pathManager.getGreenwayTrailFromCoordinates(arg0[0].location, arg0[0].kmlDocument);
	}

	protected void onPostExecute(String newGreenway) {
//		mainActivity.currentGreenwayPostExecute(newGreenway);
		//isExecuting = false;
	}
}
