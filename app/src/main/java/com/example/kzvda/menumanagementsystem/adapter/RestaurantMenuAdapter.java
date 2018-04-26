package com.example.kzvda.menumanagementsystem.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kzvda.menumanagementsystem.R;
import com.example.kzvda.menumanagementsystem.db.Model.MenuModel;
import com.example.kzvda.menumanagementsystem.serverApi.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantMenuAdapter extends RecyclerView.Adapter<RestaurantMenuAdapter.ViewHolder> {

    private List<? extends MenuModel> mDataset;
    private Context context;

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

    public RestaurantMenuAdapter(Context context) {
        this.context = context;
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
            Picasso.with(context).load(Constants.BASE_URL + mDataset.get(position).getPhotoSrc()).fit().into(holder.mImageView);
            holder.mName.setText(currentDish.getName());
            holder.mDescription.setText(currentDish.getDescription());
            holder.mPrice.setText(currentDish.getPrice() + " \u20BD");
        }

    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

    public void setProductList(final List<? extends MenuModel> menuList) {
        mDataset = menuList;
        notifyDataSetChanged();
    }

}
