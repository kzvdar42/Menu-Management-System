package com.example.kzvda.menumanagementsystem;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsListFragment extends RecycleListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_restaurant_list, container, false);

        // setting up the recycler view
        mRecyclerView = rootView.findViewById(R.id.restaurants_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        Object[] myDataset = new Object[5];
        myDataset[0] = "Change personal info";
        myDataset[1] = "Manage notifications";
        myDataset[2] = "Support";
        myDataset[3] = "Delete account";
        myDataset[4] = "Log out";

        mAdapter = new SettingsListAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }
}
