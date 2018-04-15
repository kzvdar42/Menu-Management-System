package com.example.kzvda.menumanagementsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;

public class EditRestaurantInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant_information);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.edit_restaurant_info);
        setSupportActionBar(toolbar);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPref = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        HashMap<String, Object> restaurant = Data.getData().get(sharedPref.getInt("restaurant", 0));

        EditText restaurantName = findViewById(R.id.restaurant_name);
        EditText subtitle = findViewById(R.id.subtitle);
        EditText description = findViewById(R.id.description);
        EditText location = findViewById(R.id.location);

        restaurantName.setText((String)restaurant.get("name"));
        subtitle.setText((String)restaurant.get("subname"));
        description.setText((String)restaurant.get("description"));
        location.setText((String)restaurant.get("location"));

    }

    public void onClick(View v) {
        finish();
    }
}