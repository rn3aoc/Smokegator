package com.example.smokegator.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smokegator.data.PelengEntity;
import com.example.smokegator.utils.PelengData;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PelengListViewModel extends ViewModel {

    private List<PelengEntity> mPelengs = new ArrayList<>();

    public List<PelengEntity> getmPelengs(){
        return mPelengs;
    }


    //private List<PelengEntity> mPelengs = new ArrayList<>();
   /* private MutableLiveData<List<PelengEntity>> mPelengs;
    public LiveData<List<PelengEntity>> getPelengs() {
        if (mPelengs == null){
            mPelengs = new MutableLiveData<List<PelengEntity>>();
        }
        return mPelengs;
    }
   */

   /* public void addTestPeleng(PelengEntity testPelengEntity){
        mPelengs.add(testPelengEntity);
        };
    */

    }
