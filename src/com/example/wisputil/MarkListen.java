package com.example.wisputil;

import java.io.ObjectInputStream;
import java.util.HashMap;

import android.location.Location;
import android.media.MediaPlayer;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.example.wisp.Uploader;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.Marker;

public class MarkListen implements OnMarkerClickListener {
	HashMap<Location, String> revmap;
	AmazonS3Client s3Client;
	public MarkListen(HashMap<String, Location> map){
		for (int i=0; i<map.size(); i++){
			revmap.put(map.get(""+i), ""+i);
		}
		s3Client =   new AmazonS3Client( new BasicAWSCredentials( Uploader.id, Uploader.key ) );



		
	}
	@Override
	public boolean onMarkerClick(Marker marker) {
		MediaPlayer mp= new MediaPlayer();
		Location loc= new Location("hi");
		loc.setLatitude(marker.getPosition().latitude);
		loc.setLongitude(marker.getPosition().longitude);
		String x=revmap.get(loc);
		try {
			ObjectInputStream in=new ObjectInputStream(s3Client.getObject(new GetObjectRequest(Uploader.bucket, x)).getObjectContent());
			Storage stor=(Storage) in.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return true;
	}

}
