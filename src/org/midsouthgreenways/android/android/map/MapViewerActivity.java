package org.midsouthgreenways.android.android.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.midsouthgreenways.android.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.widget.SearchView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.speakingcode.geojson.Feature;
import com.speakingcode.geojson.GeoJSON;
import com.speakingcode.geojson.GeoJSONParserManager;
import com.speakingcode.geojson.IGeoJSONParserClient;
import com.speakingcode.geojson.IGeometry;
import com.speakingcode.geojson.LineString;
import com.speakingcode.geojson.MultiLineString;

public class MapViewerActivity
	extends SherlockFragmentActivity
	implements IGeoJSONParserClient
{
	private GoogleMap map;
	private SupportMapFragment mapFragment;
	Gson gson = new Gson();
	
	private boolean processedMap = false;
	
	private HashMap<String, List<PolylineOptions>> currentMapLines = new HashMap<String, List<PolylineOptions>>();
	private HashMap<String, List<PolylineOptions>> allMapLines = new HashMap<String, List<PolylineOptions>>();

	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_viewer);

		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
	    
	    setTitle(R.string.app_name);
	    	
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.map_viewer_activity, menu);
        
        final SearchView searchView = new SearchView(getSupportActionBar().getThemedContext());
        searchView.setQueryHint("Enter Address");
        
        // Must have a "final" version of the menu to be able to use in the anonymous classes.
        final Menu finalMenu = menu;

        return super.onPrepareOptionsMenu(menu);
    }
	
	@Override
    public void onResume()
    {
    	super.onResume();
    	
    	setupMap(); 
    	getMapData();
    	setupButtons();
    }
	
	private void setupMap()
	{
		if(processedMap)
			return;
		
		processedMap = true;
		
        GoogleMapOptions options = new GoogleMapOptions()
            .mapType(GoogleMap.MAP_TYPE_NORMAL)
            .camera(new CameraPosition(new LatLng(35.1174, -89.9711), 8, 0, 0));

        if (mapFragment == null)
        {
            mapFragment =  SupportMapFragment.newInstance(options);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.maplayout, mapFragment, "mapFragment");
            fragmentTransaction.commit();
            fragmentManager.executePendingTransactions();
        }

        if (map == null) {
            map = mapFragment.getMap();      
            //setupMapListeners();
        }     
    }
	
	public void getMapData()
	{
		Log.d("MapViewActivity", "getData() called!!");
		GeoJSONParserManager gj = new GeoJSONParserManager();
		gj.setGeoJSONParserClient(this);
		gj.parseGeoJSON
		(
			readAssetTextFile("trails_greenways_wgs84_existing.json")
		);
	}
	
	public void setupButtons()
	{
		CheckBox bikeLaneSelectButton = (CheckBox) findViewById(R.id.bike_lanes_checkbox);
		bikeLaneSelectButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked)
				{
					refreshMap();
//					for (PolylineOptions p : allMapLines.values())
//					{
//						cu.addPolyline(p);
//					}
				}
				else
				{
					map.clear();
				}
				
			}
		});
	}
	
	public String readAssetTextFile(String filename)
	{
		String textFile = "";
		InputStream is;

		try
		{
			is = this.getResources()
					 .getAssets()
					 .open(filename);
			
			textFile = convertStreamToString(is);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return textFile;
	}
	
	public String convertStreamToString(InputStream is)
			throws IOException
	{
		Writer writer = new StringWriter();

		char[] buffer = new char[2048];
		try
		{
			Reader reader = new BufferedReader
			(
				new InputStreamReader(is, "UTF-8")
			);
			int n;
			while ((n = reader.read(buffer)) != -1)
			{
				writer.write(buffer, 0, n);
			}
		}
		finally
		{
			is.close();
		}
		return writer.toString();
	}

	@Override
	public void onGeoJSONParseDone(final GeoJSON geoJSON)
	{
//		runOnUiThread
//		(
//			new Runnable() {public void run(){
//				for (Feature f : geoJSON.getFeatures())
//				{
//					PolylineOptions p = new PolylineOptions();
//					p.color(0xffffffff);
//					p.visible(true);
//					p.width(10);
//					
//					IGeometry geom = f.getGeometry();
//					if (geom.getType().equalsIgnoreCase("LineString"))
//					{
//						for (double[] coord : ((LineString) geom).getLine())
//						{
//							p.add(new LatLng(coord[1], coord[0]));
//							
//						}
//					}
//					
//					map.addPolyline(p);
//				}
//			} }
//		);
//	return;
		HashMap<String, List<Feature>> featureGroups = new HashMap<String, List<Feature>>();
		featureGroups.put("Bike Routes", new ArrayList<Feature>());
		featureGroups.put("Bike Lanes", new ArrayList<Feature>());
		featureGroups.put("Shared Lanes", new ArrayList<Feature>());
		featureGroups.put("Trails", new ArrayList<Feature>());
		
		for (Feature feature : geoJSON.getFeatures())
		{
			String featureType = feature.getProperties().getTYPE();
			
			if
			(
				featureType.equalsIgnoreCase("Bike Route")
				|| featureType.equalsIgnoreCase("Bike Facility")
			)
			{
				featureGroups.get("Bike Routes").add(feature);
			}
			else if
			(
				featureType.equalsIgnoreCase("Shared Lane")
				|| featureType.equalsIgnoreCase("Shared Bike Lane")
				|| featureType.equalsIgnoreCase("Signed Shared Roadway")
				|| featureType.equalsIgnoreCase("Signed Shared Road")
			)
			{
				featureGroups.get("Shared Lanes").add(feature);
			}
			else if
			(
				featureType.equalsIgnoreCase("Bike Lane")
				|| featureType.equalsIgnoreCase("Dedicated Bike Lane")
			)
			{
				featureGroups.get("Bike Lanes").add(feature);
				//bike lanes
			}
			else if
			(
				featureType.equalsIgnoreCase("Greenway/Trail")
				|| featureType.equalsIgnoreCase("Trail")
			)
			{
				featureGroups.get("Trails").add(feature);
			}
		}
		
		for (String key : featureGroups.keySet())
		{
			List<Feature> features = featureGroups.get(key);
			allMapLines.put(key, new ArrayList<PolylineOptions>());

			int color;
			if (key.equalsIgnoreCase("Bike Lanes"))
			{
				color = 0xffcccc00;
			}
			else if (key.equalsIgnoreCase("Bike Routes"))
			{
				color = Color.BLUE;
			}
			else if (key.equalsIgnoreCase("Shared Lanes"))
			{
				color = Color.RED;
			}
			else
			{
				color = Color.GREEN;
			}
			
			for (Feature feature : features)
			{
				final PolylineOptions p = new PolylineOptions();
				p.color(color);
				p.visible(true);
				p.width(4);
				IGeometry geom = feature.getGeometry();
				if (geom.getType().equalsIgnoreCase("LineString"))
				{
					LineString ls = ((LineString) geom); 
					for (double[] coord : ls.getLine())
					{
						//Log.d("map", "Adding coord to polyline  + " + coord[1] + "," + coord[0]);
						p.add(new LatLng(coord[1],coord[0]));
					}
				}
				else if(geom.getType().equalsIgnoreCase("MultiLineString"))
				{
					MultiLineString mls = (MultiLineString)geom;
					for (LineString ls : mls.getLineStrings())
					{
						for (double[] coord : ls.getLine())
						{
							//Log.d("map", "Adding coord to polyline  + " + coord[1] + "," + coord[0]);
							p.add(new LatLng(coord[1],coord[0]));
						}
					}
				}
				allMapLines.get(key).add(p);
//				runOnUiThread(new Runnable()
//				{		
//					@Override
//					public void run()
//					{
//						Log.d("map", "Adding polyline to map.. " + p.toString());
//						map.addPolyline(p);
//						
//					}
//				});
			}
		}
	}
	
	public void refreshMap()
	{
		runOnUiThread(new Runnable()
		{		
			@Override
			public void run()
			{
				for (List<PolylineOptions> featuregroup : allMapLines.values())
				{
					for (PolylineOptions p : featuregroup)
					{
						map.addPolyline(p);
					}
				}
			}
		});
	}

}
