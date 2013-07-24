package com.example.wispdisplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

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

public class LocationGetter extends AsyncTask<Void, Void, Void> {
	MapFragment mMap;
	MapShow map;
	HashMap<LatLng, Integer> revloc= new HashMap<LatLng, Integer>();
	ArrayList<MarkerOptions> marks= new ArrayList<MarkerOptions>();
	AmazonS3Client s3Client;
	public LocationGetter(MapFragment mmap, MapShow mape){
		super();
		mMap=mmap;
		map=mape;
	}
	@Override
	protected Void doInBackground(Void... arg0) {
		try {
			Log.d("Mark", "FindingMarks");
			AWSCredentials myCredentials = new BasicAWSCredentials(Uploader.id, Uploader.key); 
			s3Client = new AmazonS3Client(myCredentials);
			List<S3ObjectSummary> ss= s3Client.listObjects(Uploader.bucket).getObjectSummaries();
			for (int i=0;i<ss.size();i++){
				Log.d("Mark", "FindingMarks");
				S3Object obj = s3Client.getObject(Uploader.bucket, ss.get(i).getKey());
				ObjectMetadata meta=obj.getObjectMetadata();
				String lat="";
				String longer="";
				String x=meta.getUserMetadata().get("loc");
				for (int n=0; n<x.length();n++){
					if (x.charAt(n)==';'){
						lat=x.substring(0, n);
						longer=x.substring(n+1, x.length());
						break;
					}
				}
				Log.d("Mark", "FindingMarks");
            	MarkerOptions mark= new MarkerOptions();
            	LatLng latlng= new LatLng(Double.parseDouble(lat), Double.parseDouble(longer));
            	mark=mark.position(latlng);
            	mark=mark.draggable(false);
            	mark=mark.visible(true);
    			Log.d("Mark", "Gonnaputmark");
            	marks.add(mark);
    			Log.d("Mark", "Gonnaputmark");
            	revloc.put(latlng, Integer.valueOf(i));
    			Log.d("Mark", "Gonnaputmark");
    			Log.d("Click", latlng.latitude+", "+latlng.longitude);
    			Log.d("Click", ""+i);

			
			}
			Log.d("Mark", "Gonnaputmark");
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	@Override
	protected void onPostExecute(Void a){
		Log.d("Mark", "Gonnaputmarkpost");
		map.addMark(marks.toArray(new MarkerOptions[marks.size()]));
		return;
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