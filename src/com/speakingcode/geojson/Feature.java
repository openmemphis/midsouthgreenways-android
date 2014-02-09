package com.speakingcode.geojson;

public class Feature
{
	Properties properties;
	IGeometry geometry;
	
	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	public IGeometry getGeometry() {
		return geometry;
	}
	public void setGeometry(IGeometry geometry) {
		this.geometry = geometry;
	}
	
	
}
