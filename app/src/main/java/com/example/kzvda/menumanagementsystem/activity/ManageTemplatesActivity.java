package com.example.kzvda.menumanagementsystem.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.kzvda.menumanagementsystem.R;
import com.example.kzvda.menumanagementsystem.adapter.RestaurantMenuAdapter;
import com.example.kzvda.menumanagementsystem.db.Entity.MenuEntity;
import com.example.kzvda.menumanagementsystem.viewModel.ManageTemplatesViewModel;

import java.util.List;

public class ManageTemplatesActivity extends AppCompatActivity {
    private RestaurantMenuAdapter mAdapter;
    private List<MenuEntity> templates;
    private RecyclerView mRecyclerView;
    private ManageTemplatesViewModel mViewModel;
    private int restaurantId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_templates);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.manage_templates);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> finish());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = findViewById(R.id.menu_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RestaurantMenuAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        SharedPreferences sharedPref = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        restaurantId = sharedPref.getInt("restaurantId", 0);
        mViewModel = ViewModelProviders.of(this).get(ManageTemplatesViewModel.class);
        mViewModel.downloadTemplates(restaurantId);
        mViewModel.getTemplates(restaurantId).observe(this, templates -> {
            this.templates = templates;
            mAdapter.setProductList(templates);
        });

    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.restaurant_menu_item:
                int itemPosition = mRecyclerView.getChildAdapterPosition(v);
                MenuEntity template = templates.get(itemPosition);
                Intent i = new Intent(this, EditTemplateActivity.class);
                i.putExtra("id", template.getDishId());
                i.putExtra("name", template.getName());
                i.putExtra("description", template.getDescription());
                i.putExtra("price", template.getPrice());
                i.putExtra("isInMenu", template.isShown());
                i.putExtra("photoSrc", template.getPhotoSrc());
                i.putExtra("restId", template.getRestaurantId());
                startActivity(i);
                break;
            case R.id.fab:
                intent = new Intent(this, NewDishActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mViewModel.downloadTemplates(restaurantId);
    }
}
