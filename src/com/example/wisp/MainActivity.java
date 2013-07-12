package com.example.wisp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
			@Override //This is called when the button is pressed
			public void onClick(View v) {
				//increments clicked to tell whether it should record or stop recording based on even/odd
				clicked++;
				if (clicked%2!=0){
					//sets up all the recording shit
					MediaRecorder med= new MediaRecorder();
					med.setAudioSource(MediaRecorder.AudioSource.MIC);
					med.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					med.setOutputFile(getCacheDir().getAbsolutePath()+File.separator+"cachedsound.3gpp");
					med.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
					try {
						//starts recording
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
					//stops recording
					med.stop();
					med.release();
					//uses gpsGet's get location call to get location, writes location to byte array
					Location loc=gpsGet.getLocation();
				    ByteArrayOutputStream out = new ByteArrayOutputStream();
				    ObjectOutputStream os;
					try {
						os = new ObjectOutputStream(out);
						os.writeObject(loc);
						os.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//Gets the sound and initializes HTTPclient
					File file = new File(getCacheDir().getAbsolutePath(),"cachedsound.3gpp");
					try {
					  MultipartEntity entity = new MultipartEntity();
					 //adds location and sound to multipartentity then writes multipartentity to server
					  entity.addPart("location", new ByteArrayBody(out.toByteArray(), "location"));
					  out.close();
					  entity.addPart("sound", new FileBody(file));
					  ObjectOutputStream oos= new ObjectOutputStream(new FileOutputStream(getCacheDir().getAbsolutePath()+File.separator+"cachedshit.wis"));
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
