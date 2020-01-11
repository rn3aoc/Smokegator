package com.example.smokegator;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smokegator.utils.Peleng;
import com.example.smokegator.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.util.Objects;

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
        if(((MainActivity)getActivity()).checkLocationPermission()) {
            map.setMyLocationEnabled(true);
        }


        switch (sharedPreferences.getString("maptype", "")){
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

        Peleng Peleng1 = new Peleng(new LatLng(56.723642, 37.770276), 70.5f);
        Peleng1.show(map);
        Peleng Peleng2 = new Peleng(new LatLng(56.723642, 37.770276), 132f);
        Peleng2.show(map);
        Peleng Peleng3 = new Peleng(new LatLng(56.723642, 37.770276), 292f); //помойка в Кунилово
        Peleng3.show(map);
        Peleng Peleng4 = new Peleng(new LatLng(56.649173, 37.722923), 55f);
        Peleng4.show(map);
        Peleng Peleng5 = new Peleng(new LatLng(56.787875, 37.821680), 163f);
        Peleng5.show(map);
    }



    /*@Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    map.getUiSettings().setMyLocationButtonEnabled(true);
                    map.setMyLocationEnabled(true);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    map.getUiSettings().setMyLocationButtonEnabled(false);
                    map.setMyLocationEnabled(false);

                }
                return;
            }

        }
    }

    */



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

