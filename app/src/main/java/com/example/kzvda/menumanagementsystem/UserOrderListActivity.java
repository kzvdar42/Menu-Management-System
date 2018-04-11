package com.example.kzvda.menumanagementsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class UserOrderListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_list);
        mRecyclerView = findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        String[][] myDataset = new String[3][2];
        myDataset[0][0] = "1";
        myDataset[0][1] = "Delivered";

        myDataset[1][0] = "2";
        myDataset[1][1] = "Delivering";

        myDataset[2][0] = "3";
        myDataset[2][1] = "Approved";
        mAdapter = new OrderListAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }
}
