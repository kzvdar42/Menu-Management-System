package com.example.kzvda.menumanagementsystem;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RestaurantMenuAdapter extends RecyclerView.Adapter<RestaurantMenuAdapter.ViewHolder>{

    private Object[][] mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private ImageView mImageView;
        private TextView mName;
        private TextView mDescription;
        private TextView mPrice;
        private ViewHolder(RelativeLayout v) {
            super(v);
            mImageView =(ImageView) v.getChildAt(0);
            mName = (TextView) v.getChildAt(1);
            mDescription = (TextView) v.getChildAt(2);
            mPrice = (TextView) v.getChildAt(3);

        }
    }

    public RestaurantMenuAdapter (Object[][] mDataset){
        this.mDataset = mDataset;
    }


    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public RestaurantMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_menu_recycle_view_item, parent, false);
        return new RestaurantMenuAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantMenuAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mImageView.setImageResource((int)mDataset[position][0]);
        holder.mName.setText( (String) mDataset[position][1]);
        holder.mDescription.setText( (String) mDataset[position][2]);
        holder.mPrice.setText( (String) mDataset[position][3]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
