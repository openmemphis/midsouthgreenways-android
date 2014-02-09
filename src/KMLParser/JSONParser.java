package KMLParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.midsouthgreenways.android.android.map.MapViewerActivity;
import org.xml.sax.InputSource;

import android.location.Location;
import android.util.Log;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class JSONParser {
	
	private MapViewerActivity mainActivity;
	
	public JSONParser(MapViewerActivity activity) {
		mainActivity = activity;
	}
	
	public KMLDocument parseJSON(String greenwayFile) {
		KMLDocument kmlDocument = new KMLDocument();
		kmlDocument.placemarks = new ArrayList<KMLPlacemark>();
		
		JsonFactory f = new JsonFactory();
		JsonParser jp;
		try {
			jp = f.createJsonParser(mainActivity.getAssets().open(greenwayFile));
			
			// This should return start array so just skip over it
			JsonToken token = jp.nextToken(); 
			token = jp.nextToken();
			
			// This will always keep track of the current placemark
			KMLPlacemark placemark = null;
			KMLCoordinate coordinate = null;
			KMLLineString lineString = null;
			String fieldname = null;
			
			while (token != JsonToken.END_ARRAY) {
				
				// This is the name of the current element
				fieldname = jp.getCurrentName();
				
				// This means we are starting a new placemark
				if (token == JsonToken.START_OBJECT) {
					placemark = new KMLPlacemark();
					placemark.lineStrings = new ArrayList<KMLLineString>();
				
				// This means we are ending a placemark and it should be added to the list2
				} else if (token == JsonToken.END_OBJECT) {
					kmlDocument.placemarks.add(placemark);
				
				// This means we are at the name element
				} else if ("n".equals(fieldname)) {
					jp.nextToken();
					placemark.name = jp.getText();
					//Log.i("trail name", placemark.name);
				
				} else if ("d".equals(fieldname)) {
					jp.nextToken();
					placemark.description = jp.getText();
					
				} else if ("m".equals(fieldname)) {
					jp.nextToken();
					if (!"".equals(jp.getText())) {
						placemark.distance = Double.parseDouble(jp.getText());
					}
					
				} else if ("pa".equals(fieldname)) {
					jp.nextToken();
					placemark.address = jp.getText();	
					
				} else if ("pt".equals(fieldname)) {
					jp.nextToken();
					placemark.parkingType = jp.getText();
					
				// This means we are at the coordinate element
				} else if ("c".equals(fieldname)) {
					
					// This should return the start array so just skip over
					jp.nextToken();
					token = jp.nextToken();
					
					// Go through each of the coordinates
					while (token != JsonToken.END_ARRAY) {
						
						// Go through each of the coordinates
						String coordinates = jp.getText();
						boolean connectToBeginningOfString = false;
						boolean connectToEndOfString = false;
						int beginningOfStringCounter = 0;
						lineString = new KMLLineString();
						lineString.coordinates = new ArrayList<KMLCoordinate>();
						
						int pos = 0;
						int end = 0;
						String coordinateArray = null;
						boolean doneProcessing = false;
						
						while (!doneProcessing) {
							end = coordinates.indexOf(' ', pos);
							if (end >= 0) {
								coordinateArray = coordinates.substring(pos,end);
								pos = end + 1;
							} else {
								coordinateArray = coordinates.substring(pos);
								doneProcessing = true;
							}
						
						//while ((end = coordinates.indexOf(' ', pos)) >= 0) {
							//coordinateArray = coordinates.substring(pos,end);
							//pos = end + 1;
							
							// This is for using integer parsing instead of a double
							int commaIndex = coordinateArray.indexOf(',');
							coordinate = new KMLCoordinate();
							
							// TODO: Switch to use integers instead of double
							/*String longitude = coordinateArray.substring(0,commaIndex);
							String latitude = coordinateArray.substring(commaIndex+1);
							
							int indexOfPeriod = longitude.indexOf('.');
							longitude = longitude.substring(0,indexOfPeriod+6);
							longitude = longitude.replace(".", "");
							
							indexOfPeriod = latitude.indexOf('.');
							latitude = latitude.substring(0,indexOfPeriod+6);
							latitude = latitude.replace(".", "");
							
							int longitudeInteger = Integer.parseInt(longitude);
							int latitudeInteger = Integer.parseInt(latitude);*/
							
							coordinate.name = placemark.name;
							//Log.i("trail", placemark.name);
							coordinate.longitude = Double.parseDouble(coordinateArray.substring(0,commaIndex));
							//Log.i("latitude", coordinateArray.substring(commaIndex+1));
							coordinate.latitude = Double.parseDouble(coordinateArray.substring(commaIndex+1));
							
							
						/*StringTokenizer st = new StringTokenizer(coordinates, " ");
						while (st.hasMoreTokens()) {	
							StringTokenizer st2 = new StringTokenizer(st.nextToken(), ",");
							coordinate = new KMLCoordinate();
							coordinate.longitude = Double.valueOf(st2.nextToken());
							coordinate.latitude = Double.valueOf(st2.nextToken());
							coordinate.name = placemark.name;*/
							
							
							// EXPERIMENTAL. Go through the array and see if there is a line string that is within a certain distance of it  (3 seconds)
							if (lineString.coordinates.size() == 0) {
								Location coordinateLocation = new Location("");
								coordinateLocation.setLatitude(coordinate.latitude);
								coordinateLocation.setLongitude(coordinate.longitude);
			                    
			                    for (int i=0; i < placemark.lineStrings.size(); i++) {
			                        KMLLineString currentLineString = placemark.lineStrings.get(i);
			                        if (currentLineString.coordinates.size() == 0) {
			                        	continue;
			                        }
			                        KMLCoordinate lastCoordinate = currentLineString.coordinates.get(currentLineString.coordinates.size()-1);
			                        //KMLCoordinate firstCoordinate = currentLineString.coordinates.get(0);
			                        Location lastCoordinateLocation = new Location("");
			                        lastCoordinateLocation.setLatitude(lastCoordinate.latitude);
			                        lastCoordinateLocation.setLongitude(lastCoordinate.longitude);
			                        //Location firstCoordinateLocation = new Location("");
			                        //firstCoordinateLocation.setLatitude(firstCoordinate.latitude);
			                        //firstCoordinateLocation.setLongitude(firstCoordinate.longitude);
			                        float lastCoordinateDistance = coordinateLocation.distanceTo(lastCoordinateLocation);
			                        //float firstCoordinateDistance = coordinateLocation.distanceTo(firstCoordinateLocation);
			                        
			                        if (lastCoordinateDistance <= 2) {
			                            connectToEndOfString = true;
			                            lineString = placemark.lineStrings.get(i);
			                        } 
			                    }
							}
							
							if (placemark.isPoint) {
								placemark.longitude = coordinate.longitude;
								placemark.latitude = coordinate.latitude;
							}
							else if (connectToBeginningOfString) {
								lineString.coordinates.add(0, coordinate);
								beginningOfStringCounter++;
							} else {
								lineString.coordinates.add(coordinate);
							}
						}
						
						if (!connectToBeginningOfString && !connectToEndOfString) {
							placemark.lineStrings.add(lineString);
						}
						
						// advance to next token until we get through all coordinates 4
						token = jp.nextToken();
					}
				
				// This means we are at the type element
				} else if ("t".equals(fieldname)) {
					token = jp.nextToken();
					placemark.isPoint = jp.getText().equals("0");
					placemark.isPath = jp.getText().equals("1");
				}
				
				token = jp.nextToken();
			}
			
			jp.close();
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int numberOfLineStrings = 0;
		for (int i=0; i<kmlDocument.placemarks.size();i++) {
			numberOfLineStrings += kmlDocument.placemarks.get(i).lineStrings.size();
		}
		//Log.i("number of line strings", String.valueOf(numberOfLineStrings));
		
		return kmlDocument;
	}
	
	public void CheckExistingLineIndexMethod() {
		/*int pos = 0;
		int end = 0;
		String coordinateArray = null;
		while ((end = coordinates.indexOf(' ', pos)) >= 0) {
			coordinateArray = coordinates.substring(pos,end);
			pos = end + 1;*/
		
		/*int pos2 = 0;
		int end2 = 0;
        String coords = coordinateArray;
		end2 = coords.indexOf(',', pos2);
        String longitude = coords.substring(pos2,end2);
        pos2 = end2 + 1;
        end2 = coords.indexOf(',', pos2);
        String latitude = coords.substring(pos2,end2);
        
        coordinate = new KMLCoordinate();
		coordinate.longitude = Double.valueOf(longitude);
		coordinate.latitude = Double.valueOf(latitude);*/
		
		/*if (firstCoordinateDistance <=2) {
    	connectToBeginningOfString = true;
    	lineString = placemark.lineStrings.get(i);
    }
    else 
    */
		
		/*String longitude = coordinateArray.substring(0,commaIndex);
		String latitude = coordinateArray.substring(commaIndex+1);
		
		int indexOfPeriod = longitude.indexOf('.');
		longitude = longitude.substring(0,indexOfPeriod+6);
		longitude = longitude.replace(".", "");
		
		indexOfPeriod = latitude.indexOf('.');
		latitude = latitude.substring(0,indexOfPeriod+6);
		latitude = latitude.replace(".", "");
		
		int longitudeInteger = Integer.parseInt(longitude);
		int latitudeInteger = Integer.parseInt(latitude);*/
	}
}
