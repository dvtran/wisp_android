package com.example.wisp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.wisputil.SerializableLatLng;
import com.example.wisputil.Stor;


public class Uploader extends AsyncTask<Stor, Void, Void>{
	public static final String key="K0vltjLmjxFD9HZ6Pk5NCMZLNf3PJitIShQAJG2i";
	public static final String id= "AKIAIOGZATNEIBT5HWFA";
	public static final String bucket= "wispdata";
	MainActivity main;
	public Uploader(MainActivity man){
		main=man;

	}
	//Gets the storage file, gets the manifest, checks the id of the next upload, puts the new file, updates manifest with new ID+location
	@Override
	protected Void doInBackground(Stor... params) {
		try {
			Stor sto=params[0];
			Log.d("2", "2");
			SerializableLatLng loc= sto.getLocation();
			Log.d("2", "2");
			AmazonS3Client s3Client =   new AmazonS3Client( new BasicAWSCredentials( id, key ) );
			Log.d("2", "2");
			new ManifestGen(main);
			ObjectInputStream in=new ObjectInputStream(s3Client.getObject(bucket, "count").getObjectContent());
			Log.d("2", "2");
			Integer i=(Integer)in.readObject();
			in.close();
			Log.d("2", "2");
			PutObjectRequest por= new PutObjectRequest(bucket, ""+i, new File(main.getCacheDir()+File.separator+"stored.wip"));
			Log.d("2", "2");
			s3Client.putObject(por);
			Log.d("2", "2");
			ObjectOutputStream out= new ObjectOutputStream(new FileOutputStream(main.getCacheDir()+File.separator+"manifest.man"));
			Log.d("2", "2");
			out.flush();
			Log.d("2", "2");
			out.writeObject(Integer.valueOf(i+1));
			Log.d("2", "2");
			out.flush();
			Log.d("2", "2");
			out.close();
			Log.d("2", "2");
			por= new PutObjectRequest(bucket, "count", main.getCacheDir()+File.separator+"manifest.man" );
			Log.d("2", "2");
			s3Client.putObject(por);
			Log.d("2", "2");
			main.done();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


}
