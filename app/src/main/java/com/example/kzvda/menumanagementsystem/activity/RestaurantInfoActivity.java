package com.example.kzvda.menumanagementsystem.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kzvda.menumanagementsystem.R;
import com.example.kzvda.menumanagementsystem.db.Entity.RestaurantEntity;
import com.example.kzvda.menumanagementsystem.viewModel.RestaurantInfoViewModel;

public class RestaurantInfoActivity extends AppCompatActivity {
    private boolean notificationsIsOn;
    private RestaurantEntity restaurant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> finish());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView imageView = findViewById(R.id.imageView);
        TextView name = findViewById(R.id.restaurant_info_name);
        TextView subname = findViewById(R.id.restaurant_info_subname);
        TextView description = findViewById(R.id.restaurant_info_description);

        int restaurantId = getIntent().getIntExtra(MainActivity.EXTRA_MESSAGE, 0);

        RestaurantInfoViewModel mViewModel = ViewModelProviders.of(this).get(RestaurantInfoViewModel.class);
        mViewModel.getRestaurant(restaurantId).observe(this, restaurant -> {
            this.restaurant = restaurant;
            imageView.setImageResource(restaurant.getPhotoSrc());
            name.setText(restaurant.getName());
            subname.setText(restaurant.getSubname());
            description.setText(restaurant.getDescription());
        });
    }

    public void onClick(View v) {
        int viewId = v.getId();
        Intent intent;
        switch (viewId) {
            case R.id.floatingActionButton:
                if (notificationsIsOn) {
                    ((FloatingActionButton) v).setImageResource(R.drawable.ic_notification_off);
                    notificationsIsOn = false;
                } else {
                    ((FloatingActionButton) v).setImageResource(R.drawable.ic_notification_on);
                    notificationsIsOn = true;
                }
                break;
            case R.id.restaurant_info_location_button:
                Uri gmmIntentUri = Uri.parse(restaurant.getLocation());
                intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
                break;
            case R.id.restaurant_info_website_button:
                String url = restaurant.getWebsite();
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                break;
            case R.id.restaurant_info_call_button:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + restaurant.getPhoneNumber()));
                startActivity(intent);
                break;

        }
    }
}
