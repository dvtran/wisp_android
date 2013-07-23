package com.example.wisp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.wisputil.SerializableLatLng;

public class ManifestGen {
	public ManifestGen(MainActivity main){
		AmazonS3Client s3Client =   new AmazonS3Client( new BasicAWSCredentials( Uploader.id, Uploader.key ) );
		try {
			ObjectOutputStream out= new ObjectOutputStream(new FileOutputStream(main.getCacheDir()+File.separator+"count"));
			out.flush();
			out.writeObject(Integer.valueOf(0));
			out.flush();
			out.close();
			PutObjectRequest por= new PutObjectRequest(Uploader.bucket, "count", new File(main.getCacheDir()+File.separator+"count"));
			s3Client.putObject(por);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
