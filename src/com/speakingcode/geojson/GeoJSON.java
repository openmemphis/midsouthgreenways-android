package com.speakingcode.geojson;

public class GeoJSON {
	String type;
	Feature[] features;
	
	public GeoJSON(String type, Feature[] features) {
		super();
		this.type = type;
		this.features = features;
	}
	
	public GeoJSON()
	{
		
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Feature[] getFeatures() {
		return features;
	}
	
	public void setFeatures(Feature[] features) {
		this.features = features;
	}
}
