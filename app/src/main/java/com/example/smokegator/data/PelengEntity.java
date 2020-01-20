package com.example.smokegator.data;

import com.example.smokegator.model.Peleng;
import com.google.android.gms.maps.model.LatLng;

//import java.security.Timestamp;
import java.util.Date;

public class PelengEntity implements Peleng {

    private LatLng latLng;
    private float bearing;
    private Date timestamp;
    private String callsign;

    public PelengEntity(LatLng latLng, float bearing, Date timestamp, String callsign ){

        this.latLng = latLng;
        this.bearing = bearing;
        this.timestamp = timestamp;
        this.callsign = callsign;

    }

    @Override
    public LatLng getLatLng() {
        return latLng;
    }

    @Override
    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public float getBearing() {
        return bearing;
    }

    @Override
    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    @Override
    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String getCallsign() {
        return callsign;
    }

    @Override
    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }
}