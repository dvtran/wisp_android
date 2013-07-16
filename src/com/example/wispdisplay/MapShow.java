package com.example.wispdisplay;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;

import com.example.wisp.GPSGrabber;
import com.example.wisp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapShow extends Activity {
	private GoogleMap map;
	public MapShow(){

	}
	public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_map);
		MapFragment mMapFragment = MapFragment.newInstance();
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.add(R.id.map, mMapFragment);
		fragmentTransaction.commit();
		setUpMap();
		map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		LocationGetter locdate= new LocationGetter();
		locdate.execute(new GPSGrabber(this).getLocation());
	}
	private void setUpMap() {
	    // Do a null check to confirm that we have not already instantiated the map.
	    if (map == null) {
	        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
	                            .getMap();
	        // Check if we were successful in obtaining the map.
	        if (map != null) {
	            // The Map is verified. It is now safe to manipulate the map.

	        }
	    }
	}
	public void addMark(MarkerOptions mark){
		map.addMarker(mark);
	}
}
