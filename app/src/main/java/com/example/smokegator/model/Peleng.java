package com.example.smokegator.model;

/*
// Abstract peleng
*/

import com.google.android.gms.maps.model.LatLng;
import java.security.Timestamp;

public interface Peleng {

    public LatLng getLatLng();
    public void setLatLng(LatLng latLng);

    /*
    public double getLatitude();
    public void setLatitude(double latitude);
    public double getLongitude();
    public void setLongitude(double longitude); */

    public float getBearing();
    public void setBearing(float bearing);

    public Timestamp getTimestamp();
    public void setTimestamp(Timestamp timestamp); //for testing purpose only

    public String getCallsign();
    public void setCallsign(String callsign);
}