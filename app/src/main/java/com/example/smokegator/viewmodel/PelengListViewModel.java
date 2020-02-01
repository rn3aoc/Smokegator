package com.example.smokegator.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.preference.PreferenceManager;

import com.example.smokegator.data.PelengEntity;
import com.example.smokegator.data.PelengsRepo;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PelengListViewModel extends AndroidViewModel{


    private MutableLiveData<List<PelengEntity>> mPelengs; //Holds Data
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>(); //Represent when a query is made
    private Context context;

    public PelengListViewModel(@NonNull Application application) {
        super(application);
    }

   /* public PelengListViewModel(Context context){
        this.context = context;
    }

    */

        public void init() {
            if(mPelengs == null){
                PelengsRepo mRepo = PelengsRepo.getInstance();
                mPelengs = mRepo.getPelengEntity();
            }

         }


    public void addNewValue(final PelengEntity pelengEntity){
            mIsUpdating.setValue(true);

           new AsyncTask<Void, Void, Void>() {
               @Override
               protected void onPostExecute(Void aVoid) {

                   List<PelengEntity> currentPeleng = mPelengs.getValue();

                    if(currentPeleng == null) {
                        currentPeleng = new ArrayList<>();
                        currentPeleng.add(pelengEntity);
                        mPelengs.postValue(currentPeleng);
                          } else {
                   currentPeleng.add(pelengEntity);
                   mPelengs.postValue(currentPeleng);
                   mIsUpdating.setValue(false);
               }
           }

                @Override
                protected Void doInBackground(Void... voids) {
                    try{
                        Thread.sleep(2000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();
        }

    public LiveData<List<PelengEntity>> getPelengEntity() { return mPelengs; }

    public LiveData<Boolean> getIsUpdating(){
        return mIsUpdating;
    }

    private LatLng FormFieldsToLatLng(double latitude, double longitude){

        return new LatLng(latitude, longitude);
    }


    public PelengEntity NewPelengEntity(double latitude,
                                        double longitude,
                                        float bearing){
        LatLng mLatLng =  FormFieldsToLatLng(latitude, longitude);
        Date timestamp = new Date();
        //String callsign = "Kreg";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplication());
        String callsign = sharedPreferences.getString("callsign", "");
        return new PelengEntity(mLatLng, bearing, timestamp, callsign);

    }


}
