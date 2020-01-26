package com.example.smokegator.utils;

import android.provider.ContactsContract;
import android.widget.EditText;
import android.content.SharedPreferences;

import com.example.smokegator.data.PelengEntity;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class FormToPelengEntity {
    private static SharedPreferences sharedPreferences;
    /*private String callsign;
    private double latitude;
    private double longitude;
    private float bearing;
    private LatLng latLng;

     */



    private static LatLng FormFieldsToLatLng(double latitude, double longitude){

        return new LatLng(latitude, longitude);
    }


    public static PelengEntity NewPelengEntity(double latitude,
                                                  double longitude,
                                                  float bearing){

        LatLng mLatLng =  FormFieldsToLatLng(latitude, longitude);
        Date timestamp = new Date();
        String callsign = "Kreg";
        //String callsign = sharedPreferences.getString("callsign", "");
        return new PelengEntity(mLatLng, bearing, timestamp, callsign);

    }
}

