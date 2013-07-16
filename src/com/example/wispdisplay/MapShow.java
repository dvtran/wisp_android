package com.example.wispdisplay;

import com.example.wisp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class MapShow {
	private GoogleMap map;
	public MapShow(){
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

	}
}
