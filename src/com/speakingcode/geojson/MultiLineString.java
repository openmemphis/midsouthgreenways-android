package com.speakingcode.geojson;

import java.util.ArrayList;

public class MultiLineString
extends Geometry
{
	ArrayList<LineString> lineStrings = new ArrayList<LineString>();

	public MultiLineString(ArrayList<LineString> lineStrings)
	{
		super();
		this.lineStrings = lineStrings;
	}
	
	public MultiLineString()
	{
		
	}

	public ArrayList<LineString> getLineStrings()
	{
		return lineStrings;
	}

	public void setLineStrings(ArrayList<LineString> lineStrings)
	{
		this.lineStrings = lineStrings;
	}
	
	public void addLineString(LineString lineString)
	{
		this.lineStrings.add(lineString);
	}
}
