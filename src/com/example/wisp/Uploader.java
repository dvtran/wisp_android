package com.example.wisp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
<<<<<<< HEAD
||||||| merged common ancestors
import java.io.StreamCorruptedException;
import java.util.HashMap;
=======
import java.io.StreamCorruptedException;
>>>>>>> 0e9156b02f97d5c2923a3c483e6ec44e570a5256

import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
<<<<<<< HEAD
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ProgressEvent;
import com.amazonaws.services.s3.model.ProgressListener;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
||||||| merged common ancestors
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.wisputil.ManifestGen;
import com.example.wisputil.SerializableLatLng;
=======
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.wisputil.SerializableLatLng;
>>>>>>> 0e9156b02f97d5c2923a3c483e6ec44e570a5256
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
<<<<<<< HEAD
			AWSCredentials myCredentials = new BasicAWSCredentials(id, key); 
			AmazonS3Client s3Client = new AmazonS3Client(myCredentials);        
			/*S3Object object = s3Client.getObject(new GetObjectRequest(bucket, "count"));
			StringBuilder s= new StringBuilder();
			InputStream reader = new BufferedInputStream(
					   object.getObjectContent());
					

					int read = -1;

					while ( ( read = reader.read() ) != -1 ) {
					    s.append(read);
					}
					reader.close();
			String str=s.toString();
			int i=Integer.valueOf(str);
			*/
			int i=s3Client.listObjects(bucket).getObjectSummaries().size();
			ObjectOutputStream out= new ObjectOutputStream(new FileOutputStream(main.getCacheDir()+File.separator+"stored.ogg"));
||||||| merged common ancestors
			Stor sto=params[0];
			Log.d("2", "2");
			SerializableLatLng loc= sto.getLocation();
			Log.d("2", "2");
			AmazonS3Client s3Client =   new AmazonS3Client( new BasicAWSCredentials( id, key ) );
			Log.d("2", "2");
			ObjectInputStream in=new ObjectInputStream(s3Client.getObject(new GetObjectRequest(bucket, "manifest")).getObjectContent());
			Log.d("2", "2");
			HashMap<String, SerializableLatLng> map=(HashMap<String, SerializableLatLng>)in.readObject();
			Log.d("2", "2");
			in.close();
			Log.d("2", "2");
			int i=map.size();
			Log.d("2", "2");
			map.put(""+i, loc);
			Log.d("2", "2");
			PutObjectRequest por= new PutObjectRequest(bucket, ""+i, new File(main.getCacheDir()+File.separator+"stored.wip"));
			Log.d("2", "2");
			s3Client.putObject(por);
			Log.d("2", "2");
			ObjectOutputStream out= new ObjectOutputStream(new FileOutputStream(main.getCacheDir()+File.separator+"manifest.man"));
			Log.d("2", "2");
=======
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
>>>>>>> 0e9156b02f97d5c2923a3c483e6ec44e570a5256
			out.flush();
<<<<<<< HEAD
			out.writeObject(params[0].sound);
||||||| merged common ancestors
			Log.d("2", "2");
			out.writeObject(map);
			Log.d("2", "2");
=======
			Log.d("2", "2");
			out.writeObject(Integer.valueOf(i+1));
			Log.d("2", "2");
>>>>>>> 0e9156b02f97d5c2923a3c483e6ec44e570a5256
			out.flush();
			out.close();
<<<<<<< HEAD
			TransferManager tx = new TransferManager(myCredentials);
	        ObjectMetadata newObjectMetadata = new ObjectMetadata();
	        newObjectMetadata.addUserMetadata("loc", params[0].location.toString());
	        newObjectMetadata.setContentLength(new File(main.getCacheDir()+File.separator+"stored.ogg").length());
	        Upload u= tx.upload(bucket,key,new FileInputStream(new File(main.getCacheDir()+File.separator+"stored.ogg")), newObjectMetadata);
	        u.addProgressListener(this);
			/*DataOutputStream outer= new DataOutputStream(new FileOutputStream(main.getCacheDir()+File.separator+"count"));
			outer.write(i);
			outer.close();
			por=new PutObjectRequest(bucket, ""+0, new File(main.getCacheDir()+File.separator+"count"));
			s3Client.putObject(por);*/
||||||| merged common ancestors
			Log.d("2", "2");
			por= new PutObjectRequest(bucket, "manifest", main.getCacheDir()+File.separator+"manifest.man" );
			Log.d("2", "2");
			s3Client.putObject(por);
			Log.d("2", "2");
=======
			Log.d("2", "2");
			por= new PutObjectRequest(bucket, "count", main.getCacheDir()+File.separator+"manifest.man" );
			Log.d("2", "2");
			s3Client.putObject(por);
			Log.d("2", "2");
			main.done();
>>>>>>> 0e9156b02f97d5c2923a3c483e6ec44e570a5256

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
