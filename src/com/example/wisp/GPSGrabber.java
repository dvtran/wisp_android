package com.example.wisp;

import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

public class GPSGrabber
	implements
	GooglePlayServicesClient.ConnectionCallbacks,
	GooglePlayServicesClient.OnConnectionFailedListener {
		LocationClient mLocationClient;
		Location mCurrentLocation;
		MainActivity main;

	    public GPSGrabber(MainActivity man){
			main=man;

	    
	    }
	    public Location getLocation(){
	        mLocationClient.connect();
	    	mLocationClient = new LocationClient(main, this, this);
	        mCurrentLocation = mLocationClient.getLastLocation();
	        mLocationClient.disconnect();
	        return mCurrentLocation;
	    }
		@Override
		public void onConnectionFailed(ConnectionResult result) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onConnected(Bundle connectionHint) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDisconnected() {
			// TODO Auto-generated method stub
			
		}

}
