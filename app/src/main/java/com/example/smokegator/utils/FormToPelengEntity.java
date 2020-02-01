package com.example.smokegator.utils;

import android.content.Context;
import android.provider.ContactsContract;
import android.widget.EditText;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.example.smokegator.data.PelengEntity;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class FormToPelengEntity {
    private final Context context;
    /*private String callsign;
    private double latitude;
    private double longitude;
    private float bearing;
    private LatLng latLng;

     */

    public FormToPelengEntity(Context context){
        this.context = context;
    }

    private LatLng FormFieldsToLatLng(double latitude, double longitude){

        return new LatLng(latitude, longitude);
    }


    public PelengEntity NewPelengEntity(double latitude,
   // public PelengEntity NewPelengEntity(double latitude,
                                                  double longitude,
                                                  float bearing){

        LatLng mLatLng =  FormFieldsToLatLng(latitude, longitude);
        Date timestamp = new Date();
        //String callsign = "Kreg";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String callsign = sharedPreferences.getString("callsign", "");
        return new PelengEntity(mLatLng, bearing, timestamp, callsign);

    }
}

