package com.speakingcode.geojson;

public class GeoJSONParserManager
{
	private IGeoJSONParserClient geoJSONParserClient;
	private GeoJSONParser geoJSONParser = new GeoJSONParser();
	
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
				GeoJSONParserManager.this.geoJSONParserClient.onGeoJSONParseDone
				(
					GeoJSONParserManager.this.geoJSONParser.parseJson(geoJSONString)
				);
			}
		}.start();
	}
}
