package com.speakingcode.geojson;

import android.util.Log;

public class GeoJSONParserManager
{
	private IGeoJSONParserClient geoJSONParserClient;
	private JSONOrgGeoJSONParser geoJSONParser = new JSONOrgGeoJSONParser();
	
	public GeoJSONParserManager()
	{
		
	}
	
	public void setGeoJSONParserClient(IGeoJSONParserClient geoJSONParserClient)
	{
		this.geoJSONParserClient = geoJSONParserClient;
	}
	
	public void parseGeoJSON(final String geoJSONString)
	{
		new Thread()
		{
			public void run()
			{
				//Log.d("GeoJSONParserManager", "parseGeoJSON started!!");
				GeoJSON gj = GeoJSONParserManager.this.geoJSONParser.parseJSON(geoJSONString);
				
				GeoJSONParserManager.this.geoJSONParserClient.onGeoJSONParseDone
				(
					gj
				);
			
			}
		}.start();
	}
}
