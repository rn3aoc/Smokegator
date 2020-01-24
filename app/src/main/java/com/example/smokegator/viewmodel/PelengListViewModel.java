package com.example.smokegator.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smokegator.data.PelengEntity;
import com.example.smokegator.data.PelengsRepo;
import com.example.smokegator.utils.PelengData;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PelengListViewModel extends ViewModel {

    //private List<PelengEntity> Pelengs = new ArrayList<>();

   // public List<PelengEntity> getmPelengs(){
  //      return mPelengs;
   // }

        //Mutable = Can be indirectly change, unlike LiveData, which can only be observed
        private MutableLiveData<List<PelengEntity>> mPelengs; //Holds Data
        private PelengsRepo mRepo;
        private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>(); //Represent when a query is made
        //private List<PelengEntity> Pelengs = new ArrayList<>();


        public void init(){
            if(mPelengs != null){
                return;
            }
            mRepo = PelengsRepo.getInstance();
            mPelengs = mRepo.getPelengEntity();
            }

        public void addNewValue(final PelengEntity pelengEntity){
            mIsUpdating.setValue(true);

           new AsyncTask<Void, Void, Void>(){
                @Override
                protected void onPostExecute(Void aVoid) {
                    List<PelengEntity> currentPeleng = mPelengs.getValue();
                    currentPeleng.add(pelengEntity);
                    mPelengs.postValue(currentPeleng);
                    mIsUpdating.setValue(false);
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

        public LiveData<List<PelengEntity>> getPelengEntity(){ return mPelengs; }

        public LiveData<Boolean> getIsUpdating(){
            return mIsUpdating;
        }

}
