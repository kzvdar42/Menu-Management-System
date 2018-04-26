package com.example.kzvda.menumanagementsystem.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.kzvda.menumanagementsystem.R;
import com.example.kzvda.menumanagementsystem.serverApi.RequestBodies.AddDishRequest;
import com.example.kzvda.menumanagementsystem.viewModel.NewDishViewModel;

import java.util.Objects;

public class NewDishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dish);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.new_dish);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> finish());
        ActionBar actionbar = getSupportActionBar();
        Objects.requireNonNull(actionbar).setDisplayHomeAsUpEnabled(true);
    }

    public void onClick(View v) {
        String name = ((TextView) findViewById(R.id.setName)).getText().toString();
        String description = ((TextView) findViewById(R.id.setDescription)).getText().toString();
        boolean addToMenu = ((CheckBox) findViewById(R.id.add_to_templates)).isChecked();
        int price = Integer.parseInt(((TextView) findViewById(R.id.setPrice)).getText().toString());
        SharedPreferences sharedPref = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        AddDishRequest menuEntity = new AddDishRequest(sharedPref.getInt("restaurantId", -1), name, description, price, addToMenu);
        NewDishViewModel mViewModel = ViewModelProviders.of(this).get(NewDishViewModel.class);
        mViewModel.uploadToMenu(menuEntity);
        finish();
    }
}
