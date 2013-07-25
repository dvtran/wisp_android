package com.example.wisp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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


public class Uploader extends AsyncTask<Stor, Void, Void> implements ProgressListener{
	public static final String key="K0vltjLmjxFD9HZ6Pk5NCMZLNf3PJitIShQAJG2i";
	public static final String id= "AKIAIOGZATNEIBT5HWFA";
	public static final String bucket= "wispdata";
	public String name;
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
			ByteArrayOutputStream out= new ByteArrayOutputStream();
			out.write(params[0].sound);
			out.writeTo(new FileOutputStream(main.getCacheDir()+File.separator+"stored.ogg"));
			TransferManager tx = new TransferManager(myCredentials);
	        ObjectMetadata newObjectMetadata = new ObjectMetadata();
	        newObjectMetadata.addUserMetadata("loc", params[0].location.toString());
	        newObjectMetadata.addUserMetadata("Name", name);
	        newObjectMetadata.setContentLength(new File(main.getCacheDir()+File.separator+"stored.ogg").length());
	        Upload u= tx.upload(bucket,(i+1)+".3gpp",new FileInputStream(new File(main.getCacheDir()+File.separator+"stored.ogg")), newObjectMetadata);
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
	public void addName(String x){
		name=x;
	}
	@Override
	public void progressChanged(ProgressEvent e) {
		if (e.getEventCode()==ProgressEvent.FAILED_EVENT_CODE){
			Log.d("UPLOAD", "UPLOAD FAILED");
			main.done();

		}
		else if (e.getEventCode()==ProgressEvent.COMPLETED_EVENT_CODE){
			
			main.done();
		}
		else{
			Log.d("Upload", "Canceled "+(e.getEventCode()==ProgressEvent.CANCELED_EVENT_CODE));
			Log.d("Upload", "PartDone "+(e.getEventCode()==ProgressEvent.PART_COMPLETED_EVENT_CODE));
			Log.d("Upload", "PartFailed "+(e.getEventCode()==ProgressEvent.PART_FAILED_EVENT_CODE));
			Log.d("Upload", "StartedEvent "+(e.getEventCode()==ProgressEvent.STARTED_EVENT_CODE));


		}
		
	}


}
