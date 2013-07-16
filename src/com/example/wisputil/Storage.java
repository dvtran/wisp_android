package com.example.wisputil;

import java.io.Serializable;

import android.location.Location;

public class Storage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7703373910652239983L;
	Location location;
	Byte[] sound;
	public Storage(Location loc, Byte[] s){
		location=loc;
		sound=s;
	}
	public Location getLocation(){
		return location;
	}
	public Byte[] getSound(){
		return sound;
	}
}
