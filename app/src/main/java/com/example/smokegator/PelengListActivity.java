package com.example.smokegator;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smokegator.adapter.PelengListAdapter;
import com.example.smokegator.viewmodel.PelengListViewModel;

import java.util.ArrayList;

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
        pelengListViewModel.getmPelengs().add("Horse");
        pelengListViewModel.getmPelengs().add("Camel");
        pelengListViewModel.getmPelengs().add("Deer");
        pelengListViewModel.getmPelengs().add("Lama");
        pelengListViewModel.getmPelengs().add("Cow");

       /* ArrayList<String> Pelengs = new ArrayList<>();
        Pelengs.add("Horse");
        Pelengs.add("Cow");
        Pelengs.add("Camel");
        Pelengs.add("Sheep");
        Pelengs.add("Goat");
        */

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvPelengs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PelengListAdapter(this, pelengListViewModel.getmPelengs());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

   /* @Override
    public void onItemClick(View view, int position) {

    }*/
}




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