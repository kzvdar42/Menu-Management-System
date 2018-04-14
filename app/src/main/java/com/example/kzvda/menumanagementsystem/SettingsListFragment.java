package com.example.kzvda.menumanagementsystem;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

public class SettingsListFragment extends RecycleListFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recycle_view, container, false);
        int currentUserType = getArguments().getInt("userType");
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
            if (forUser[currentUserType]) {
                Object[] ob = {objects[0], objects[2]};
                currentSettings.add(ob);
            }

            mAdapter = new SettingsListAdapter(currentSettings);
            mRecyclerView.setAdapter(mAdapter);
        }
        return rootView;
    }
}
