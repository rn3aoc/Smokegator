package com.example.smokegator.data;

import com.example.smokegator.model.Peleng;
import com.google.android.gms.maps.model.LatLng;

import java.security.Timestamp;

public class PelengEntity implements Peleng {

    private LatLng latLng;
    private float bearing;
    private Timestamp timestamp;
    private String callsign;

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
    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public void setTimestamp(Timestamp timestamp) {
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