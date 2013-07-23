package com.example.wispdisplay;

import java.util.HashMap;
import java.util.List;

import android.os.AsyncTask;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.wisp.Uploader;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationGetter extends AsyncTask<Void, MarkerOptions, Void> {
	MapFragment mMap;
	MapShow map;
	HashMap<LatLng, Integer> revloc= new HashMap<LatLng, Integer>();
	public LocationGetter(MapFragment mmap, MapShow mape){
		super();
		mMap=mmap;
		map=mape;
	}
	@Override
	protected Void doInBackground(Void... arg0) {
		try {
			
			AWSCredentials myCredentials = new BasicAWSCredentials(Uploader.id, Uploader.key); 
			AmazonS3Client s3Client = new AmazonS3Client(myCredentials);
			List<S3ObjectSummary> ss= s3Client.listObjects(Uploader.bucket).getObjectSummaries();
			for (int i=0;i<ss.size();i++){
				S3Object obj = s3Client.getObject(Uploader.bucket, ss.get(i).getKey());
				ObjectMetadata meta=obj.getObjectMetadata();
				String lat="";
				String longer="";
				String x=meta.getUserMetadata().get("loc");
				for (int n=0; n<x.length();n++){
					if (x.charAt(n)==';'){
						lat=x.substring(0, n);
						longer=x.substring(n+1, x.length());
					}
				}
				
            	MarkerOptions mark= new MarkerOptions();
            	LatLng latlng= new LatLng(Double.parseDouble(lat), Double.parseDouble(longer));
            	mark.position(latlng);
            	mark.draggable(false);
            	mark.visible(false);
            	map.addMark(mark);
            	revloc.put(latlng, Integer.valueOf(i));
			
			}
			

		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public Integer getSound(LatLng l){
		if (revloc.containsKey(l)){
			return revloc.get(l);
		}
		else{
			return null;
		}
	}


}