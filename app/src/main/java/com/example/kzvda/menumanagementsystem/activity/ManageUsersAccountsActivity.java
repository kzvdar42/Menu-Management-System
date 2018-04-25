package com.example.kzvda.menumanagementsystem.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.kzvda.menumanagementsystem.Data;
import com.example.kzvda.menumanagementsystem.R;
import com.example.kzvda.menumanagementsystem.adapter.RestaurantMenuAdapter;
import com.example.kzvda.menumanagementsystem.adapter.SimpleListAdapter;

import java.util.LinkedList;
import java.util.Objects;

public class ManageUsersAccountsActivity extends AppCompatActivity {
    protected RecyclerView mRecyclerView;
    protected SimpleListAdapter mAdapter;
    protected LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users_accounts);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.users);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> finish());
        ActionBar actionbar = getSupportActionBar();
        Objects.requireNonNull(actionbar).setDisplayHomeAsUpEnabled(true);

        RecyclerView mRecyclerView = findViewById(R.id.menu_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

//        mAdapter = new SimpleListAdapter();
//        mRecyclerView.setAdapter(mAdapter);
    }
}
