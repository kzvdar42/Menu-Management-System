package com.example.kzvda.menumanagementsystem.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kzvda.menumanagementsystem.R;
import com.example.kzvda.menumanagementsystem.db.Model.RestaurantModel;
import com.example.kzvda.menumanagementsystem.serverApi.Constants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {
    private Resources res;
    private List<? extends RestaurantModel> mDataset;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mImageView;
        public TextView mName;

        public ViewHolder(CardView v) {
            super(v);
            RelativeLayout rv = (RelativeLayout) v.getChildAt(0);
            mImageView = (ImageView) rv.getChildAt(0);
            mName = (TextView) rv.getChildAt(1);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RestaurantListAdapter(Context context) {
        this.context = context;
        this.res = context.getResources();
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public RestaurantListAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
                                                               int viewType) {

        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_list_recycle_view_item, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(context).load(Constants.BASE_URL + mDataset.get(position).getPhotoSrc()).resize(128, 128)
                .into(holder.mImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap imageBitmap = ((BitmapDrawable) holder.mImageView.getDrawable()).getBitmap();
                        RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(res, imageBitmap);
                        imageDrawable.setCircular(true);
                        imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                        holder.mImageView.setImageDrawable(imageDrawable);
                    }

                    @Override
                    public void onError() {
                        holder.mImageView.setImageResource(R.drawable.food_example);
                    }
                });
        holder.mName.setText(mDataset.get(position).getName());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

    public void setProductList(final List<? extends RestaurantModel> menuList) {
        mDataset = menuList;
        notifyDataSetChanged();
    }
}