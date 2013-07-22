package com.example.wispdisplay;

import java.io.ObjectInputStream;
import java.util.HashMap;

import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;

import android.app.FragmentTransaction;
import android.location.Location;
import android.os.AsyncTask;

import com.example.wisp.R;
import com.example.wisp.Uploader;
import com.example.wisputil.MarkListen;
import com.example.wisputil.Stor;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationGetter extends AsyncTask<Void, MarkerOptions, Void> {
	MapFragment mMap;
	MapShow map;
	public LocationGetter(MapFragment mmap, MapShow mape){
		super();
		mMap=mmap;
		map=mape;
	}
	@Override
	protected Void doInBackground(Void... arg0) {
		try {
			RestS3Service s3Service = new RestS3Service(new AWSCredentials( Uploader.id, Uploader.key ));
			S3Object[] objects = s3Service.listObjects(Uploader.bucket);
			for (int i=0;i<objects.length;i++){
				S3Object obj = s3Service.getObject(Uploader.bucket, objects[i].getKey());
            	ObjectInputStream in = new ObjectInputStream(obj.getDataInputStream());
            	Stor stor= (Stor)in.readObject();
            	MarkerOptions mark= new MarkerOptions();
            	mark.position(stor.getLocation().readResolve());
            	mark.draggable(false);
            	mark.visible(false);
            	map.addMark(mark);
			}
		
			

		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}


}