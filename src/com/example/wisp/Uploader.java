package com.example.wisp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.HashMap;

import android.location.Location;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.wisputil.Storage;


public class Uploader{
	public static final String key="blank";
	public static final String id= "id";
	public static final String bucket= "bucket";
	MainActivity main;
	public Uploader(MainActivity man){
		main=man;

	}
	//Gets the storage file, gets the manifest, checks the id of the next upload, puts the new file, updates manifest with new ID+location
	public void upload(){
		try {
			ObjectInputStream in= new ObjectInputStream(new FileInputStream(main.getCacheDir().getAbsolutePath()+File.separator+"stored.wip"));
			Storage sto= (Storage)in.readObject();
			Location loc= sto.getLocation();
			AmazonS3Client s3Client =   new AmazonS3Client( new BasicAWSCredentials( id, key ) );
			in=new ObjectInputStream(s3Client.getObject(new GetObjectRequest(bucket, "manifest")).getObjectContent());
			HashMap<String, Location> map=(HashMap<String, Location>)in.readObject();
			in.close();
			int i=map.size();
			map.put(""+i, loc);
			PutObjectRequest por= new PutObjectRequest(bucket, ""+i, new File(main.getCacheDir().getAbsolutePath()+File.separator+"stored.wip"));
			s3Client.putObject(por);
			ObjectOutputStream out= new ObjectOutputStream(new FileOutputStream(main.getCacheDir().getAbsolutePath()+File.separator+"manifest.man"));
			out.flush();
			out.writeObject(map);
			out.flush();
			out.close();
			por= new PutObjectRequest(bucket, "manifest", main.getCacheDir().getAbsolutePath()+File.separator+"manifest.man" );
			s3Client.putObject(por);

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
		}
	}


}
