package com.example.kzvda.menumanagementsystem.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kzvda.menumanagementsystem.R;

public class SettingsListFragment extends Fragment {

    //private static final boolean[][] isOn = {{true, true, true},{true, false, false},{true, true, false},{true, true, true},{true, true, true}};
    private static final boolean[][] isOn = {{true, false, false}, {true, true, false}};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        int usertype = sharedPref.getInt("usertype", 0);

        View manageNotifications = rootView.findViewById(R.id.settings_notifications);
        View support = rootView.findViewById(R.id.settings_support);


        manageNotifications.setVisibility((isOn[0][usertype]) ? View.VISIBLE : View.GONE);
        support.setVisibility((isOn[1][usertype]) ? View.VISIBLE : View.GONE);

        return rootView;
    }
}
