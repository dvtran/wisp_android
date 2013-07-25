package com.example.wisp;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.google.android.gms.maps.model.Marker;

public class SoundGetter extends AsyncTask<Marker, Void, String> {
	MarkListen mark;
	public SoundGetter(MarkListen m){
		mark=m;
	}
	@Override
	protected String doInBackground(Marker...m) {
		AmazonS3Client s3Client =   new AmazonS3Client( new BasicAWSCredentials( Uploader.id, Uploader.key ) );
		if (mark.locget.getSound(m[0])==-1){
			Toast toast = Toast.makeText(mark.locget.map.getApplicationContext(), "No Sound Found", Toast.LENGTH_SHORT);
			toast.setDuration(5);
			toast.show();
		}
		else{
			int x=mark.locget.getSound(m[0]);
		try {
			Log.d("Sound", x+"");
			long aLong = s3Client.getObjectMetadata(Uploader.bucket, x+".3gpp").getContentLength();
			int anInt = new BigDecimal(aLong).intValueExact();
			byte[] sound=new byte[anInt];
			DataInputStream in=new DataInputStream(s3Client.getObject(new GetObjectRequest(Uploader.bucket, x+".3gpp")).getObjectContent());
			in.readFully(sound);
			in.close();
			ByteArrayOutputStream out= new ByteArrayOutputStream();
			out.write(sound);
			out.writeTo(mark.locget.map.openFileOutput("sound.3gpp", MapShow.MODE_WORLD_READABLE));
			out.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		Log.d("File", mark.locget.map.getFilesDir().getAbsolutePath()+File.separator+"sound.3gpp");
		return mark.locget.map.getFilesDir().getAbsolutePath()+File.separator+"sound.3gpp";
	}
	@Override
	public void onPostExecute(String f){
		mark.listen(f);
	}
}
