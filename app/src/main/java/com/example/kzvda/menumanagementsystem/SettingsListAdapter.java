package com.example.kzvda.menumanagementsystem;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;

public class SettingsListAdapter extends RecyclerView.Adapter<SettingsListAdapter.ViewHolder> {

    private LinkedList<Object[]> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView mName;

        private ViewHolder(RelativeLayout v) {
            super(v);
            mName = (TextView) v.getChildAt(0);

        }
    }

    public SettingsListAdapter(LinkedList<Object[]> mDataset) {
        this.mDataset = mDataset;
    }


    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public SettingsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.settings_recycle_view_item, parent, false);
        return new SettingsListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsListAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mName.setText((int) mDataset.get(position)[0]);
        holder.itemView.setId((int) mDataset.get(position)[1]);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
