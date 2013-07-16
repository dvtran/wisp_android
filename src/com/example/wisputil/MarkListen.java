package com.example.wisputil;

import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.Marker;

public class MarkListen implements OnMarkerClickListener {

	public MarkListen(HashMap<String, Location>){
		
	}
	@Override
	public boolean onMarkerClick(Marker marker) {
		
		return true;
	}

}
