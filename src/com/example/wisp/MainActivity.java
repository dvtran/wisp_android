package com.example.wisp;

import java.io.File;
import java.io.IOException;
import com.example.wisp.R;
import android.app.Activity;
import android.location.Location;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	boolean clicked=false;
	boolean stored=false;
	boolean playing =false;
	GPSGrabber gpsGet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b= (Button) findViewById(R.id.button1);
        Button b2= (Button) findViewById(R.id.button2);
        gpsGet= new GPSGrabber(this);
        b.setOnClickListener(new View.OnClickListener() {
			MediaRecorder med;
		    Location loc;

			@Override //This is called when the button is pressed
			public void onClick(View v) {
				//increments clicked to tell whether it should record or stop recording based on even/odd
				clicked=!clicked;
				if (clicked&&!stored){
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
				else if(!clicked&&!stored){
					//stops recording
					med.stop();
					med.release();
					//uses gpsGet's get location call to get location, writes location to byte array
					loc=gpsGet.getLocation();
					stored=true;
				}
				else{
					//Upload
					//new Uploader(loc, getCacheDir().getAbsoluteFile());
				}

			}
		});
        b2.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View v) {
        	    MediaPlayer mp = new MediaPlayer();
        	    playing=false;
				if (!playing){
				    mp = new MediaPlayer();

				    try {
				        mp.setDataSource(getCacheDir().getAbsolutePath()+File.separator+"cachedsound.3gpp");
				        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
							
							@Override
							public void onCompletion(MediaPlayer mp) {
								mp.stop();
								playing=false;
								
							}
						});
				        mp.prepare();
				        playing=true;
				        mp.start();
				    } catch (Exception e) {
				        e.printStackTrace();
				    }
				}
				else{
					mp.pause();
					playing=false;
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
