package com.example.wisp;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.Marker;

public class InfoWindow implements InfoWindowAdapter, OnInfoWindowClickListener{
	MapShow ac;
	MarkListen ma;
	public InfoWindow(MapShow a, MarkListen m){
		ac=a;
		ma=m;
		
	}
	@Override
	public View getInfoContents(Marker m) {
        View v = ac.getLayoutInflater().inflate(R.layout.windowlayout, null);
        TextView title = (TextView) v.findViewById(R.id.title);
        title.setTextSize(21);
        title.setText(m.getTitle());
		return v;
	}

	@Override
	public View getInfoWindow(Marker m) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onInfoWindowClick(Marker marker) {
		ma.onListen(marker);
		
	}

}
