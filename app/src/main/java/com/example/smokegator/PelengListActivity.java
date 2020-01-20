package com.example.smokegator;

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
