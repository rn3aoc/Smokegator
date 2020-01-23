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
import java.util.List;
import static com.example.smokegator.utils.PelengEntityToString.EntityToString;

public class PelengListAdapter extends RecyclerView.Adapter<PelengListAdapter.ViewHolder> {
    private List<PelengEntity> mPelengEntities;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public PelengListAdapter(Context context, List<PelengEntity> tvPelengText) {
       this.mPelengEntities = tvPelengText;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pelenglist_row, parent, false);
        return new ViewHolder(view);
       }

    // binds the pelengEntities to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.myTextView.setText(EntityToString(mPelengEntities.get(position)));
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