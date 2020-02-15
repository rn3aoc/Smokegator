package wildfire.volunteers.smokegator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.Manifest;

import wildfire.volunteers.smokegator.R;
import wildfire.volunteers.smokegator.ui.GroupActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static Context contextOfApplication;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static Context getContextOfApplication(){
        return contextOfApplication;
    }

    private static final int RC_SIGN_IN = 123;

    TextView status_text_view;
    ImageButton sync_image_button;
    ImageButton group_image_button;
    private boolean syncing;
    AuthUI auth;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contextOfApplication = getApplicationContext();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton list_image_button = findViewById(R.id.listImageButton);
        list_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PelengListActivity.class);
                startActivity(intent);
            }
        });
        ImageButton peleng_image_button = findViewById(R.id.pelengImageButton);
        peleng_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GetPelengActivity.class);
                startActivity(intent);
            }
        });
        ImageButton settings_image_button = findViewById(R.id.settingsImageButton);
        settings_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
        ImageButton ar_image_button = findViewById(R.id.ARImageButton);
        ar_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ARPelengatorActivity.class);
                startActivity(intent);
            }
        });
        group_image_button = findViewById(R.id.groupImageButton);
        group_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GroupActivity.class);
                startActivity(intent);
            }
        });
        sync_image_button = findViewById(R.id.syncImageButton);
        sync_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syncToggle();
            }
        });
        status_text_view = findViewById(R.id.statusTextView);

        // AuthUI auth = AuthUI.getInstance();

        /* FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            statusTextView.setText("Auth on");
        } else {
            statusTextView.setText("Auth off");
        }*/



        updateUI();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } */

        switch (id){
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void syncToggle(){

        if(FirebaseAuth.getInstance().getCurrentUser() != null) { // user logged in
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                            finish();
                            updateUI();
                            Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
        else { // user not logged in
            startActivityForResult(
                    // Get an instance of AuthUI based on the default app
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.GoogleBuilder().build()
                            ))
                            .build(),
                    RC_SIGN_IN);
            //updateUI();
            //Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateUI() {
        //update auth status indicator
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            status_text_view.setText("Auth on");
        else
            status_text_view.setText("Auth off");


    }


}
