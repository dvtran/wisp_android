package com.example.wisp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;

public class GPSGrabber {
	protected LocationManager locationManager;
	private final Context context;
	private boolean gpsOn=false;
	private Location location;
	public GPSGrabber(Context con){
		context=con;
	}
	public Location getLocation(){
		return location;
	}
	protected void getGPS(){
		try{
			//creates location manager, checks if GPS is on
			locationManager= (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
			gpsOn=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			if (gpsOn){
				//if GPS is on, gets the location
				location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			}
			else{
				//otherwise, gets them to change their settings and tries GPS again.
				changeOptions();
				getGPS();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	//Builds a toast that has an option to go to the settings for location and one to cancel
	private void changeOptions(){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

	    alertDialog.setTitle("GPS is off");

	    alertDialog.setMessage("Please enable GPS by going to the settings Menu");

	    alertDialog.setPositiveButton("Settings",
	            new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                    Intent intent = new Intent(
	                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	                    context.startActivity(intent);
	                }
	            });

	    alertDialog.setNegativeButton("Cancel",
	            new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                    dialog.cancel();
	                }
	            });
	    alertDialog.show();
	}

}
