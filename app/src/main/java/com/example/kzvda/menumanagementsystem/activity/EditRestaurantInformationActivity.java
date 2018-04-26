package com.example.kzvda.menumanagementsystem.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.kzvda.menumanagementsystem.R;
import com.example.kzvda.menumanagementsystem.db.Entity.RestaurantEntity;
import com.example.kzvda.menumanagementsystem.viewModel.EditRestaurantInfoViewModel;

import java.util.Objects;

public class EditRestaurantInformationActivity extends AppCompatActivity {

    RestaurantEntity restaurant;
    int restaurantId;
    EditRestaurantInfoViewModel mViewModel;
    EditText restaurantName;
    EditText subtitle;
    EditText description;
    EditText location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant_information);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.edit_restaurant_info);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> finish());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        restaurantName = findViewById(R.id.restaurant_name);
        subtitle = findViewById(R.id.subtitle);
        description = findViewById(R.id.description);
        location = findViewById(R.id.location);

        SharedPreferences sharedPref = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        restaurantId = sharedPref.getInt("restaurantId", 0);
        mViewModel = ViewModelProviders.of(this).get(EditRestaurantInfoViewModel.class);
        mViewModel.getRestaurant(restaurantId).observe(this, restaurant -> {
            this.restaurant = restaurant;
            restaurantName.setText(restaurant.getName());
            subtitle.setText(restaurant.getSubname());
            description.setText(restaurant.getDescription());
            location.setText(restaurant.getLocation());
        });

    }

    public void onClick(View v) {
        finish();
    }
}