package Weather;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WundergroundWeatherHandler extends DefaultHandler {

	private WeatherSet weatherSet;
	private StringBuilder currentValue;
	private boolean in_display_location = false;
	
	public WeatherSet getWeatherSet() {
		return weatherSet;
	}
	
	// ===========================================================
	// Methods
	// ===========================================================
	@Override
	public void startDocument() throws SAXException {
		weatherSet = new WeatherSet();
	}

	@Override
	public void endDocument() throws SAXException {
		// Nothing
	}

	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		currentValue = new StringBuilder();
		
		if (localName.equals("display_location")) {
			in_display_location = true;
		} 
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		if (localName.equals("display_location")) {
			in_display_location = false;
		} else if (in_display_location && localName.equals("full")) {
			weatherSet.cityName = currentValue.toString();
		} else if (localName.equals("weather")) {
			weatherSet.condition = currentValue.toString();
		} else if (localName.equals("temp_f")) {
			weatherSet.temperature = currentValue.toString();
		} else if (localName.equals("relative_humidity")) {
			weatherSet.humidity = currentValue.toString();
		} else if (localName.equals("wind_dir")) {
			weatherSet.windCondition = currentValue.toString();
		} else if (localName.equals("wind_mph")) {
			weatherSet.windCondition = weatherSet.windCondition + " at " + currentValue.toString() + " mph";
		} 
	}

	@Override
	public void characters(char ch[], int start, int length) {
		String s = new String(ch).substring(start, start+length);
		currentValue.append(s);
	}
}
