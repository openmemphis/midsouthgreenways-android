package org.midsouthgreenways.android.android.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.midsouthgreenways.android.R;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.widget.SearchView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.speakingcode.geojson.Feature;
import com.speakingcode.geojson.GeoJSON;
import com.speakingcode.geojson.GeoJSONParserManager;
import com.speakingcode.geojson.Geometry;
import com.speakingcode.geojson.IGeoJSONParserClient;

public class MapViewerActivity
	extends SherlockFragmentActivity
	implements IGeoJSONParserClient
{
	private GoogleMap map;
	private SupportMapFragment mapFragment;
	Gson gson = new Gson();
	
	
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
    }
	
	private void setupMap()
	{      
        GoogleMapOptions options = new GoogleMapOptions()
            .mapType(GoogleMap.MAP_TYPE_NORMAL)
            .camera(new CameraPosition(new LatLng(42.495694,-96.404795), 1, 0, 0));

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
		GeoJSONParserManager gj = new GeoJSONParserManager();
		gj.parseGeoJSON
		(
			readAssetTextFile("trails_greenways_wgs84_existing.json")
		);
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
	public void onGeoJSONParseDone(GeoJSON geoJSON)
	{
		for (Feature feature : geoJSON.getFeatures())
		{
			Geometry geom = feature.getGeometry();
			if(geom.getType().equalsIgnoreCase("LineString"))
			{
				//coordinates will be array of 2-dim, 3 element 'coords' arrays
				
			}
			else if(geom.getType().equalsIgnoreCase("MultiLineString"))
			{
				//coordinates will be array of arrays of 2-dim,  3-element 'coords' arrays
				
			}
		}
	}

}
