package com.example.smokegator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.smokegator.R;
import com.example.smokegator.data.PelengEntity;
import com.example.smokegator.utils.FormToPelengEntity;
import com.example.smokegator.utils.PelengEntityToString;
import com.example.smokegator.viewmodel.PelengListViewModel;

import java.util.List;

public class GetPelengActivity extends AppCompatActivity {
    private PelengListViewModel mViewModel;
    private static final String TAG = "GetPelengActivity";
    private EditText latitude;
    private EditText longitude;
    private EditText bearing;

    //private GetPelengActivityBinding mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // mBinding = DataBindingUtil. setContentView(this, R.layout.activity_get_peleng);
        setContentView(R.layout.activity_get_peleng);
        mViewModel = ViewModelProviders.of(this).get(PelengListViewModel.class);
        mViewModel.init();


            Button button = (Button) findViewById(R.id.button);
                    button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          latitude = (EditText)findViewById(R.id.editTextLat);
                                          longitude = findViewById(R.id.editTextLng);
                                          bearing = findViewById(R.id.editTextMBearing);

                                          double aDoublelat = Double.parseDouble(latitude.getText().toString());
                                          double aDoublelon = Double.parseDouble(longitude.getText().toString());
                                          float aFloatbearing = Float.parseFloat(bearing.getText().toString());

                                          PelengEntity nEntity = FormToPelengEntity.NewPelengEntity(
                                                  aDoublelat,
                                                  aDoublelon,
                                                  aFloatbearing
                                          );

                                          mViewModel.addNewValue(nEntity);



                                          if(nEntity != null){
                                              Toast.makeText(getApplicationContext(), PelengEntityToString.EntityToString(nEntity), Toast.LENGTH_SHORT).show();
                                          } else {Toast.makeText(getApplicationContext(), "NOTHING", Toast.LENGTH_SHORT).show();}


                                        }

                                      });


    }

}
