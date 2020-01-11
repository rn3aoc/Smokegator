package com.example.smokegator.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.example.smokegator.MainActivity;
import com.example.smokegator.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

//import static com.google.maps.android.SphericalUtil.computeHeading;
import static android.content.Context.MODE_PRIVATE;
import static com.google.maps.android.SphericalUtil.computeOffset;



public class Peleng {

    //private Context mContext; //для поддержки контекста
    private static final String TAG = "Peleng";
    Context applicationContext = MainActivity.getContextOfApplication();
    public LatLng mLatLng;
    public float mBearing;
    public float mAlpha = 0.5f;
    //public Timestamp mTimestamp;
    private Object mMarker;
    private Object mPolyline;
    private SharedPreferences mSharedPreferences;


   // public Peleng(Context context) {
    public Peleng(LatLng latLng, float bearing) {
       // this.mContext = mContext;
        mLatLng = latLng;
        mBearing = bearing;
    //    this.context=context;
    //      LatLng mLatLng;
    //      float mBearing;
       // mSharedPreferences = context.getSharedPreferences("root_preferences", 0);
         mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        // mTimestamp = ....
    }

    public void show(GoogleMap map){

        mMarker = map.addMarker(new MarkerOptions()
            .position(mLatLng)
            //.anchor(0.5f,606.0f/612.0f)
            .anchor(0.5f,35f/42.0f)
            .flat(true)
            .rotation(mBearing)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.peleng_darkred_30px))
            .alpha(mAlpha));
            //.title(Integer.toString(mSharedPreferences.getInt("pelenglength",10)))
            //.alpha(mSharedPreferences.getFloat("pelenglength",50)/60));

        mPolyline = map.addPolyline(new PolylineOptions()
            .clickable(true)
                .width(2)
            .add(
                    mLatLng,
                    pelengToLatLng()
            ));
    }

    public void setAlpha() {

    }


    private LatLng pelengToLatLng(){
        double distance = mSharedPreferences.getInt("pelenglength", 15)*300; // 0-30 km
       // double distance = 10; // хардкод длины пеленга
        LatLng endPoint = computeOffset(mLatLng, distance, mBearing);
        return endPoint; // for testing only
    }



}
