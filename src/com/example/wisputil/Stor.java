package com.example.wisputil;

import java.io.Serializable;

public class Stor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3093540784144607823L;
	public SerializableLatLng location;
	public Byte[] sound;
	public Stor(SerializableLatLng loc, Byte[] sou){
		location=loc;
		sound=sou;
	}
	public SerializableLatLng getLocation(){
		return location;
	}
	public Byte[] getSound(){
		return sound;
	}
}
