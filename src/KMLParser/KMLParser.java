package KMLParser;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class KMLParser extends DefaultHandler {

	//public static ArrayList<KMLPlacemark> placemarks;
	private KMLDocument kmlDocument;
	private KMLPlacemark currentPlacemark;
	private KMLLineString currentLineString;
	private StringBuilder currentValue;
	private boolean inPoint;
	
	public KMLDocument getKMLDocument()
	{
	    return kmlDocument;
	}
	
	@Override
	public void startDocument() throws SAXException {
		kmlDocument = new KMLDocument();
		kmlDocument.placemarks = new ArrayList<KMLPlacemark>();
	}
	
	@Override
	public void endDocument() throws SAXException { }
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		
		// reset the current value whenever we start a new element
		currentValue = new StringBuilder();
		
		if (localName.equalsIgnoreCase("Placemark")) {
			this.currentPlacemark = new KMLPlacemark();
			this.currentPlacemark.lineStrings = new ArrayList<KMLLineString>();
		}
		else if (localName.equalsIgnoreCase("LineString") && currentPlacemark != null) {
			currentLineString = new KMLLineString();
			currentLineString.coordinates = new ArrayList<KMLCoordinate>();
		}
		else if (localName.equalsIgnoreCase("point") && currentPlacemark != null) {
			inPoint=true;
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		
		if (this.currentPlacemark != null) {
			if (localName.equalsIgnoreCase("Placemark")) {
				if (currentPlacemark.status != "Under Construction") {
					kmlDocument.placemarks.add(currentPlacemark);
				}
			}
			else if (localName.equalsIgnoreCase("name") && currentPlacemark != null) {
				currentPlacemark.name = currentValue.toString();
			}
			else if (localName.equalsIgnoreCase("description") && currentPlacemark != null) {
				currentPlacemark.description = currentValue.toString();
				
				// The status is in the description. If the status of under construction is somewhere
				// in the description than don't show it
				/*if (currentPlacemark.description.indexOf("<td>Under Construction</td>") > 0) {
					currentPlacemark.status = "Under Construction";
				} else {
					currentPlacemark.status = "Existing";
				}*/
			}
			else if (localName.equalsIgnoreCase("longitude") && currentPlacemark != null) {
				Double longitude = new Double(currentValue.toString());
				currentPlacemark.longitude = longitude.doubleValue();
			}
			else if (localName.equalsIgnoreCase("latitude") && currentPlacemark != null) {
				// if we've hit the latitude tag it means this is a point (greenway or parking lot)
				currentPlacemark.isPoint = true;
				Double latitude = new Double(currentValue.toString());
				currentPlacemark.latitude = latitude.doubleValue();
			}
			else if (localName.equalsIgnoreCase("point")) {
				inPoint = false;
			}
			else if (localName.equalsIgnoreCase("LineString")) {
				currentPlacemark.lineStrings.add(currentLineString);
			}
			else if (localName.equalsIgnoreCase("line")) {
				// if we've hit the line it means that this is a route (greenway path)
				if (inPoint) {
					currentPlacemark.isPoint = true;
				} else {
					currentPlacemark.isPath = true;
				}
				
				// line are first split by a space, than split by comma for lat, long, and alt
				String[] coordinates = currentValue.toString().split(" ");
							
				for (int i=0; i<coordinates.length; i++) {
					String[] coordinateTuple = coordinates[i].split(",");
					
					// all line should have longitude, latitude, and altitude. If not than return
					if (coordinateTuple.length != 3) {
						continue;
					}
					
					if (currentPlacemark.isPath) {
						KMLCoordinate coordinate = new KMLCoordinate();
						coordinate.name = currentPlacemark.name;
						Double longitude = new Double(coordinateTuple[0]);
						coordinate.longitude = longitude.doubleValue();
						Double latitude = new Double(coordinateTuple[1]);
						coordinate.latitude = latitude.doubleValue();
						Double altitude = new Double(coordinateTuple[2]);
						coordinate.altitude = altitude.doubleValue();
						currentLineString.coordinates.add(coordinate);
					}
					else if (currentPlacemark.isPoint) {
						Double longitude = new Double(coordinateTuple[0]);
						currentPlacemark.longitude = longitude;
						Double latitude = new Double(coordinateTuple[1]);
						currentPlacemark.latitude = latitude;
					}
				}
			}
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		
		String s = new String(ch).substring(start, start+length);
		currentValue.append(s);
	}
}
