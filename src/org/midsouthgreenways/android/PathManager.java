package org.midsouthgreenways.android;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;
import android.util.Log;
import KMLParser.KMLCoordinate;
import KMLParser.KMLDocument;
import KMLParser.KMLLineString;
import KMLParser.KMLPlacemark;

public class PathManager {

	private final float GREENWAY_SLICE_UPDATE_DISTANCE = 400;
	private final float GREENWAY_BUFFER_DISTANCE = 30;
	private final float GREENWAY_EXTENDED_BUFFER_DISTANCE = 40;
	
	private Location lastDataRefreshLocation = null;
	private List<KMLCoordinate> greenwaySliceCoordinates;
	private String lastGreenwayFound = null;
	
	public String getGreenwayTrailFromCoordinates(Location currentLocation, KMLDocument doc) {
		if (currentLocation == null || doc == null) 
			return null;
		
		// Don't bother if the GPS isn't very accurate at the moment
		if (currentLocation.getAccuracy() > 50) {
			//return "Not accurate";
			return null;
		}
		
		// TODO: Right now just add all items to the greenway slice. Later we may change this back to be a certain distance
		/*if (greenwaySliceCoordinates == null) {
			greenwaySliceCoordinates = new ArrayList<KMLCoordinate>();
			for (int i=0; i<doc.placemarks.size(); i++) {
				KMLPlacemark currentPlacemark = doc.placemarks.get(i);
				if (currentPlacemark.isPath) {
					for (int j=0; j<currentPlacemark.lineStrings.size(); j++) {
						for (int k=0; k<currentPlacemark.lineStrings.get(j).coordinates.size(); k++) {
							Location coordinateLocation = new Location("");
							coordinateLocation.setLatitude(currentPlacemark.lineStrings.get(j).coordinates.get(k).latitude);
							coordinateLocation.setLongitude(currentPlacemark.lineStrings.get(j).coordinates.get(k).longitude);
		                    greenwaySliceCoordinates.add(currentPlacemark.lineStrings.get(j).coordinates.get(k));
						}
					}
				}
			}
			lastDataRefreshLocation = currentLocation;
		}*/
		
		// If we are 400 meters away from the last location that we got the slice of greenway line, refresh 1
		//double distanceFromLastUpdate = currentLocation.distanceTo(lastDataRefreshLocation);
		//if (lastDataRefreshLocation == null || currentLocation.distanceTo(lastDataRefreshLocation) >= GREENWAY_SLICE_UPDATE_DISTANCE) {
			//lastDataRefreshLocation = currentLocation;
			//refreshGreenwaySlice(doc);
			//return "Refreshed locations";
		//}
		
		
		boolean needsUpdate = true;
		if (lastDataRefreshLocation != null) {
			needsUpdate = lastDataRefreshLocation.distanceTo(currentLocation) >= GREENWAY_SLICE_UPDATE_DISTANCE;
		}
		if (needsUpdate) {
			lastDataRefreshLocation = currentLocation;
			refreshGreenwaySlice(doc);
			//return "Refreshed locations";
		}
		
	    // Go through the greenway slice and see if the user is a certain distance away from any of the greenway line. 
	    // The coordinate that is the closest will be the current greenway they are on. If there are no line within that
	    // distance, than return nothing.
	    //double coordinateDistance = -1;
	    String greenwayName = null;
	    double minDistance = -1;
	    //Log.i("greenway slice coords", String.valueOf(greenwaySliceCoordinates.size()));
	    for (int i=0; i<greenwaySliceCoordinates.size(); i++) {
	    	KMLCoordinate currentCoordinate = greenwaySliceCoordinates.get(i);
	    	Location coordinateLocation = new Location("");
	    	coordinateLocation.setLongitude(currentCoordinate.longitude);
	    	coordinateLocation.setLatitude(currentCoordinate.latitude);
	    	float distance = currentLocation.distanceTo(coordinateLocation);
	    	//if ((distance <= GREENWAY_BUFFER_DISTANCE || (currentCoordinate.name == lastGreenwayFound && distance <= GREENWAY_EXTENDED_BUFFER_DISTANCE)) && (coordinateDistance == -1 || distance < coordinateDistance)) {
	        if (distance <= GREENWAY_BUFFER_DISTANCE && (minDistance == -1 || minDistance > distance)) {
	        	greenwayName = currentCoordinate.name;
	        	minDistance = distance;
	            //Log.i("Closest greenway", greenwayName);
	        }
	        if (minDistance == -1 || minDistance > distance) {
	    		minDistance = distance;
	    		//Log.i("new distance", String.valueOf(minDistance));
	    	}
	    }
	    
	    lastGreenwayFound = greenwayName;
	    return greenwayName;
	    /*String myMessage = greenwayName == null ? "No greenway" : greenwayName;
	    Log.i("Final greenway", myMessage);
	    if (greenwayName == null) {
	    	return String.format("%d, %.2f, %.2f", greenwaySliceCoordinates.size(), minDistance, currentLocation.distanceTo(lastDataRefreshLocation));
	    } else {
	    	return String.format("%s (%.2f)", greenwayName, minDistance);
	    }*/
	}
	
