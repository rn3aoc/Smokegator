package com.example.smokegator.utils;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class PelengData {
    public Date timestamp; // Ms since UNIX Creation
    public String callsign;
    public LatLng latLng;
    public float t_bearing; // True bearing
    public boolean approved;
    public String comment;
}
