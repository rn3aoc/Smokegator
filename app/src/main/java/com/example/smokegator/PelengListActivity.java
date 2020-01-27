package com.example.smokegator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smokegator.adapter.PelengListAdapter;
import com.example.smokegator.data.PelengEntity;
import com.example.smokegator.viewmodel.PelengListViewModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;

public class PelengListActivity extends AppCompatActivity {

    private static final String TAG = "PelengListActivity";

    private PelengListAdapter mAdapter;
    private PelengListViewModel pelengListViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peleng_list);
        Log.d(TAG, "onCreate: started");

       // initWidgets();


            pelengListViewModel = ViewModelProviders.of(this).get(PelengListViewModel.class);
            pelengListViewModel.init();

            //To observe changes done to the LiveData objects
            pelengListViewModel.getPelengEntity().observe(this, new Observer<List<PelengEntity>>() {
                @Override
                public void onChanged(@Nullable List<PelengEntity> mPelengs) {
                    mAdapter.notifyDataSetChanged();
                }
            });


            initRecyclerView();

    }

    /*private void initWidgets() {
       // mFab = findViewById(R.id.fab_peleng_list);
        mRecyclerView = findViewById(R.id.rvPelengs);
       mAdapter = new PelengListAdapter(this, pelengListViewModel.getPelengEntity().getValue());
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
      //  mProgressBar = findViewById(R.id.progress_circular);


    }

     */


    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView mRecyclerView = findViewById(R.id.rvPelengs);
        mAdapter = new PelengListAdapter(this, pelengListViewModel.getPelengEntity().getValue());
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


    }




}









/*
// !!!!!!! The last working piece of example START
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smokegator.adapter.PelengListAdapter;
import com.example.smokegator.data.PelengEntity;
import com.example.smokegator.viewmodel.PelengListViewModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.security.Timestamp;

public class PelengListActivity extends AppCompatActivity implements PelengListAdapter.ItemClickListener {

    PelengListAdapter adapter;
    PelengListViewModel pelengListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peleng_list);

        // get viewmodel
        pelengListViewModel = ViewModelProviders.of(this).get(PelengListViewModel.class);


    // data to populate the RecyclerView with
        pelengListViewModel.getmPelengs().add(new PelengEntity(new LatLng(56.723642, 37.770276),
                70.5f,
                new Date(),
                "Kreg"));
        pelengListViewModel.getmPelengs().add(new PelengEntity(new LatLng(56.723642, 37.770276),
                70.5f,
                new Date(),
                "Kreg"));
        pelengListViewModel.getmPelengs().add(new PelengEntity(new LatLng(56.723642, 37.770276),
                70.5f,
                new Date(),
                "Kreg"));
        pelengListViewModel.getmPelengs().add(new PelengEntity(new LatLng(56.723642, 37.770276),
                70.5f,
                new Date(),
                "Kreg"));
        pelengListViewModel.getmPelengs().add(new PelengEntity(new LatLng(56.723642, 37.770276),
                70.5f,
                new Date(),
                "Kreg"));
        pelengListViewModel.getmPelengs().add(new PelengEntity(new LatLng(56.723642, 37.770276),
                70.5f,
                new Date(),
                "Kreg"));


       // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvPelengs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PelengListAdapter(this, pelengListViewModel.getmPelengs());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
// !!!!!!! The last working piece of example END
*/



/*
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.smokegator.viewmodel.PelengListViewModel;
import com.google.android.gms.maps.model.LatLng;

public class PelengListActivity extends AppCompatActivity {
    private RecyclerView pelengsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PelengListViewModel model = ViewModelProviders.of(this).get(PelengListViewModel.class);
        setContentView(R.layout.activity_peleng_list);
        initRecyclerView();
        // Test data
       // PelengListViewModel.addTestPeleng(setLatLng()) ;

    }
    private void initRecyclerView() {
        pelengsRecyclerView = findViewById(R.id.data_recycler_view);
        pelengsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
*/