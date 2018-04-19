package com.example.kzvda.menumanagementsystem;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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

import com.example.kzvda.menumanagementsystem.db.Model.MenuModel;
import com.example.kzvda.menumanagementsystem.db.Model.RestaurantModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {
    private Resources res;
    private List<? extends RestaurantModel> mDataset;

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
    public RestaurantListAdapter(Resources res) {
        this.res = res;
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

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        RoundedBitmapDrawable dr =
                RoundedBitmapDrawableFactory.create(res, drawableToBitmap(res.getDrawable(mDataset.get(position).getPhotoSrc())));
        dr.setCircular(true);
        holder.mImageView.setImageDrawable(dr);
        holder.mName.setText(mDataset.get(position).getName());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (mDataset != null)
            return mDataset.size();
        else return 0;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public void setProductList(final List<? extends RestaurantModel> menuList) {
        mDataset = menuList;
        notifyDataSetChanged();
    }
}