package wildfire.volunteers.smokegator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import wildfire.volunteers.smokegator.R;
import wildfire.volunteers.smokegator.data.PelengEntity;
import wildfire.volunteers.smokegator.ui.CompassView;
import wildfire.volunteers.smokegator.utils.PelengEntityToString;

import java.util.List;

public class PelengListAdapter extends RecyclerView.Adapter<PelengListAdapter.ViewHolder> {
    private List<PelengEntity> mPelengEntities;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public PelengListAdapter(Context context, List<PelengEntity> entityList) {
       this.mPelengEntities = entityList;
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
        viewHolder.myCompassIndicatorView.updateAzimuth(mPelengEntities.get(position).getBearing());
        viewHolder.myRowStringView.setText(PelengEntityToString.EntityToString(mPelengEntities.get(position)));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mPelengEntities.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CompassView myCompassIndicatorView;
        TextView myRowStringView;


        ViewHolder(View itemView) {
            super(itemView);
            myCompassIndicatorView = itemView.findViewById(R.id.compass_view);
            myRowStringView = itemView.findViewById(R.id.tvPelengText);
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

