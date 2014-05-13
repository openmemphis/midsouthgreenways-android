package com.speakingcode.android.location;

import java.text.DecimalFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationMapManager
{
    private Activity activity;
    private GoogleMap map;
    public Marker marker;
    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;
    
    public LocationMapManager(Activity activity, GoogleMap map)
    {
        this.activity = activity;
        this.map = map;        
    }
    
    public void geolocate() {
        final LocationManager locationManager =
                (LocationManager) this.activity.getSystemService(Context.LOCATION_SERVICE);
        
        boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!gpsEnabled) {                     
            AlertDialog.Builder ab = new AlertDialog.Builder(this.activity);
            AlertDialog ad = ab.create();  
            ad.setCanceledOnTouchOutside(false);
            ad.setTitle("Location Services Not Enabled");
            ad.setMessage("In order to locate your device, you must enable location services.");
            
            ad.setButton(DialogInterface.BUTTON_POSITIVE, "Go to location services", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    activity.startActivity(settingsIntent);
                    findDeviceLocation();
                }
            });
            
            ad.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {                          
                }
            });      
            
            ad.show();
        } else {
           findDeviceLocation();
        }
    }
    
    private void findDeviceLocation() {
        final LocationManager locationManager =
                (LocationManager) this.activity.getSystemService(Context.LOCATION_SERVICE);
        
        if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER)) {    
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    10000,          // 10-second interval.
                    10,             // 10 meters.
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {   
                            Log.d("LOCATION LOCATION","LOCATION CHANGED!!!!!!!!!");
                        	try
                            {
                                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                displayMarker(latLng, "Current Location");
                                moveMapCameraToLocation(latLng, 10);                        
                                locationManager.removeUpdates(this);
                            }
                            catch (Exception e)
                            {                            
                            }
                        }
    
                        @Override
                        public void onProviderDisabled(String arg0) {}
    
                        @Override
                        public void onProviderEnabled(String arg0) {}
    
                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {}
                    }
             );
        } else {
            //maybe we should show an error message ?
        }
    }
    
    public void displayMarker(LatLng latLng, String description) {        
        if (this.marker != null) {
            this.marker.setPosition(latLng);  
            this.marker.setSnippet(description);
        } else {
            this.marker = this.map.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Current Location")
//                .snippet(description)
                .draggable(true)
            );
        }
        this.marker.showInfoWindow();
    }
    public void displayMarker(LatLng latLng) {
        displayMarker(latLng, this.getFormattedLatLng(latLng));
    }
    
    public String getFormattedLatLng(LatLng latLng) {
        DecimalFormat df = new DecimalFormat("#.######");
        String description = "" + df.format(latLng.latitude) + ", " + df.format(latLng.longitude);
        return description;
    }
    
    private void moveMapCameraToLocation(LatLng latLng, int zoomLevel) {                  
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel);
        map.animateCamera(cameraUpdate, new CancelableCallback() {
        	@Override
        	public void onFinish(){
                CameraUpdate cu = CameraUpdateFactory.scrollBy(0, -110);
                map.animateCamera(cu);
        	}
        	
        	@Override
        	public void onCancel(){
        		// Do nothing
        	}
        });
        
        
     }
}
