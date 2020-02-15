package wildfire.volunteers.smokegator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import wildfire.volunteers.smokegator.R;

import wildfire.volunteers.smokegator.adapter.PelengListAdapter;
import wildfire.volunteers.smokegator.data.PelengEntity;
import wildfire.volunteers.smokegator.viewmodel.PelengListViewModel;
import java.util.List;

public class PelengListActivity extends AppCompatActivity {

    private static final String TAG = "PelengListActivity";

    private PelengListAdapter mAdapter;
    private PelengListViewModel pelengListViewModel;

    FloatingActionButton get_peleng_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peleng_list);
        Log.d(TAG, "onCreate: started");


        pelengListViewModel = ViewModelProviders.of(this).get(PelengListViewModel.class);
        pelengListViewModel.init();

        //To observe changes done to the LiveData objects
        pelengListViewModel.getPelengEntity().observe(this, new Observer<List<PelengEntity>>() {
                @Override
                public void onChanged(@Nullable List<PelengEntity> mPelengs) {
                    mAdapter.notifyDataSetChanged();
                }
            });


        initRecyclerView();

        get_peleng_button = findViewById(R.id.getPelengActionButton);
        get_peleng_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GetPelengActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView mRecyclerView = findViewById(R.id.rvPelengs);
        mAdapter = new PelengListAdapter(this, pelengListViewModel.getPelengEntity().getValue());
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


    }

}
