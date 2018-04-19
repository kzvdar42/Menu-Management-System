package com.example.kzvda.menumanagementsystem;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kzvda.menumanagementsystem.db.Model.MenuModel;

import java.util.List;

public class RestaurantMenuAdapter extends RecyclerView.Adapter<RestaurantMenuAdapter.ViewHolder> {

    private List<? extends MenuModel> mDataset;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mName;
        private TextView mDescription;
        private TextView mPrice;

        private ViewHolder(CardView v) {
            super(v);
            RelativeLayout rv = (RelativeLayout) v.getChildAt(0);
            mImageView = (ImageView) rv.getChildAt(0);
            mName = (TextView) rv.getChildAt(1);
            mDescription = (TextView) rv.getChildAt(2);
            mPrice = (TextView) rv.getChildAt(3);

        }
    }

    public RestaurantMenuAdapter() {
    }


    @NonNull
    @Override
    public RestaurantMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_menu_recycle_view_item, parent, false);
        return new RestaurantMenuAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantMenuAdapter.ViewHolder holder, int position) {
        if (mDataset != null) {
            MenuModel currentDish = mDataset.get(position);
            holder.mImageView.setImageResource(currentDish.getPhotoSrc());
            holder.mName.setText(currentDish.getName());
            holder.mDescription.setText(currentDish.getDescription());
            holder.mPrice.setText(currentDish.getPrice() + " P");
        }

    }

    @Override
    public int getItemCount() {
        if (mDataset != null)
            return mDataset.size();
        else return 0;

    }

    public void setProductList(final List<? extends MenuModel> menuList) {
        mDataset = menuList;
        notifyDataSetChanged();
    }

}
