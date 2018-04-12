package com.example.kzvda.menumanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RestaurantListFragment extends Fragment {

    public RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

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
        Object[][] myDataset = new Object[3][2];
        myDataset[0][0] = R.drawable.cacio_e_vino;
        myDataset[0][1] = "Cacio E Vino";
        myDataset[1][1] = "OMC";
        myDataset[1][0] = R.drawable.omc;
        myDataset[2][1] = "Wrap & Go";
        myDataset[2][0] = R.drawable.wrap_and_go;

        mAdapter = new RestaurantListAdapter(myDataset, getResources());
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    protected RecyclerView getRecycleView(){
        return mRecyclerView;
    }
}
