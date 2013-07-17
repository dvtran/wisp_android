package com.example.wisputil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import android.location.Location;
import android.media.MediaPlayer;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.example.wisp.MainActivity;
import com.example.wisp.Uploader;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MarkListen implements OnMarkerClickListener {
	HashMap<Location, String> revmap= new HashMap<Location, String>(50);
	AmazonS3Client s3Client;
	MainActivity main;
	public MarkListen(HashMap<String, Location> map, MainActivity man){
		for (int i=0; i<map.size(); i++){
			revmap.put(map.get(""+i), ""+i);
		}
		s3Client =   new AmazonS3Client( new BasicAWSCredentials( Uploader.id, Uploader.key ) );
		main=man;



		
	}
	public static LatLng toLatLng(Location loc){
		return new LatLng(loc.getLatitude(), loc.getLongitude());
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
			in.close();
			ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(main.getCacheDir().getAbsolutePath()+File.separator+"cachesound.3ogg"));
			out.flush();
			out.writeObject(stor.getSound());
			out.close();
			mp.setDataSource(main.getCacheDir().getAbsolutePath()+File.separator+"cachesound.3ogg");
			mp.start();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return true;
	}

}
