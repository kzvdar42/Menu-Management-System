package com.example.kzvda.menumanagementsystem;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RestaurantMenuAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder>{

    private Object[][] mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mImageView;
        public TextView mName;
        public ViewHolder(RelativeLayout v) {
            super(v);
            mImageView =(ImageView) v.getChildAt(0);
            mName = (TextView) v.getChildAt(1);

        }
    }

    public RestaurantMenuAdapter (Object[][] mDataset){
        this.mDataset = mDataset;
    }


    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public RestaurantListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_view_item, parent, false);
        return new RestaurantListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantListAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mImageView.setImageResource((int)mDataset[position][0]);
        holder.mName.setText( (String) mDataset[position][1]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
