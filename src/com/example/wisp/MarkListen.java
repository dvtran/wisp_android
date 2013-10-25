package com.example.wisp;

import java.io.IOException;

import android.location.Location;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MarkListen implements OnMarkerClickListener, OnCompletionListener {
	LocationGetter locget;
	public MarkListen(LocationGetter locgetter){
		locget=locgetter;


		
	}
	public static LatLng toLatLng(Location loc){
		return new LatLng(loc.getLatitude(), loc.getLongitude());
	}
	@Override
	public boolean onMarkerClick(Marker marker) {
		marker.showInfoWindow();
		return true;
	}
	public void onListen(Marker m){
		SoundGetter get= new SoundGetter(this);
		get.execute(m);
	}
	public void listen(String f){
		MediaPlayer mp= new MediaPlayer();
		Log.d("Listen", f);
		mp.setOnCompletionListener(this);
		try {
			mp.setDataSource(f);
			mp.prepare();
			mp.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void onCompletion(MediaPlayer e) {
		e.stop();
		e.release();
		
	}

}
