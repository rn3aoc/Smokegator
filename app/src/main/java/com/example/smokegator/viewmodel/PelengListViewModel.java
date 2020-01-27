package com.example.smokegator.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smokegator.data.PelengEntity;
import com.example.smokegator.data.PelengsRepo;

import java.util.ArrayList;
import java.util.List;

public class PelengListViewModel extends ViewModel {


        private MutableLiveData<List<PelengEntity>> mPelengs; //Holds Data
        private PelengsRepo mRepo;
        private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>(); //Represent when a query is made
        //private PelengEntity currentPeleng;

        public void init() {
            if(mPelengs == null){
                mRepo = PelengsRepo.getInstance();
                mPelengs = mRepo.getPelengEntity();
            }

            /*if (mPelengs != null) {
                return;
            } else {
                mPelengs = new MutableLiveData<>();
                mRepo = PelengsRepo.getInstance();
                mPelengs = mRepo.getPelengEntity();
            }

             */
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

    public LiveData<List<PelengEntity>> getPelengEntity(){

            return mPelengs; }

    public LiveData<Boolean> getIsUpdating(){
        return mIsUpdating;
    }

}
