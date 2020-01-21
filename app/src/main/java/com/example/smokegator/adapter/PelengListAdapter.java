package com.example.smokegator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smokegator.R;
import com.example.smokegator.data.PelengEntity;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static android.os.Build.VERSION_CODES.M;

public class PelengListAdapter extends RecyclerView.Adapter<PelengListAdapter.ViewHolder> {
    private List<PelengEntity> mPelengEntities;
    //private List<String> mPelengEntities;
    //private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;

    // Turn Date to formatted String date
    private String PelengEntityDateToString(Date timestamp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ssz");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        return  simpleDateFormat.format(timestamp);
    }

    // Turn LatLng into String latitude and String longitude
    private String PelengEntityLatLngToString(LatLng latLng){
        return String.valueOf(latLng.latitude) + " " + String.valueOf(latLng.longitude);
    }

    // Turn PelengEntity into String
    private String PelengEntityToString(PelengEntity pelengEntity){
       //String pelengString = String.valueOf(pelengEntity.getBearing()) + " " + pelengEntity.getCallsign() ;
        return  PelengEntityLatLngToString(pelengEntity.getLatLng())
                + " "
                + pelengEntity.getBearing()
                + " "
                + pelengEntity.getCallsign()
                + "\n"
                + PelengEntityDateToString(pelengEntity.getTimestamp());
    }

    // data is passed into the constructor
    public PelengListAdapter(Context context, List<PelengEntity> tvPelengText) {
        this.mContext = context;
       // this.mInflater = LayoutInflater.from(context);
        this.mPelengEntities = tvPelengText;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pelenglist_row, parent, false);
        //View view = mInflater.inflate(R.layout.pelenglist_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
       // return new ViewHolder(view);
    }


    // binds the pelengEntities to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.myTextView.setText(PelengEntityToString(mPelengEntities.get(position)));
       // viewHolder.pelengEntities.setText(mPelengEntities.get(position).getBearing());
       // viewHolder.pelengEntities.setText(mPelengEntities.get(position).getCallsign());
        //String peleng = mPelengEntities.get(position);
        //viewHolder.myTextView.setText(peleng);
       // holder.myTextView.setText(peleng);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mPelengEntities.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tvPelengText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting pelengEntities at click position
    /*public String getItem(int id) {
        return mPelengEntities.get(id);
    }*/

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}






/*import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smokegator.R;
import com.example.smokegator.data.PelengEntity;
import com.example.smokegator.model.Peleng;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PelengListAdapter extends RecyclerView.Adapter<PelengListAdapter.PelengListViewHolder>{

    private List<Peleng> pelengList = new ArrayList<>();

    public void setItems(Collection<Peleng> pelengs) {
        pelengList.addAll(pelengs);
        notifyDataSetChanged();
    }

    public void clearItems() {
        pelengList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PelengListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pelenglist_row, parent, false);
        return new PelengListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PelengListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return pelengList.size();
    }


    class PelengListViewHolder extends RecyclerView.ViewHolder{
        private TextView pelengText;


        public PelengListViewHolder(View itemView) {
            super(itemView);
            pelengText = itemView.findViewById(R.id.tvPelengText);

        }


    }

}
*/