package com.example.wisp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;

import android.location.Location;

public class Uploader {

	public Uploader(Location loc, MainActivity main){
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
		File file = new File(main.getCacheDir().getAbsolutePath(),"cachedsound.3gpp");
		try {
		  MultipartEntity entity = new MultipartEntity();
		 //adds location and sound to multipartentity then writes multipartentity to cache
		  entity.addPart("location", new ByteArrayBody(out.toByteArray(), "location"));
		  out.close();
		  entity.addPart("sound", new FileBody(file));
		  ObjectOutputStream oos= new ObjectOutputStream(new FileOutputStream(main.getCacheDir().getAbsolutePath()+File.separator+"cachedshit.wis"));
		  oos.writeObject(entity);
		  oos.flush();
		  
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
