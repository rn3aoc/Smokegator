package wildfire.volunteers.smokegator;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.GeomagneticField;
import android.location.LocationProvider;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import wildfire.volunteers.smokegator.data.PelengEntity;
import wildfire.volunteers.smokegator.ui.MarkerIcon;
import wildfire.volunteers.smokegator.viewmodel.PelengListViewModel;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import static com.google.maps.android.SphericalUtil.computeOffset;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap map;
    private SharedPreferences sharedPreferences;
    private float inclination;

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


        MapStateManager mapStateManager = new MapStateManager(getContext());
        CameraPosition position = mapStateManager.getSavedCameraPosition();
        if (position != null) {
            CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
           // Toast.makeText(getContext(), "entering Resume State", Toast.LENGTH_SHORT).show();
            map.moveCamera(update);

           }

        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setCompassEnabled(false);
        map.getUiSettings().setRotateGesturesEnabled(false);
        //map.getUiSettings().setZoomControlsEnabled(true);
        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(56.723641, 37.770276), 12));
        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(, 12));

        PelengListViewModel pelengListViewModel = ViewModelProviders.of(this).get(PelengListViewModel.class);
        pelengListViewModel.init();
        LiveData<List<PelengEntity>> pelengEntities = pelengListViewModel.getPelengEntity();


        pelengEntities.observe(this, new Observer<List<PelengEntity>>() {
            @Override
            public void onChanged(@Nullable List<PelengEntity> mPelengs) {

                for (int i = 0; i < mPelengs.size(); i++) {
                    Marker mMarker = map.addMarker(new MarkerOptions()
                            .position(mPelengs.get(i).getLatLng())
                            .anchor(0.5f, 287f/300f) // hardcoded icon size
                            .flat(true)
                            .rotation(mPelengs.get(i).getBearing())
                            //.icon(BitmapDescriptorFactory.fromResource(R.drawable.peleng_darkred_30px))
                            .icon(BitmapDescriptorFactory.fromBitmap(new MarkerIcon(
                                    mPelengs.get(i).getBearing(),
                                    mPelengs.get(i).getTimestamp(),
                                    mPelengs.get(i).getCallsign()).getBitmap()))
                            .alpha(0.8f));
                    Polyline mPolyline = map.addPolyline(new PolylineOptions()
                            .clickable(true)
                            .width(2)
                            .add(
                                    mPelengs.get(i).getLatLng(),
                                    pelengToLatLng(mPelengs.get(i).getLatLng(), mPelengs.get(i).getBearing())
                            ));
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
        MapStateManager mapStateManager = new MapStateManager(getContext());
        mapStateManager.saveMapState(map);
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

    private LatLng pelengToLatLng(LatLng mLatLng, float mBearing){
        double distance = sharedPreferences.getInt("pelenglength", 15)*300; // 0-30 km
        return computeOffset(mLatLng, distance, mBearing);
    }

    public class MapStateManager {

        private static final String LONGITUDE = "longitude";
        private static final String LATITUDE = "latitude";
        private static final String ZOOM = "zoom";
        private static final String BEARING = "bearing";
        private static final String TILT = "tilt";
        private static final String PREFS_NAME ="mapCameraState";
        private SharedPreferences mapStatePrefs;

        MapStateManager(Context context) {
            mapStatePrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }

        void saveMapState(GoogleMap googleMap) {
            SharedPreferences.Editor editor = mapStatePrefs.edit();
            CameraPosition position = googleMap.getCameraPosition();

            editor.putFloat(LATITUDE, (float) position.target.latitude);
            editor.putFloat(LONGITUDE, (float) position.target.longitude);
            editor.putFloat(ZOOM, position.zoom);
            editor.putFloat(TILT, position.tilt);
            editor.putFloat(BEARING, position.bearing);
            editor.apply();
        }

        public CameraPosition getSavedCameraPosition() {
            double latitude = mapStatePrefs.getFloat(LATITUDE, 0);
            if (latitude == 0) {
                return null;
            }
            double longitude = mapStatePrefs.getFloat(LONGITUDE, 0);
            LatLng target = new LatLng(latitude, longitude);

            float zoom = mapStatePrefs.getFloat(ZOOM, 0);
            float bearing = mapStatePrefs.getFloat(BEARING, 0);
            float tilt = mapStatePrefs.getFloat(TILT, 0);

            CameraPosition position = new CameraPosition(target, zoom, tilt, bearing);
            return position;
        }

     }




}

