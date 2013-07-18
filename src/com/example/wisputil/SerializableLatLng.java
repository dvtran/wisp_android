package com.example.wisputil;

import java.io.Serializable;

import com.google.android.gms.maps.model.LatLng;

public class SerializableLatLng implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 5030365755268065417L;
/**
	 * 
	 */
public final double Latitude;
public final double Longitude;

public SerializableLatLng(LatLng latLng) {
    Latitude=latLng.latitude;
    Longitude=latLng.longitude;
}   

//this is where the translation happens
public LatLng readResolve() {
    return new LatLng(Latitude, Longitude);
}

}