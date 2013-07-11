package com.example.wisp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.location.Location;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	int clicked=0;
	GPSGrabber gpsGet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b= (Button) findViewById(R.id.button1);
        gpsGet= new GPSGrabber(this);
        b.setOnClickListener(new View.OnClickListener() {
			MediaRecorder med;
			@Override
			public void onClick(View v) {
				clicked++;
				if (clicked%2!=0){
					MediaRecorder med= new MediaRecorder();
					med.setAudioSource(MediaRecorder.AudioSource.MIC);
					med.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					med.setOutputFile(getCacheDir().getAbsolutePath()+File.separator+"cachedsound.3gpp");
					med.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
					try {
						med.prepare();
						med.start();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}// TODO Auto-generated method stub
				}
				else{
					med.stop();
					med.release();
					Location loc=gpsGet.getLocation();
				    ByteArrayOutputStream out = new ByteArrayOutputStream();
				    ObjectOutputStream os;
					try {
						os = new ObjectOutputStream(out);
						os.writeObject(loc);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					File file = new File(getCacheDir().getAbsolutePath(),"cachedsound.3gpp");
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost("http://serverlocation");
					 
					try {
					  MultipartEntity entity = new MultipartEntity();
					 
					  entity.addPart("location", new ByteArrayBody(out.toByteArray(), "location"));
					  entity.addPart("sound", new FileBody(file));
					  httppost.setEntity(entity);
					  HttpResponse response = httpclient.execute(httppost);
					} catch (ClientProtocolException e) {
					} catch (IOException e) {
					}
				}
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
