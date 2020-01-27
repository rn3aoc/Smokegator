package wildfire.volunteers.smokegator.data;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PelengsRepo {

    private static PelengsRepo instance;
    private ArrayList<PelengEntity> dataSet = new ArrayList<>();

    /* Singleton Pattern  */
    public static PelengsRepo getInstance(){
        if(instance == null ){
            instance = new PelengsRepo();
           }
        return instance;
    }

    //Mimic getting data from web source
   public MutableLiveData<List<PelengEntity>> getPelengEntity(){
        setPelengEntity();

        MutableLiveData<List<PelengEntity>> data = new MutableLiveData<>();

      // MutableLiveData<List<PelengEntity>> data;
         data.setValue(dataSet);
        return data;
    }

    //Mimic data retrieval from database
    private void setPelengEntity(){
        if(dataSet == null){
        dataSet.add(

                new PelengEntity(new LatLng(37.7510, 14.9934),
                        70.5f,
                        new Date(),
                        "Kreg"));}

    }

}