	// Still need to work on this guy
	public ArrayList<TrailInformation> getClosestTrails(Location currentLocation, KMLDocument doc, double distance) {
		ArrayList<TrailInformation> closestTrails = new ArrayList<TrailInformation>();
		for (int i=0; i<doc.placemarks.size(); i++) {
			KMLPlacemark currentPlacemark = doc.placemarks.get(i);
			if (currentPlacemark.isPoint) {
	            
				// if this is a parking lot we want to see if it is closes to any close trail
	            if (currentPlacemark.name.startsWith("Parking Area")) {
	                // do something around finding the closest parking
	            } else {
	            	Location coordinateLocation = new Location("");
	    	    	coordinateLocation.setLongitude(currentPlacemark.longitude);
	    	    	coordinateLocation.setLatitude(currentPlacemark.latitude);
	    	    	float coordinateDistance = currentLocation.distanceTo(coordinateLocation);
	                if (coordinateDistance <= distance) {
	                    
	                    // see if this trail already exists, if it does, update the distance if it is less
	                    boolean wasFound = false;
	                    for (int j=0; j<closestTrails.size(); j++) {
	                    	TrailInformation addedTrailInfo = closestTrails.get(j);
	                    	if (addedTrailInfo.trailName.equals(currentPlacemark.name)) {
	                    		wasFound = true;
	                    		addedTrailInfo.distanceFromLocation = addedTrailInfo.distanceFromLocation > coordinateDistance ? coordinateDistance : addedTrailInfo.distanceFromLocation;
	                    	}
	                    }
	                    
	                    if (!wasFound) {
	                        TrailInformation trailInformation = new TrailInformation();
	                        trailInformation.trailName = currentPlacemark.name;
	                        trailInformation.trailDescription = currentPlacemark.description;
	                        trailInformation.distanceFromLocation = coordinateDistance;
	                        trailInformation.location = coordinateLocation;
	                        closestTrails.add(trailInformation);
	                    }
	                }
	            }
	        }
		}
		return closestTrails;
	}
	
	// Every 400 meters, refresh the slice of greenway that we want to look at. This is a performance improvement so that we are
	// only looking at greenway line that are near to us instead of the entire greenway system
	private void refreshGreenwaySlice(KMLDocument doc) {
		greenwaySliceCoordinates = new ArrayList<KMLCoordinate>();
		for (int i=0; i<doc.placemarks.size(); i++) {
			KMLPlacemark currentPlacemark = doc.placemarks.get(i);
			if (currentPlacemark.isPath) {
				for (int j=0; j<currentPlacemark.lineStrings.size(); j++) {
					KMLLineString currentLineString = currentPlacemark.lineStrings.get(j);
					for (int k=0; k<currentLineString.coordinates.size(); k++) {
						Location coordinateLocation = new Location("");
						coordinateLocation.setLatitude(currentLineString.coordinates.get(k).latitude);
						coordinateLocation.setLongitude(currentLineString.coordinates.get(k).longitude);
						double distance = lastDataRefreshLocation.distanceTo(coordinateLocation);
						if (distance <= GREENWAY_SLICE_UPDATE_DISTANCE) {
	                        greenwaySliceCoordinates.add(currentLineString.coordinates.get(k));
	                        //KMLCoordinate currentCoordinate = currentPlacemark.lineStrings.get(j).coordinates.get(k);
	                        //Log.i("Refresh", String.valueOf(currentCoordinate.name));
	                    }
					}
				}
			}
		}
		//Log.i("Refresh", String.valueOf(greenwaySliceCoordinates.size()));
	}
}