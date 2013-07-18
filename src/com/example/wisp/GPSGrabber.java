package com.example.wisp;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

public class GPSGrabber
	implements
	GooglePlayServicesClient.ConnectionCallbacks,
	GooglePlayServicesClient.OnConnectionFailedListener {
		LocationClient mLocationClient;
		Location mCurrentLocation;
		Activity main;

	    public GPSGrabber(Activity man){
			main=man;

	    
	    }
	    public Location getLocation(){
	    	mCurrentLocation=null;
	    	mLocationClient = new LocationClient(main, this, this);
	        mLocationClient.connect();
	        while (mCurrentLocation==null){
	        	Log.d("RunningLocation", "FML");
	            Handler handler = new Handler(); 
	            handler.postDelayed(new Runnable() { 
	                 public void run() { 
	                 } 
	            }, 50); 
	        }
	        return mCurrentLocation;
	    }
		@Override
		public void onConnectionFailed(ConnectionResult result) {
			Toast toast = Toast.makeText(main.getApplicationContext(), "Unable to Connect to Google Play Services", Toast.LENGTH_SHORT);
			toast.setDuration(5);
			toast.show();
		}

		@Override
		public void onConnected(Bundle connectionHint) {
	        mCurrentLocation = mLocationClient.getLastLocation();
	        mLocationClient.disconnect();			
		}

		@Override
		public void onDisconnected() {
			// TODO Auto-generated method stub
			
		}

}
