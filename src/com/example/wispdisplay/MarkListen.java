package com.example.wispdisplay;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.location.Location;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.example.wisp.Uploader;
import com.example.wisputil.Stor;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MarkListen implements OnMarkerClickListener {
	LocationGetter locget;
	public MarkListen(LocationGetter locgetter){
		locget=locgetter;


		
	}
	public static LatLng toLatLng(Location loc){
		return new LatLng(loc.getLatitude(), loc.getLongitude());
	}
	@Override
	public boolean onMarkerClick(Marker marker) {
		AmazonS3Client s3Client =   new AmazonS3Client( new BasicAWSCredentials( Uploader.id, Uploader.key ) );
		MediaPlayer mp= new MediaPlayer();
		LatLng latlng= marker.getPosition();
		if (locget.getSound(latlng)==null){
			Toast toast = Toast.makeText(locget.map.getApplicationContext(), "No Sound Found", Toast.LENGTH_SHORT);
			toast.setDuration(5);
			toast.show();
		}
		else{
			int x=locget.getSound(latlng);
		try {
			ObjectInputStream in=new ObjectInputStream(s3Client.getObject(new GetObjectRequest(Uploader.bucket, x+".ogg")).getObjectContent());
			Byte[] sound= (Byte[]) in.readObject();
			in.close();
			ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(locget.map.getCacheDir()+File.separator+"cachesound.3ogg"));
			out.flush();
			out.writeObject(sound);
			out.close();
			mp.setDataSource(locget.map.getCacheDir()+File.separator+"cachesound.3ogg");
			mp.start();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return true;
	}

}
