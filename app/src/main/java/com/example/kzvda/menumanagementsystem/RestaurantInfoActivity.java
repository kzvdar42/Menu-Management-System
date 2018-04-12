package com.example.kzvda.menumanagementsystem;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RestaurantInfoActivity extends AppCompatActivity {
    private boolean notificationsIsOn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        ImageView imageView = findViewById(R.id.imageView);
        TextView name = findViewById(R.id.restaurant_info_name);
        TextView subname = findViewById(R.id.restaurant_info_subname);
        int restaurant = getIntent().getIntExtra(MainActivity.EXTRA_MESSAGE,0);
        switch (restaurant) {
            case 0:
                imageView.setImageResource(R.drawable.cacio_e_vino);
                name.setText("Cacio e Vino");
                subname.setText("Italian Restaurant");
                break;
            case 1:
                imageView.setImageResource(R.drawable.omc);
                name.setText("OMC");
                subname.setText("Canteen in University building");
                break;
            case 2:
                imageView.setImageResource(R.drawable.wrap_and_go);
                name.setText("Wrap and Go");
                subname.setText("Шаурмичная");
                break;
        }
    }

    private void onClick(View v){
        int viewId = v.getId();
        if (viewId == R.id.floatingActionButton) {
            if (notificationsIsOn) {
                ((FloatingActionButton) v).setImageResource(R.drawable.ic_notification_off);
                notificationsIsOn = false;
            } else {
                ((FloatingActionButton) v).setImageResource(R.drawable.ic_notification_on);
                notificationsIsOn = true;
            }

        }
    }
}
