package com.example.kzvda.menumanagementsystem.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kzvda.menumanagementsystem.Data;
import com.example.kzvda.menumanagementsystem.R;
import com.example.kzvda.menumanagementsystem.adapter.SimpleListAdapter;

import java.util.LinkedList;

public class SettingsListFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    protected SimpleListAdapter mAdapter;
    protected LinearLayoutManager mLayoutManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recycle_view, container, false);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        // setting up the recycler view
        mRecyclerView = rootView.findViewById(R.id.restaurants_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        LinkedList<Object[]> currentSettings = new LinkedList<>();
        for (Object[] objects : Data.getSettingsList()) {
            boolean[] forUser = (boolean[]) objects[1];
            if (forUser[sharedPref.getInt("usertype", 0)]) {
                Object[] ob = {objects[0], objects[2]};
                currentSettings.add(ob);
            }

            mAdapter = new SimpleListAdapter(currentSettings);
            mRecyclerView.setAdapter(mAdapter);
        }
        return rootView;
    }
}
