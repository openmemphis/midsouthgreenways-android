package org.midsouthgreenways.android;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.midsouthgreenways.android.android.map.MapViewerActivity;

import KMLParser.KMLDocument;
import android.location.Location;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

public class ClosestTrailsViewController {

	private int CLOSEST_TRAILS_DISTANCE = 8047;
	private MapViewerActivity mainActivity;
	private ArrayList<TrailInformation> closestTrails;
	
	private static Comparator<TrailInformation> COMPARATOR = new Comparator<TrailInformation>()
    {
        public int compare(TrailInformation o1, TrailInformation o2)
        {
            if (o1.distanceFromLocation == o2.distanceFromLocation) {
            	return 0;
            } else if (o1.distanceFromLocation > o2.distanceFromLocation) {
            	return 1;
            } else if (o1.distanceFromLocation < o2.distanceFromLocation) {
            	return -1;
            }
            return 0;
        }
    };

	public ClosestTrailsViewController(MapViewerActivity activity) {
		mainActivity = activity;
	}
	
	public void setListView(Location currentLocation, KMLDocument doc) {
		
		RelativeLayout closestTrailsLayout = (RelativeLayout)mainActivity.findViewById(R.layout.closesttrailsview);
		final ListView lv= (ListView)closestTrailsLayout.findViewById(R.id.closestTrailsListView);
		
		// create the grid item mapping
		String[] from = new String[] {"greenwayNameTextView", "greenwayDistanceTextView", "greenwayDescriptionTextView"};
        int[] to = new int[] { R.id.greenwayNameTextView, R.id.greenwayDistanceTextView, R.id.greenwayDescriptionTextView };

        // get the list of the closest trails
        PathManager pathManager = new PathManager();
        closestTrails = pathManager.getClosestTrails(currentLocation, doc, CLOSEST_TRAILS_DISTANCE);
        
        // sort the closest trails collectinon based on distance from location
        Collections.sort(closestTrails, COMPARATOR);
        
        // prepare the list of all records
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        for(int i = 0; i < closestTrails.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            
            String trailName = closestTrails.get(i).trailName;
            int trailParens = trailName.indexOf('(');
            if (trailParens > 0) {
            	trailName = trailName.substring(0,trailParens);
            }
            map.put("greenwayNameTextView", trailName);
            
            String distanceText;
//            if (closestTrails.get(i).trailName.equals(mainActivity.getCurrentGreenway())) {
//            	distanceText = "You are here     ";
//            } else {
//            	double totalDistanceMiles = (closestTrails.get(i).distanceFromLocation/1000) * .6214;
//            	distanceText = String.format("%.1f Miles Away     ", totalDistanceMiles);
//            }
//            map.put("greenwayDistanceTextView", distanceText);
            map.put("greenwayLongitude", String.valueOf(closestTrails.get(i).location.getLongitude()));
            map.put("greenwayLatitude", String.valueOf(closestTrails.get(i).location.getLatitude()));
            
            // remove html tags and the length and surface type descriptions
            String trailDescription = closestTrails.get(i).trailDescription;
         
            		/*.replaceAll("\\<.*?>", "");
            int locationOfLength = trailDescription.indexOf("Length:");
            if (locationOfLength > 0) {
            	trailDescription = trailDescription.substring(0, locationOfLength);
            }*/
            
            map.put("greenwayDescriptionTextView", trailDescription);
            fillMaps.add(map);
        }
 
        // fill in the grid_item layout
        SimpleAdapter adapter = new SimpleAdapter(mainActivity, fillMaps, R.layout.closesttrailsview_griditem, from, to);
        lv.setAdapter(adapter);
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				HashMap<String, String> map = (HashMap<String, String>)lv.getItemAtPosition(arg2);
				TrailInformation trail = getTrail(Double.parseDouble(map.get("greenwayLongitude")), Double.parseDouble(map.get("greenwayLatitude")));
				if (trail != null) {
//					mainActivity.resignAllViews();
//					mainActivity.recenterMap(trail.location);
//					mainActivity.showAnnotation(trail.trailName, trail.trailDescription, trail.location);
				}
			}
        });
	}
	
	// Get the trail from the trailname
	private TrailInformation getTrail(double longitude, double latitude) {
		for (int i=0; i<closestTrails.size(); i++) {
			//String closestTrailName = closestTrails.get(i).trailName;
			TrailInformation currentTrail = closestTrails.get(i);
			/*int trailParens = closestTrailName.indexOf('(');
            if (trailParens > 0) {
            	closestTrailName = closestTrailName.substring(0,trailParens);
            }*/
			//if (closestTrailName == trailName) {
			if (currentTrail.location.getLatitude() == latitude && currentTrail.location.getLongitude() == longitude) {
				return closestTrails.get(i);
			}
		}
		return null;
	}
	
}
