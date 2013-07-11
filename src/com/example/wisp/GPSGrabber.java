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
			locationManager= (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
			gpsOn=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			if (gpsOn){
				location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			}
			else{
				changeOptions();
				getGPS();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
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
