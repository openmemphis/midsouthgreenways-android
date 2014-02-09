package com.speakingcode.geojson;


import com.google.gson.Gson;

public class GeoJSONParser
{

	private IGeoJSONParserClient client;

	public GeoJSON parseJson(String geoJSONString)
	{
		Gson gson = new Gson();
		return gson.fromJson(geoJSONString, GeoJSON.class);
	}

}
