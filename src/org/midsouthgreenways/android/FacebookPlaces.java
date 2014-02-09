package org.midsouthgreenways.android;

import java.util.Map;
import java.util.TreeMap;

public class FacebookPlaces {
	private static Map<String, String> facebookPlaces;
	
	// Get the facebook place ID
	public static String getPlaceID(String key) {
		if (key == null) {
			return null;
		}
		
		if (facebookPlaces == null) {
			initializeFacebookPlaces();
		}
		
		if (facebookPlaces.containsKey(key)) {
			return facebookPlaces.get(key);
		}
		return null;
	}
	
	private static void initializeFacebookPlaces() {
		facebookPlaces = new TreeMap<String, String>();
		
		// Raleigh Trails
		facebookPlaces.put("Abbotts Creek Trail", "370259006396301");
		facebookPlaces.put("Baileywick Trail", "384736284940340");
		facebookPlaces.put("Beaver Dam Trail", "199646200170928");
		facebookPlaces.put("Crabtree Creek Trail", "383914818359342");
		facebookPlaces.put("East Fork Mine Creek Trail", "487877657902345");
		facebookPlaces.put("Gardner Street Trail", "295575583885563");
		facebookPlaces.put("Hare Snipe Creek Trail", "487508177939433");
		facebookPlaces.put("Honeycutt Creek Trail", "306892879423490");
		facebookPlaces.put("House Creek Trail", "517797161583337");
		facebookPlaces.put("Lake Johnson Loop Trail", "494686680565996");
		facebookPlaces.put("Lake Lynn Loop Trail", "558675614146863");
		facebookPlaces.put("Little Rock Trail", "115624441930886");
		facebookPlaces.put("Marsh Creek Trail", "378599718892535");
		facebookPlaces.put("Mine Creek Trail", "493401230692538");
		facebookPlaces.put("Neuse River Trail", "138505606298021");
		facebookPlaces.put("Reedy Creek Trail", "111284295618715");
		facebookPlaces.put("Richland Creek Trail", "118460314979207");
		facebookPlaces.put("Rocky Branch Trail", "544650625549824");
		facebookPlaces.put("Shelley Lake Loop Trail", "380716698677761");
		facebookPlaces.put("Simms Branch Trail", "349202555175263");
		facebookPlaces.put("Snelling Branch Trail", "375647195851935");
		facebookPlaces.put("Wakefield Trail", "436538136406543");
		facebookPlaces.put("Walnut Creek Trail", "528257333870286");
	}
}
