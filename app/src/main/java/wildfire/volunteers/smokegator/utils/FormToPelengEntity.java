package wildfire.volunteers.smokegator.utils;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import wildfire.volunteers.smokegator.data.PelengEntity;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class FormToPelengEntity {
    private  SharedPreferences sharedPreferences;
    /*private String callsign;
    private double latitude;
    private double longitude;
    private float bearing;
    private LatLng latLng;

     */
    private Context context;


    private LatLng FormFieldsToLatLng(double latitude, double longitude){

        return new LatLng(latitude, longitude);
    }


    public PelengEntity NewPelengEntity(double latitude,
                                               double longitude,
                                               float bearing){


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());

        LatLng mLatLng =  FormFieldsToLatLng(latitude, longitude);
        Date timestamp = new Date();
        //String callsign = "Kreg";  //ToDo replace to stored user callsign
        String callsign = sharedPreferences.getString("callsign", "default");
        return new PelengEntity(mLatLng, bearing, timestamp, callsign);

    }
}

