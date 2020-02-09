package wildfire.volunteers.smokegator;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.hardware.GeomagneticField;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import wildfire.volunteers.smokegator.R;
import wildfire.volunteers.smokegator.data.PelengEntity;
import wildfire.volunteers.smokegator.ui.CompassView;
import wildfire.volunteers.smokegator.utils.FormToPelengEntity;
import wildfire.volunteers.smokegator.utils.PelengEntityToString;
import wildfire.volunteers.smokegator.viewmodel.PelengListViewModel;




public class GetPelengActivity extends AppCompatActivity {

    private static final String TAG = "GetPelengActivity";

    /**
     * Code used in requesting runtime permissions.
     */
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    /**
     * Constant used in the location settings dialog.
     */
    private static final int REQUEST_CHECK_SETTINGS = 0x1;

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    // Keys for storing activity state in the Bundle.
    private final static String KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates";
    private final static String KEY_LOCATION = "location";
    private final static String KEY_LAST_UPDATED_TIME_STRING = "last-updated-time-string";

    /*
    * Possible Azimuth values
     */
    private static final float MIN_BEARING = 0f;
    private static final float MAX_BEARING = 360f;


    /**
     * Provides access to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;
    /**
     * Provides access to the Location Settings API.
     */
    private SettingsClient mSettingsClient;
    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    private LocationRequest mLocationRequest;
    /**
     * Stores the types of location services the client is interested in using. Used for checking
     * settings to determine if the device has optimal location settings.
     */
    private LocationSettingsRequest mLocationSettingsRequest;
    /**
     * Callback for Location events.
     */
    private LocationCallback mLocationCallback;
    /**
     * Represents a geographical location.
     */
    private Location mCurrentLocation;

    /*
    * Local geomagnetic inclination object.
     */
    private GeomagneticField mGeomagneticField = new GeomagneticField(0,0,0, new Date().getTime());

    // UI Widgets.
    private Button mStartUpdatesButton; //ToDo change to on/off trigger button
    private Button mStopUpdatesButton;
    private ToggleButton mToggleButton;
    private EditText latitudeView;
    private EditText longitudeView;
    private EditText magBearing;
    private EditText trueBearing;
    private TextView accuracy;
    private TextView inclinationView;
    private CompassView compassView;

    /**
     * Tracks the status of the location updates request. Value changes when the user presses the
     * Start Updates and Stop Updates buttons.
     */
    private Boolean mRequestingLocationUpdates;

    /**
     * Time when the location was updated represented as a String.
     */
    private String mLastUpdateTime;

    private float mInclination;


