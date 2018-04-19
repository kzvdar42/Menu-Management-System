package com.example.kzvda.menumanagementsystem;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;


public class RestaurantMenuActivity extends AppCompatActivity {
    private int restaurant;
    private RestaurantMenuAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        Intent intent = getIntent();
        restaurant = intent.getIntExtra(MainActivity.EXTRA_MESSAGE, 0);
        String[] title = {"Cacio e Vino", "OMC", "Wrap & Go"};
        toolbar.setTitle(title[restaurant]);
        setSupportActionBar(toolbar);


        toolbar.setNavigationOnClickListener(v -> finish());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // setting up the recycler view
        RecyclerView mRecyclerView = findViewById(R.id.menu_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAdapter = new RestaurantMenuAdapter();
        mRecyclerView.setAdapter(mAdapter);

        ViewModel mViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        mViewModel.getRestaurantMenu(restaurant).observe(this, words -> {
            mAdapter.setProductList(words);
        });
    }

    public void onClick(View v) {
        Intent intent = new Intent(this, RestaurantInfoActivity.class);
        intent.putExtra(MainActivity.EXTRA_MESSAGE, restaurant);
        startActivity(intent);
    }

}
