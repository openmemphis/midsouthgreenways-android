package org.midsouthgreenways.android;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.midsouthgreenways.android.android.map.MapViewerActivity;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;


import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import Weather.GoogleWeatherHandler;
import Weather.WeatherImages;
import Weather.WeatherSet;
import Weather.WundergroundWeatherHandler;

public class WeatherViewController {

//	private MapViewerActivity mainActivity;
//	private boolean isExecutingWeatherTask = false;
//	
//	public boolean getIsExecutingWeatherTask() { return isExecutingWeatherTask; }
//	public void setIsExecutingWeatherTask(boolean value) { isExecutingWeatherTask = value; }
//	
//	public WeatherViewController(MapViewerActivity activity) {
//		mainActivity = activity;
//	}
//	
//	public void getWeather(String postalCode) {
//		postalCode = "27603";
//		if (!isExecutingWeatherTask) {
//			GetWeatherTask weatherTask = new GetWeatherTask(this);
//			weatherTask.execute(postalCode);
//		}
//	}
//	
//	public void updateWeatherView(WeatherSet weatherSet) {
//		TextView weatherNotAvailable = (TextView)((RelativeLayout)mainActivity.findViewById(R.)).findViewById(R.id.weatherNotAvailable);
//		ImageView weatherImage = (ImageView)((RelativeLayout)mainActivity.findViewById(R.id.weatherView)).findViewById(R.id.conditionImage);
//		TextView humidityLabel = (TextView)((RelativeLayout)mainActivity.findViewById(R.id.weatherView)).findViewById(R.id.humidityLabel);
//		TextView windLabel = (TextView)((RelativeLayout)mainActivity.findViewById(R.id.weatherView)).findViewById(R.id.windConditionLabel);
//		
//		if (weatherSet != null) {
//			
//			// set visibility of elements
//			weatherNotAvailable.setVisibility(View.INVISIBLE);
//			weatherImage.setVisibility(View.VISIBLE);
//			humidityLabel.setVisibility(View.VISIBLE);
//			windLabel.setVisibility(View.VISIBLE);
//			
//			weatherImage.setImageResource(WeatherImages.getImageResource(weatherSet.condition));
//			TextView condition = (TextView)((RelativeLayout)mainActivity.findViewById(R.id.weatherView)).findViewById(R.id.condition);
//			condition.setText(weatherSet.condition);
//			TextView temperature = (TextView)((RelativeLayout)mainActivity.findViewById(R.id.weatherView)).findViewById(R.id.temperature);
//			temperature.setText(String.format("%s�F", weatherSet.temperature));
//			TextView cityName = (TextView)((RelativeLayout)mainActivity.findViewById(R.id.weatherView)).findViewById(R.id.cityName);
//			cityName.setText(weatherSet.cityName);
//			TextView humidity = (TextView)((RelativeLayout)mainActivity.findViewById(R.id.weatherView)).findViewById(R.id.humidity);
//			humidity.setText(weatherSet.humidity);
//			TextView wind = (TextView)((RelativeLayout)mainActivity.findViewById(R.id.weatherView)).findViewById(R.id.windCondition);
//			wind.setText(weatherSet.windCondition);
//			
//		} else {
//			// set visibility of elements
//			weatherNotAvailable.setVisibility(View.VISIBLE);
//			weatherImage.setVisibility(View.INVISIBLE);
//			humidityLabel.setVisibility(View.INVISIBLE);
//			windLabel.setVisibility(View.INVISIBLE);
//		}
//	}
}
