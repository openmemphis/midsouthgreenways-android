package org.midsouthgreenways.android;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import Weather.WeatherImages;
import Weather.WeatherSet;
import Weather.WundergroundWeatherHandler;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GetWeatherTask extends AsyncTask<String, Void, WeatherSet> {

	//private final String WUNDERGROUND_QUERY_STRING = "http://api.wunderground.com/api/5e4eed8d2aa85da1/conditions/q/%s.xml";
	private final String WUNDERGROUND_API_KEY = "5e4eed8d2aa85da1";
	
	private WeatherViewController weatherViewController;
	
	public GetWeatherTask(WeatherViewController viewController) {
		weatherViewController = viewController;
	}
	
	private final String WUNDERGROUND_QUERY_STRING = "http://api.wunderground.com/api/5e4eed8d2aa85da1/conditions/q/%s.xml";
	
	@Override
	protected WeatherSet doInBackground(String... postalCode) {
//		weatherViewController.setIsExecutingWeatherTask(true);
		try {
			
			if (postalCode == null) {
				return null;
			}
			
			String weatherString = String.format(WUNDERGROUND_QUERY_STRING, postalCode);
			URL url = new URL(weatherString);
			
			/* Get a SAXParser from the SAXPArserFactory. */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
	
			/* Get the XMLReader of the SAXParser we created. */
			XMLReader xr = sp.getXMLReader();
	
			WundergroundWeatherHandler wh = new WundergroundWeatherHandler();
			xr.setContentHandler(wh);
	
			/* Parse the xml-data our URL-call returned. */
			xr.parse(new InputSource(url.openStream()));
	
			/* Our Handler now provides the parsed weather-data to us. */
			WeatherSet ws = wh.getWeatherSet();
			return ws;
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected void onPostExecute(WeatherSet weatherSet) {
        if (weatherSet != null) {
//        	weatherViewController.updateWeatherView(weatherSet);
        }
//        weatherViewController.setIsExecutingWeatherTask(false);
    }
}
