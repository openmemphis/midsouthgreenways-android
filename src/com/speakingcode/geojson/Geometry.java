package com.speakingcode.geojson;

public class Geometry
implements IGeometry
{
	String type;
	
	@Override
	public String getType()
	{
		return type;
	}
	
	@Override
	public void setType(String type)
	{
		this.type = type;
	}
}
