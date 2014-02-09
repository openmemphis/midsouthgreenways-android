package KMLParser;

import java.util.ArrayList;

public class KMLPlacemark {
	public String name;
	public String description;
	public String surfaceType;
	public String address;
	public String parkingType;
	public double distance;
	public String status;
	public double longitude;
	public double latitude;
	public ArrayList<KMLLineString> lineStrings;
	
	
	public boolean isPoint;
	public boolean isPath;
}
