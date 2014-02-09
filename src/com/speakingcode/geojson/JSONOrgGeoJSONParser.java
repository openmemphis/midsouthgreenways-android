package com.speakingcode.geojson;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class JSONOrgGeoJSONParser
{
	public GeoJSON parseJSON(String geoJSONString)
	{
		Log.d("geojsonstring", geoJSONString);
		GeoJSON gj = new GeoJSON();
		try
		{
			
			JSONObject geoJSON = new JSONObject(geoJSONString);
			JSONArray featuresJSON = geoJSON.getJSONArray("features");
			Feature[] features = new Feature[featuresJSON.length()];

			for (int i = 0; i < featuresJSON.length(); i++)
			{
				Feature f = new Feature();
				JSONObject featureJSON = featuresJSON.getJSONObject(i);
				
				JSONObject propertyJSON = featureJSON.getJSONObject("properties");
				Properties p = new Properties
				(
					propertyJSON.getString("name"),
					propertyJSON.getString("City"),
					propertyJSON.getString("State"),
					propertyJSON.getLong("Miles"),
					propertyJSON.getString("TYPE"),
					propertyJSON.getString("Remarks"),
					propertyJSON.getString("Material"),
					propertyJSON.getString("Facility"),
					propertyJSON.getString("County"),
					propertyJSON.getString("Hours"),
					propertyJSON.getString("Management"),
					propertyJSON.getString("Website"),
					propertyJSON.getString("Location")
				);
				
				f.setProperties(p);
				
				JSONObject geometryJSON = new JSONObject();
				try
				{
					geometryJSON = featureJSON.getJSONObject("geometry");
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				if (geometryJSON == null)
				{
					Log.d("JSONOrgParser", "geom null!!!");
					continue;
				}
				String geometryType = null;
				try
				{
					geometryType = geometryJSON.getString("type");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				if (geometryType == null)
				{
					Log.d("JSONOrgParser", "geom type null!!!");
					continue;
				}
				if(geometryJSON.getString("type").equalsIgnoreCase("LineString"))
				{
					JSONArray lineStringJSON = geometryJSON.getJSONArray("coordinates");
					LineString lineString = new LineString();
					lineString.setType(geometryType);

					for (int j = 0; j < lineStringJSON.length(); j++)
					{
						JSONArray coordinateJSON = lineStringJSON.getJSONArray(j);
						double[] coordinate =
						{
							coordinateJSON.getDouble(0),
							coordinateJSON.getDouble(1),
							coordinateJSON.getDouble(2)
						};
						
						lineString.addCoordinate(coordinate);
					}
					f.setGeometry(lineString);
				}
				else if(geometryJSON.getString("type").equalsIgnoreCase("MultiLineString"))
				{
					JSONArray multiLineStringJSON = geometryJSON.getJSONArray("coordinates");
					MultiLineString multiLineString = new MultiLineString();
					multiLineString.setType(geometryType);
					
					for (int j = 0; j < multiLineStringJSON.length(); j++)
					{
						JSONArray lineStringJSON = multiLineStringJSON.getJSONArray(j);
						LineString lineString = new LineString();
						for (int k = 0; k < lineStringJSON.length(); k++)
						{
							
							JSONArray coordinateJSON = lineStringJSON.getJSONArray(k);
							double[] coordinate =
							{
								coordinateJSON.getDouble(0),
								coordinateJSON.getDouble(1),
								coordinateJSON.getDouble(2)
							};
							
							lineString.addCoordinate(coordinate);
						}
						multiLineString.addLineString(lineString);
					}
					f.setGeometry(multiLineString);
				}
				
				features[i] = f;
			}
			gj.setFeatures(features);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return gj;
	}
}
