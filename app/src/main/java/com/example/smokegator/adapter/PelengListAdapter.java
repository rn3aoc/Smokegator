package com.example.smokegator.adapter;

import android.view.LayoutInflater;
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
