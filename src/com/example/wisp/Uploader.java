package com.example.wisp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ProgressEvent;
import com.amazonaws.services.s3.model.ProgressListener;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.example.wisputil.SerializableLatLng;
import com.example.wisputil.Stor;


public class Uploader extends AsyncTask<Stor, Void, Void> implements ProgressListener{
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
			AWSCredentials myCredentials = new BasicAWSCredentials(id, key); 
			AmazonS3Client s3Client = new AmazonS3Client(myCredentials);        
			int i=s3Client.listObjects(bucket).getObjectSummaries().size();
			ObjectOutputStream out= new ObjectOutputStream(new FileOutputStream(main.getCacheDir()+File.separator+"stored.ogg"));
			Stor sto=params[0];
			out.writeObject(sto.sound);
			out.flush();
			out.close();
			TransferManager tx = new TransferManager(myCredentials);
	        ObjectMetadata newObjectMetadata = new ObjectMetadata();
	        newObjectMetadata.addUserMetadata("loc", params[0].location.toString());
	        newObjectMetadata.setContentLength(new File(main.getCacheDir()+File.separator+"stored.ogg").length());
	        Upload u= tx.upload(bucket,i+".ogg",new FileInputStream(new File(main.getCacheDir()+File.separator+"stored.ogg")), newObjectMetadata);
	        u.addProgressListener(this);


		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public void progressChanged(ProgressEvent e) {
		if (e.equals(ProgressEvent.FAILED_EVENT_CODE)){
			Log.d("FAILURE", "UPLOAD FAILED");
		}
		else if (e.equals(ProgressEvent.COMPLETED_EVENT_CODE)){
			main.done();
		}
		
	}


}
