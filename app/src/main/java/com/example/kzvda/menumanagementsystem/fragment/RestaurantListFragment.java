package com.example.kzvda.menumanagementsystem.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kzvda.menumanagementsystem.R;
import com.example.kzvda.menumanagementsystem.adapter.RestaurantListAdapter;
import com.example.kzvda.menumanagementsystem.viewModel.MainViewModel;

import java.util.Objects;

public class RestaurantListFragment extends Fragment {

    public RecyclerView mRecyclerView;
    public RestaurantListAdapter mAdapter;
    public LinearLayoutManager mLayoutManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recycle_view, container, false);

        // setting up the recycler view
        mRecyclerView = rootView.findViewById(R.id.restaurants_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)

        mAdapter = new RestaurantListAdapter(getResources());
        mRecyclerView.setAdapter(mAdapter);

        MainViewModel mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        mViewModel.getRestaurantsList().observe(this, words -> {
            mAdapter.setProductList(words);
        });
        return rootView;
    }
}
