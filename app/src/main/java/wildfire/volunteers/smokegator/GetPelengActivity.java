package wildfire.volunteers.smokegator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import wildfire.volunteers.smokegator.R;
import wildfire.volunteers.smokegator.data.PelengEntity;
import wildfire.volunteers.smokegator.utils.FormToPelengEntity;
import wildfire.volunteers.smokegator.utils.PelengEntityToString;
import wildfire.volunteers.smokegator.viewmodel.PelengListViewModel;

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
                                                  Double.parseDouble(latitude.getText().toString()),
                                                  Double.parseDouble(longitude.getText().toString()),
                                                  Float.parseFloat(bearing.getText().toString()));

                                          mViewModel.addNewValue(nEntity);



                                          if(nEntity != null){
                                              Toast.makeText(getApplicationContext(), PelengEntityToString.EntityToString(nEntity), Toast.LENGTH_SHORT).show();
                                          } else {Toast.makeText(getApplicationContext(), "NOTHING", Toast.LENGTH_SHORT).show();}


                                        }

                                      });


    }

}
