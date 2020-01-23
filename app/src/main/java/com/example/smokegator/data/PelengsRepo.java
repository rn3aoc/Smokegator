package com.example.smokegator.data;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PelengsRepo {

    private static PelengsRepo instance;
    private ArrayList<PelengEntity> dataSet = new ArrayList<>();

    /* Singleton Pattern  */
    public static PelengsRepo getInstance(){
        if(instance == null ){
            instance = new PelengsRepo();
        }
        return instance;
    }

    //Mimic getting data from web source
    public MutableLiveData<List<PelengEntity>> getPelengEntity(){
        setPelengEntity();

        MutableLiveData<List<PelengEntity>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    //Mimic data retrieval from database
    private void setPelengEntity(){
        dataSet.add(
                new PelengEntity(new LatLng(56.723642, 37.770276),
                        70.5f,
                        new Date(),
                        "Kreg"));
        dataSet.add(
                new PelengEntity(new LatLng(56.723642, 37.770276),
                        70.5f,
                        new Date(),
                        "Kreg"));
        dataSet.add(
                new PelengEntity(new LatLng(56.723642, 37.770276),
                        70.5f,
                        new Date(),
                        "Kreg"));
        dataSet.add(
                new PelengEntity(new LatLng(56.723642, 37.770276),
                        70.5f,
                        new Date(),
                        "Kreg"));
        dataSet.add(
                new PelengEntity(new LatLng(56.723642, 37.770276),
                        70.5f,
                        new Date(),
                        "Kreg"));


    }
}
