package Weather;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GoogleWeatherHandler extends DefaultHandler {

	private WeatherSet weatherSet;
	private boolean in_forecast_information = false;
	private boolean in_current_conditions = false;
	
	// public methods
	
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
		String dataAttribute = atts.getValue("data");
		
		if (localName.equals("forecast_information")) {
			in_forecast_information = true;
		} else if (localName.equals("current_conditions")) {
			in_current_conditions = true;
		} else if (in_forecast_information && localName.equals("city")) {
			weatherSet.cityName = dataAttribute;
		} else if (in_current_conditions && localName.equals("condition")) {
			weatherSet.condition = dataAttribute;
		} else if (in_current_conditions && localName.equals("temp_f")) {
			weatherSet.temperature = dataAttribute;
		} else if (in_current_conditions && localName.equals("humidity")) {
			weatherSet.humidity = dataAttribute.replace("Humidity: ", "");
		} else if (in_current_conditions && localName.equals("wind_condition")) {
			weatherSet.windCondition = dataAttribute.replace("Wind: ", "");
		}
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		if (localName.equals("forecast_information")) {
			in_forecast_information = false;
		} else if (localName.equals("current_conditions")) {
			in_current_conditions = false;
		}
	}

	@Override
	public void characters(char ch[], int start, int length) {
		/*
		 * Would be called on the following structure: <element>characters</element>
		 */
	}
	
}
