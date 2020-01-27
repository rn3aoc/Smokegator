package com.example.smokegator;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smokegator.data.PelengEntity;
import com.example.smokegator.viewmodel.PelengListViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class    MapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap map;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

           sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext()/* Activity context */);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);


        mapView.getMapAsync(this);


        return v;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        // Updates the location and zoom of the MapView
        /*CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
        map.animateCamera(cameraUpdate);*/
        if (((MainActivity) getActivity()).checkLocationPermission()) {
            map.setMyLocationEnabled(true);
        }


        switch (sharedPreferences.getString("maptype", "")) {
            case "1":
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case "2":
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case "3":
                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case "4":
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;

        }

        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(56.723641, 37.770276), 12));

        PelengListViewModel pelengListViewModel = ViewModelProviders.of(this).get(PelengListViewModel.class);
        pelengListViewModel.init();
        LiveData<List<PelengEntity>> pelengEntities = pelengListViewModel.getPelengEntity();


        pelengEntities.observe(this, new Observer<List<PelengEntity>>() {
            @Override
            public void onChanged(@Nullable List<PelengEntity> mPelengs) {

                for (int i = 0; i < mPelengs.size(); i++) {
                    Marker mMarker = map.addMarker(new MarkerOptions()
                            .position(mPelengs.get(i).getLatLng())
                            .anchor(0.5f, 35f / 42.0f)
                            .flat(true)
                            .rotation(mPelengs.get(i).getBearing())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.peleng_darkred_30px))
                            .alpha(0.8f));
                }


            }
        });


    }


    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}

