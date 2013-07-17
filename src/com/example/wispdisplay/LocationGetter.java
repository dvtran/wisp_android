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
import com.example.wisputil.Storage;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationGetter extends AsyncTask<Location, MarkerOptions, Void> {
	MapFragment mMap;
	MapShow map;
	HashMap<Location, String> revmap= new HashMap<Location, String>(50);
	public LocationGetter(MapFragment mmap, MapShow mape){
		super();
		mMap=mmap;
		map=mape;
	}
	@Override
	protected Void doInBackground(Location... arg0) {
		GoogleMapOptions option= new GoogleMapOptions();
		option.camera(new CameraPosition(MarkListen.toLatLng(arg0[0]),(float)14.0,(float)0.0,(float)0.0));
		mMap=MapFragment.newInstance(option);
		FragmentTransaction fragmentTransaction = map.getFragmentManager().beginTransaction();
		fragmentTransaction.add(R.id.map, mMap);
		fragmentTransaction.commit();
		map.setUpMap();
		try {
			RestS3Service s3Service = new RestS3Service(new AWSCredentials( Uploader.id, Uploader.key ));
			S3Object[] objects = s3Service.listObjects(Uploader.bucket);
			for (int i=0;i<objects.length;i++){
				S3Object obj = s3Service.getObject(Uploader.bucket, objects[i].getKey());
            	ObjectInputStream in = new ObjectInputStream(obj.getDataInputStream());
            	Storage stor= (Storage)in.readObject();
            	MarkerOptions mark= new MarkerOptions();
            	mark.position(MarkListen.toLatLng(stor.getLocation()));
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