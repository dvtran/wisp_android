package com.example.wisputil;

import java.io.Serializable;

import com.google.android.gms.maps.model.LatLng;

public class Storage implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 471779142407741010L;
	SerializableLatLng location;
	Byte[] sound;
	public Storage(SerializableLatLng loc, Byte[] s){
		location=loc;
		sound=s;
	}
	public LatLng getLocation(){
		return location.readResolve();
	}
	public Byte[] getSound(){
		return sound;
	}
}
