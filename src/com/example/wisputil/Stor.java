package com.example.wisputil;

import java.io.Serializable;

public class Stor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3592718685712229322L;
	public SerializableLatLng location;
	public byte[] sound;
	public Stor(SerializableLatLng loc, byte[] sou){
		location=loc;
		sound=sou;
	}
	public SerializableLatLng getLocation(){
		return location;
	}
	public byte[] getSound(){
		return sound;
	}
}
