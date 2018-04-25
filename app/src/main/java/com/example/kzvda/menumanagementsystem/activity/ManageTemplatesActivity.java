package com.example.kzvda.menumanagementsystem.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.kzvda.menumanagementsystem.R;
import com.example.kzvda.menumanagementsystem.adapter.RestaurantMenuAdapter;
import com.example.kzvda.menumanagementsystem.viewModel.ViewModel;

public class ManageTemplatesActivity extends AppCompatActivity {
    private RestaurantMenuAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_templates);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.manage_templates);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> finish());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView mRecyclerView = findViewById(R.id.menu_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RestaurantMenuAdapter();
        mRecyclerView.setAdapter(mAdapter);

        SharedPreferences sharedPref = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        int restaurantId = sharedPref.getInt("restaurantId",0);
        ViewModel mViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        mViewModel.downloadTemplates(restaurantId);
        mViewModel.getTemplates(restaurantId).observe(this, words -> {
            mAdapter.setProductList(words);
        });

    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.fab:
                intent = new Intent(this, NewDishActivity.class);
                startActivity(intent);
                break;
        }
    }
}
