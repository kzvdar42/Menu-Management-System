package com.example.kzvda.menumanagementsystem;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class RestaurantInfoActivity extends AppCompatActivity {
    private boolean notificationsIsOn;
    private Map<Integer, HashMap<String, Object>> mDataset;
    private int restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        mDataset = Data.getData();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView imageView = findViewById(R.id.imageView);
        TextView name = findViewById(R.id.restaurant_info_name);
        TextView subname = findViewById(R.id.restaurant_info_subname);
        restaurant = getIntent().getIntExtra(MainActivity.EXTRA_MESSAGE, 0);
        imageView.setImageResource((int) mDataset.get(restaurant).get("icon"));
        name.setText((String) mDataset.get(restaurant).get("name"));
        subname.setText((String) mDataset.get(restaurant).get("subname"));
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
                Uri gmmIntentUri = Uri.parse((String) mDataset.get(restaurant).get("location"));
                intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
                break;
            case R.id.restaurant_info_website_button:
                String url = (String) mDataset.get(restaurant).get("website");
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                break;
            case R.id.restaurant_info_call_button:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mDataset.get(restaurant).get("phone_num")));
                startActivity(intent);
                break;

        }
    }
}
