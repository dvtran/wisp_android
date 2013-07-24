package com.example.wisp;

import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapShow extends Activity implements ConnectionCallbacks, OnConnectionFailedListener {
	private GoogleMap map;
	private Location loc;
	private LocationClient locclient;
	MapFragment mMapFragment;
	LocationGetter locdate;
	ProgressDialog dialog;
	ProgressDialog pd;
	HashMap<Marker, Integer> revloc= new HashMap<Marker, Integer>();
	public MapShow(){

	}
	public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_map);
        pd = ProgressDialog.show(this, "", "Loading...",
                true);
        pd.setCancelable(false);
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        Log.d("Fuckthis", ""+(ConnectionResult.SUCCESS==resultCode));
        switch (resultCode) {
           case ConnectionResult.SUCCESS: // proceed
              break;
            case ConnectionResult.SERVICE_MISSING:
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
            case ConnectionResult.SERVICE_DISABLED:
            	  pd.cancel();
                  Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 1);
                  dialog.show();
         }
		setUpMap();
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		Log.d("Click", ""+(locdate==null));
    	locclient = new LocationClient(this, this, this);
        locclient.connect();
	}
	protected void setUpMap() {
	    // Do a null check to confirm that we have not already instantiated the map.
	    if (map == null) {
	        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
	        // Check if we were successful in obtaining the map.
	        if (map == null) {
	        	Log.d("Map", "MapNull");
	        }
	    }
	}
	public void addMark(MarkerOptions[] mark){
		Log.d("Mark", "MarkergonnaAdded");
		for (int i=0;i<mark.length;i++){
			revloc.put(map.addMarker(mark[i]), i);
		}
		Log.d("Mark", "MarkerAdded");
		Log.d("Click", "Revloc, "+revloc.keySet().size()+", "+revloc.keySet().toArray()[0].toString());
	}
	@Override
	public void onConnected(Bundle arg0) {
		Log.d("Loc", "here");
		if (locclient.getLastLocation()==null){
				Log.d("Loc", "here");
			 	final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				Log.d("Loc", "here");
				loc=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		else{
			Log.d("Loc", "here2");
			loc=locclient.getLastLocation();
		}
		Log.d("Loc", "here");
		map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(loc.getLatitude(), loc.getLongitude())));
		Log.d("Loc", "here");
		locdate= new LocationGetter(mMapFragment, this);
		Log.d("Loc", "here");
		locdate.execute((Void)null);
		map.setOnMarkerClickListener(new MarkListen(locdate));
		Log.d("Loc", "here");
        pd.cancel();
        locclient.disconnect();
		
	}
	@Override
	public void onDisconnected() {		
	}
	@Override
    protected Dialog onCreateDialog(int id) {
        return dialog;
	}
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Toast toast = Toast.makeText(getApplicationContext(), "Unable to Connect to Google Play Services", Toast.LENGTH_SHORT);
		toast.setDuration(5);
		toast.show();				
	}
	public int getSound(Marker mark){
		if (revloc.containsKey(mark)==false){
			return -1;
		}
		return revloc.get(mark).intValue();
	}
}
