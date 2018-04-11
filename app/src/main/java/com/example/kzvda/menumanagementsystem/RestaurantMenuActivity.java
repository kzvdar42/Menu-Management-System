package com.example.kzvda.menumanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

public class RestaurantMenuActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Cacio e Vino");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        int message = intent.getIntExtra(RestaurantListActivity.EXTRA_MESSAGE, 0);
        String [] title = {"Cacio e Vino", "OMC", "Wrap & Go"};
        toolbar.setTitle(title[message]);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        // setting up the recycler view
        mRecyclerView = findViewById(R.id.menu_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        Object[][] myDataset = new Object[3][2];
        myDataset[0][0] = R.drawable.cacio_e_vino;
        myDataset[0][1] = "Cacio E Vino";
        myDataset[1][1] = "OMC";
        myDataset[1][0] = R.drawable.omc;
        myDataset[2][1] = "Wrap & Go";
        myDataset[2][0] = R.drawable.wrap_and_go;

        mAdapter = new RestaurantListAdapter(myDataset, getResources());
        mRecyclerView.setAdapter(mAdapter);
    }

}
