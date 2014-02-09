package com.speakingcode.geojson;

import java.util.ArrayList;

public class LineString
extends Geometry
{
	ArrayList<double[]> line = new ArrayList<double[]>();
	
	public LineString(ArrayList<double[]> line)
	{
		super();
		this.line = line;
	}
	
	public LineString()
	{
		
	}
	
	public ArrayList<double[]> getLine()
	{
		return line;
	}

	public void setLine(ArrayList<double[]> line)
	{
		this.line = line;
	}
	
	public void addCoordinate(double[] coordinate)
	{
		this.line.add(coordinate);
	}
	
}
