package com.example.wisp;

import java.io.Serializable;

import com.google.android.gms.maps.model.LatLng;

public class SerializableLatLng implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = -9107934123689300030L;
public final double Latitude;
public final double Longitude;

public SerializableLatLng(LatLng latLng) {
    Latitude=latLng.latitude;
    Longitude=latLng.longitude;
}
public SerializableLatLng(Double lat, Double longs){
	Latitude=lat;
	Longitude=longs;
}

//this is where the translation happens
public LatLng readResolve() {
    return new LatLng(Latitude, Longitude);
}
public String toString(){
	return ""+Latitude+";"+Longitude;
}

}