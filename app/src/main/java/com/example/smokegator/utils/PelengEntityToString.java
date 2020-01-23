package com.example.smokegator.utils;

import com.example.smokegator.data.PelengEntity;
import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class PelengEntityToString {

    // Turns Date to formatted String date
    @NotNull
    private static String DateToString(Date timestamp){
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ssz", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        return  simpleDateFormat.format(timestamp);
    }

    // Turns LatLng into String latitude and String longitude
    @Contract(pure = true)
    @org.jetbrains.annotations.NotNull
    private static String LatLngToString(@NotNull LatLng latLng){
        String latitude;
        String longitude;
        if(latLng.latitude < 0){ latitude = latLng.latitude * (-1) + "S";
        } else {latitude = latLng.latitude + "N" ;}
        if(latLng.longitude < 0){longitude = latLng.longitude * (-1) + "W";
        } else {longitude = latLng.longitude + "E";}
        return latitude + " " + longitude;
    }

    @NotNull
    public static String EntityToString(@NotNull PelengEntity pelengEntity){

        return  LatLngToString(pelengEntity.getLatLng())
                + " ("
                + pelengEntity.getBearing()
                + "°) "
                + pelengEntity.getCallsign()
                + "\n"
                + DateToString(pelengEntity.getTimestamp());
    }
}
