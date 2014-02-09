package org.midsouthgreenways.android;

import java.util.Map;
import java.util.TreeMap;

public class TriangleWikiPages {
	private static Map<String, String> wikiPages;
	
	// get the TriangleWiki page if one exists
	public static String getPage(String key) {
		if (wikiPages == null) {
			initializeWikiPages();
		}
		
		if (wikiPages.containsKey(key)) {
			return wikiPages.get(key);
		}
		return null;
	}
	
	private static void initializeWikiPages() {
		wikiPages = new TreeMap<String, String>();
		wikiPages.put("Rocky Branch Trail", "https://trianglewiki.org/Rocky_Branch_Greenway");
		wikiPages.put("Walnut Creek Trail", "https://trianglewiki.org/Walnut_Creek_Greenway");
		wikiPages.put("Reedy Creek Trail", "https://trianglewiki.org/Reedy_Creek_Greenway");
		wikiPages.put("Little Rock Trail", "https://trianglewiki.org/Little_Rock_Greenway");
		wikiPages.put("Neuse River Trail", "https://trianglewiki.org/Neuse_River_Greenway");
	}
}
