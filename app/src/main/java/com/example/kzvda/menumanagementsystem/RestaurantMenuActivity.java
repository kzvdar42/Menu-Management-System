package com.example.kzvda.menumanagementsystem;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.kzvda.menumanagementsystem.ViewModel.ViewModel;


public class RestaurantMenuActivity extends AppCompatActivity {
    private int restaurantId;
    private RestaurantMenuAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        Intent intent = getIntent();
        restaurantId = intent.getIntExtra(MainActivity.EXTRA_MESSAGE, 0);
        ViewModel mViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        mViewModel.getRestaurant(restaurantId).observe(this, restaurant -> {
            System.out.println(restaurant);
            toolbar.setTitle(restaurant.getName());
        });
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView mRecyclerView = findViewById(R.id.menu_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RestaurantMenuAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mViewModel.getRestaurantMenu(restaurantId).observe(this, words -> {
            mAdapter.setProductList(words);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.restaurant_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.restaurant_info_menu_item) {
            Intent intent = new Intent(this, RestaurantInfoActivity.class);
            intent.putExtra(MainActivity.EXTRA_MESSAGE, restaurantId);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
