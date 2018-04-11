package com.example.kzvda.menumanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class RestaurantMenuActivity extends AppCompatActivity {

    private static final String DESCRIPTION = "Lorem ipsum dolor sit amet," +
            "consectetur adipiscing elit, sed do" +
            "eiusmod tempor incididunt ut" +
            "labore et dolore magna aliqua.";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        Intent intent = getIntent();
        int message = intent.getIntExtra(RestaurantListActivity.EXTRA_MESSAGE, 0);
        String [] title = {"Cacio e Vino", "OMC", "Wrap & Go"};
        toolbar.setTitle(title[message]);
        setSupportActionBar(toolbar);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);
//        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        // setting up the recycler view
        mRecyclerView = findViewById(R.id.menu_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        Object[][] myDataset = new Object[3][4];
        myDataset[0][0] = R.drawable.ic_launcher_background;
        myDataset[0][1] = "Pizza Diavola";
        myDataset[0][2] = DESCRIPTION;
        myDataset[0][3] = "300 P";
        myDataset[1][0] = R.drawable.ic_launcher_background;
        myDataset[1][1] = "Pizza Margherita";
        myDataset[1][2] = DESCRIPTION;
        myDataset[1][3] = "300 P";
        myDataset[2][0] = R.drawable.ic_launcher_background;
        myDataset[2][1] = "Noodles soup";
        myDataset[2][2] = DESCRIPTION;
        myDataset[2][3] = "300 P";

        mAdapter = new RestaurantMenuAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }

}
