package com.example.wisp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.wispdisplay.MapShow;
import com.example.wisputil.SerializableLatLng;
import com.example.wisputil.Stor;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

public class MainActivity extends Activity {
	boolean clicked=false;
	boolean stored=false;
	boolean playing =false;
	boolean lochere=false;
	boolean waitonloc=false;
	boolean waitonupload=false;
	boolean recording=false;
	Uploader upload= new Uploader(this);
	GPSGrabber gpsGet;
	Location loc=null;
	Stor sto;
    final MainActivity main=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Button b= (Button) findViewById(R.id.button1);
        Button b2= (Button) findViewById(R.id.button2);
        Button b3= (Button) findViewById(R.id.button3);
        Button b4= (Button) findViewById(R.id.mapswitch);
        gpsGet= new GPSGrabber(this);
        b.setOnClickListener(new View.OnClickListener() {
			MediaRecorder med= new MediaRecorder();
			
			@Override //This is called when the button is pressed
			public void onClick(View v) {
				clicked=!clicked;
				Log.d("click", "click");
				if (waitonupload){
					Toast toast = Toast.makeText(getApplicationContext(), "Please wait, processing", Toast.LENGTH_SHORT);
					toast.setDuration(5);
					toast.show();
				}
				else if (clicked&&!stored&&!lochere&&!waitonloc){
					//sets up all the recording shit
					Log.d("1", "1");
					med.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
					Log.d("1", "1");
					med.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					Log.d("1", "1");
					med.setOutputFile(getCacheDir()+File.separator+"cachedsound.3gpp");
					Log.d("1", "1");
					med.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
					Log.d("1", "1");
					try {
						//starts recording
						med.prepare();
						Log.d("1", "1");
						med.start();
						recording=true;
						Log.d("1", "1");
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}// TODO Auto-generated method stub
					Log.d("Player", med.toString());
				}
				else if(!clicked&&!stored&&!lochere&&!waitonloc){
					//stops recording
					Log.d("playerstop", med.toString());
					med.stop();
					med.reset();
					med.release();
					med=new MediaRecorder();
					playing=false;
					playing=false;
					waitonloc=true;
					//uses gpsGet's get location call to get location, writes location to byte array
					gpsGet.execute((Void)null);
					
				}
				else if (stored&&!waitonloc){
					Log.d("atupload", "atupload");
					waitonupload=true;
					upload.execute(sto);
					Log.d("1", "1");
					

					
				}
				else{
					Toast toast = Toast.makeText(getApplicationContext(), "Please wait, processing", Toast.LENGTH_SHORT);
					toast.setDuration(5);
					toast.show();
					Log.d("stored", stored+"");
					Log.d("waitonloc", waitonloc+"");
				}

			}
		});
        b2.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View v) {
        		Log.d("1", "1");
        		if (stored==true&&recording==false){
        	    MediaPlayer mp = new MediaPlayer();
        	    playing=!playing;
				if (playing==true){
				    mp = new MediaPlayer();

				    try {
				        mp.setDataSource(getCacheDir()+File.separator+"cachedsound.3gpp");
				        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
							
							@Override
							public void onCompletion(MediaPlayer mp) {
								mp.stop();
								mp.reset();
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
        	}
        });
        b3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("1", "1");
				if (stored){
				new AlertDialog.Builder(main)
				.setTitle("Delete Sound")
				.setMessage("Do you want to delete your sound?")
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

				    public void onClick(DialogInterface dialog, int whichButton) {
				        File f= new File(getCacheDir()+File.separator+"loc.gps");
				        f.delete();
				        f= new File(getCacheDir()+File.separator+"cachedsound.3gpp");
				        f.delete();
				        f=null;
				    	clicked=false;
				    	stored=false;
				    	playing =false;
				    	lochere=false;
				    	waitonloc=false;
				    	waitonupload=false;
				    	recording=false;
				    	loc=null;

				        
				    }})
				 .setNegativeButton(android.R.string.no, null).show();
				}
				else{
					Toast toast = Toast.makeText(getApplicationContext(), "You need a sound first!", Toast.LENGTH_SHORT);
					toast.setDuration(5);
					toast.show();
				}
			}
		});
        b4.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(main, MapShow.class);
				MainActivity.this.startActivity(intent);

				
			}
        	
        });
    }
    protected void done(){
    	clicked=false;
    	stored=false;
    	playing =false;
    	lochere=false;
    	waitonloc=false;
    	upload= new Uploader(this);
    	gpsGet= new GPSGrabber(this);
    	loc=null;
    	sto=null;
    	waitonupload=false;
    }
    protected void onLocationGet(Location l){
    	Log.d("GotToLocGet", ""+waitonloc);
    	if (waitonloc){
    	lochere=true;
    	loc=l;
    	try {
			FileInputStream in= new FileInputStream(getCacheDir()+File.separator+"cachedsound.3gpp");
			byte[] sou = IOUtils.toByteArray(in);
			in.close();
			Stor stor= new Stor(new SerializableLatLng(loc.getLatitude(), loc.getLongitude()), sou);
			sto=stor;

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		stored=true;
		lochere=false;
		waitonloc=false;
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
    public class GPSGrabber  extends AsyncTask<Void, Void, Void>
    implements
    GooglePlayServicesClient.ConnectionCallbacks,
    GooglePlayServicesClient.OnConnectionFailedListener{
		LocationClient mLocationClient;
		Location mCurrentLocation;
		MainActivity main;

    	public GPSGrabber(MainActivity man){
    		main=man;
    		
    	}

		@Override
		public void onConnectionFailed(ConnectionResult result) {
			Toast toast = Toast.makeText(main.getApplicationContext(), "Unable to Connect to Google Play Services", Toast.LENGTH_SHORT);
			toast.setDuration(5);
			toast.show();			
		}

		@Override
		public void onConnected(Bundle connectionHint) {
			while (mCurrentLocation==null){
	        mCurrentLocation = mLocationClient.getLastLocation();
	        Log.d("Loc", ""+(mCurrentLocation==null));
	        if (mCurrentLocation==null){
	        	Log.d("loop", "ran");
	        	 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
	                     main);
	             alertDialogBuilder
	                     .setMessage("GPS is disabled in your device. Enable it?")
	                     .setCancelable(false)
	                     .setPositiveButton("Enable GPS",
	                             new DialogInterface.OnClickListener() {
	                                 public void onClick(DialogInterface dialog,
	                                         int id) {
	                                     Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	                                     startActivity(intent);
	                                     mCurrentLocation= mLocationClient.getLastLocation();
	                                 }
	                             });
	             alertDialogBuilder.setNegativeButton("Cancel",
	                     new DialogInterface.OnClickListener() {
	                         public void onClick(DialogInterface dialog, int id) {
	                             dialog.cancel();
	                         }
	                     });
	             AlertDialog alert = alertDialogBuilder.create();
	             alert.show();
	        }
	        Log.d("Loc", ""+(mCurrentLocation==null));
	        Log.d("Loc", mCurrentLocation.getLatitude()+", "+mCurrentLocation.getLongitude());
	        

	        
		}
	        mLocationClient.disconnect();
	        Log.d("Loc", "locationdcd");
	        main.onLocationGet(mCurrentLocation);
		}

		@Override
		public void onDisconnected() {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected Void doInBackground(Void... params) {
	    	mCurrentLocation=null;
	    	mLocationClient = new LocationClient(main, this, this);
	        mLocationClient.connect();
			return null;
		}
    	
    }
}