    private PelengListViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_get_peleng);

        mStartUpdatesButton = (Button) findViewById(R.id.start_updates_button);
        mStopUpdatesButton = (Button) findViewById(R.id.stop_updates_button);
        mToggleButton = findViewById(R.id.GPStoggleButton);

        mViewModel = ViewModelProviders.of(this).get(PelengListViewModel.class);
        mViewModel.init();

        mRequestingLocationUpdates = false;
        mLastUpdateTime = "";

        // Update values using data stored in the Bundle.
        updateValuesFromBundle(savedInstanceState);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        // Kick off the process of building the LocationCallback, LocationRequest, and
        // LocationSettingsRequest objects.
        createLocationCallback();
        createLocationRequest();
        buildLocationSettingsRequest();

        latitudeView = (EditText)findViewById(R.id.editTextLat);
        longitudeView = findViewById(R.id.editTextLng);
        magBearing = findViewById(R.id.editTextMBearing);
        trueBearing = findViewById(R.id.editTextTBearing);
        accuracy = findViewById(R.id.AccuracyTextView);
        inclinationView = findViewById(R.id.inclinationTextView);
        compassView = findViewById(R.id.compassView);

        // Check latitude input
        latitudeView.addTextChangedListener(new TextWatcher() {
            double latitude;
            double longitude;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (latitudeView.getText().toString().isEmpty()) latitude = 0;
                    else latitude = Float.parseFloat(latitudeView.getText().toString());

                if (Math.abs(latitude) > 90d)
                    latitudeView.setError("Latitude cannot be more than 90 degrees");
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (latitudeView.getText().toString().isEmpty()) latitude = 0;
                    else latitude = Float.parseFloat(latitudeView.getText().toString());
                if (longitudeView.getText().toString().isEmpty()) longitude = 0;
                    else longitude = Float.parseFloat(longitudeView.getText().toString());

                mGeomagneticField = new GeomagneticField(
                        (float) latitude,
                        (float) longitude,
                        0f,
                        new Date().getTime());

                inclinationView.setText(String.format(Locale.US, "Incl. %.2f°", mGeomagneticField.getDeclination()));
            }
        });

        // Check longitude input
        longitudeView.addTextChangedListener(new TextWatcher() {
            double latitude;
            double longitude;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (latitudeView.getText().toString().isEmpty()) longitude = 0;
                    else longitude = Float.parseFloat(longitudeView.getText().toString());

                if (Math.abs(longitude) > 180d)
                    longitudeView.setError("Longitude cannot be more than 180 degrees");
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (latitudeView.getText().toString().isEmpty()) latitude = 0;
                    else latitude = Float.parseFloat(latitudeView.getText().toString());
                if (longitudeView.getText().toString().isEmpty()) longitude = 0;
                    else longitude = Float.parseFloat(longitudeView.getText().toString());
                mGeomagneticField = new GeomagneticField(
                        (float) latitude,
                        (float) longitude,
                        0f,
                        new Date().getTime());

                inclinationView.setText(String.format(Locale.US, "Incl. %.2f°", mGeomagneticField.getDeclination()));
            }
        });

        // Mag and True bearings EditText recounting
        magBearing.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                float magbearing;
                float truebearing;
                float inclination;
                // in case of editText field is cleaned
                if (magBearing.getText().toString().isEmpty()) magbearing = 0;
                else magbearing = Float.parseFloat(magBearing.getText().toString());

                if(magbearing > 360)
                    trueBearing.setError("Peleng value cannot be more than 360.");
                if(magbearing < 0)
                    trueBearing.setError("Peleng value cannot be negative.");

                // recalculate truebearing
                inclination = mGeomagneticField.getDeclination();
                if (magbearing - inclination < 0)
                    truebearing = magbearing - inclination + 360f;
                else {
                    if (magbearing - inclination > 360)
                        truebearing = magbearing - inclination - 360f;
                    else truebearing = magbearing - inclination;
                }

                trueBearing.setText(String.valueOf(truebearing));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Check azimuth, update compass indicator
        trueBearing.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                float bearing;
                // in case of editText field is cleaned
                if (trueBearing.getText().toString().isEmpty()) bearing = 0;
                    else bearing = Float.parseFloat(trueBearing.getText().toString());

                if(bearing > 360)
                    trueBearing.setError("Peleng value cannot be more than 360.");
                if(bearing < 0)
                    trueBearing.setError("Peleng value cannot be negative.");

                compassView.updateAzimuth(bearing);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });





        Button button = (Button) findViewById(R.id.button);
                    button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          // double aDoublelat = Double.parseDouble(latitude.getText().toString());
                                          //double aDoublelon = Double.parseDouble(longitude.getText().toString());
                                          //float aFloatbearing = Float.parseFloat(bearing.getText().toString());

                                          /*PelengEntity nEntity = FormToPelengEntity.NewPelengEntity(
                                                  aDoublelat,
                                                  aDoublelon,
                                                  aFloatbearing
                                          );

                                           */
                                         PelengEntity nEntity = mViewModel.NewPelengEntity(
                                                  Double.parseDouble(latitudeView.getText().toString()),
                                                  Double.parseDouble(longitudeView.getText().toString()),
                                                  Float.parseFloat(trueBearing.getText().toString()));

                                          mViewModel.addNewValue(nEntity);

                                          if(nEntity != null){
                                              Toast.makeText(getApplicationContext(), PelengEntityToString.EntityToString(nEntity), Toast.LENGTH_SHORT).show();
                                          } else {Toast.makeText(getApplicationContext(), "NOTHING", Toast.LENGTH_SHORT).show();}
                                        }

                                      });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Within {@code onPause()}, we remove location updates. Here, we resume receiving
        // location updates if the user has requested them.
        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        } else if (!checkPermissions()) {
            requestPermissions();
        }

        updateUI();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Remove location updates to save battery.
        stopLocationUpdates();
    }

    /**
     * Updates fields based on data stored in the bundle.
     *
     * @param savedInstanceState The activity state saved in the Bundle.
     */
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Update the value of mRequestingLocationUpdates from the Bundle, and make sure that
            // the Start Updates and Stop Updates buttons are correctly enabled or disabled.
            if (savedInstanceState.keySet().contains(KEY_REQUESTING_LOCATION_UPDATES)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        KEY_REQUESTING_LOCATION_UPDATES);
            }

            // Update the value of mCurrentLocation from the Bundle and update the UI to show the
            // correct latitude and longitude.
            if (savedInstanceState.keySet().contains(KEY_LOCATION)) {
                // Since KEY_LOCATION was found in the Bundle, we can be sure that mCurrentLocation
                // is not null.
                mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            }

            // Update the value of mLastUpdateTime from the Bundle and update the UI.
            if (savedInstanceState.keySet().contains(KEY_LAST_UPDATED_TIME_STRING)) {
                mLastUpdateTime = savedInstanceState.getString(KEY_LAST_UPDATED_TIME_STRING);
            }
            updateUI();
        }
    }

    /**
     * Updates all UI fields.
     */
    private void updateUI() {
        setButtonsEnabledState();
        updateLocationUI();
    }

    /**
     * Disables both buttons when functionality is disabled due to insuffucient location settings.
     * Otherwise ensures that only one button is enabled at any time. The Start Updates button is
     * enabled if the user is not requesting location updates. The Stop Updates button is enabled
     * if the user is requesting location updates.
     */
    private void setButtonsEnabledState() {
        if (mRequestingLocationUpdates) {
            mStartUpdatesButton.setEnabled(false);
            mStopUpdatesButton.setEnabled(true);
        } else {
            mStartUpdatesButton.setEnabled(true);
            mStopUpdatesButton.setEnabled(false);
        }
    }

    /**
     * Sets the value of the UI fields for the location latitude, longitude and last update time.
     */
    private void updateLocationUI() {
        if (mCurrentLocation != null) {
            //Toast.makeText(getApplicationContext(), String.valueOf(mCurrentLocation.getLatitude()), Toast.LENGTH_SHORT).show();
            latitudeView.setText(String.valueOf(mCurrentLocation.getLatitude()));
            longitudeView.setText(String.valueOf(mCurrentLocation.getLongitude()));
            accuracy.setText(String.valueOf(mCurrentLocation.getAccuracy()));
            inclinationView.setText(String.format(Locale.US, "Incl. %.2f°", mGeomagneticField.getDeclination()));


            //mLastUpdateTimeTextView.setText(String.format(Locale.ENGLISH, "%s: %s", mLastUpdateTimeLabel, mLastUpdateTime));
        }
    }

    /**
     * Creates a callback for receiving location events.
     */
    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                /*mGeomagneticField = new GeomagneticField(
                        (float)mCurrentLocation.getLatitude(),
                        (float)mCurrentLocation.getLongitude(),
                        (float)mCurrentLocation.getAltitude(),
                        new Date().getTime()); */
                updateLocationUI();
            }
        };
    }

    /**
     * Sets up the location request. Android has two location request settings:
     * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
     * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
     * the AndroidManifest.xml.
     * <p/>
     * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
     * interval (5 seconds), the Fused Location Provider API returns location updates that are
     * accurate to within a few feet.
     * <p/>
     * These settings are appropriate for mapping applications that show real-time location
     * updates.
     */
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Uses a {@link com.google.android.gms.location.LocationSettingsRequest.Builder} to build
     * a {@link com.google.android.gms.location.LocationSettingsRequest} that is used for checking
     * if a device has the needed location settings.
     */
    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.permission_rationale,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(GetPelengActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(GetPelengActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    /**
     * Requests location updates from the FusedLocationApi. Note: we don't call this unless location
     * runtime permission has been granted.
     */
    private void startLocationUpdates() {
        // Begin by checking if the device has the necessary location settings.
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        updateUI();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(GetPelengActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);
                                Toast.makeText(GetPelengActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                                mRequestingLocationUpdates = false;
                        }

                        updateUI();
                    }
                });
    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    private void stopLocationUpdates() {
        if (!mRequestingLocationUpdates) {
            Log.d(TAG, "stopLocationUpdates: updates never requested, no-op.");
            return;
        }

        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mRequestingLocationUpdates = false;
                        setButtonsEnabledState();
                    }
                });
    }

    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    /**
     * Handles the Start Updates button and requests start of location updates. Does nothing if
     * updates have already been requested.
     */
    public void startUpdatesButtonHandler(View view) {
        if (!mRequestingLocationUpdates) {
            mRequestingLocationUpdates = true;
            setButtonsEnabledState();
            startLocationUpdates();
        }
    }

    /**
     * Handles the Stop Updates button, and requests removal of location updates.
     */
    public void stopUpdatesButtonHandler(View view) {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        stopLocationUpdates();
    }

    public void toggleUpdatesHandler (View view) {
        if (mToggleButton.isChecked()){
            if (!mRequestingLocationUpdates) {
                mRequestingLocationUpdates = true;
                setButtonsEnabledState();
                startLocationUpdates();
            }
        }
        else if (!mToggleButton.isChecked()){
            stopLocationUpdates();
        }
    }

}
