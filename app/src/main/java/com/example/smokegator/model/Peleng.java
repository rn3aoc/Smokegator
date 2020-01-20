package com.example.smokegator.model;

/*
// Abstract peleng
*/

import com.google.android.gms.maps.model.LatLng;
import java.security.Timestamp;
import java.util.Date;

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

    public Date getTimestamp();
    public void setTimestamp(Date timestamp); //for testing purpose only

    public String getCallsign();
    public void setCallsign(String callsign);
}