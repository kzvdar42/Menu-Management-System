package com.example.kzvda.menumanagementsystem.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.kzvda.menumanagementsystem.R;

import java.util.Objects;

public class ManageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.manage_label);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> finish());
        ActionBar actionbar = getSupportActionBar();
        Objects.requireNonNull(actionbar).setDisplayHomeAsUpEnabled(true);
    }


    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.manage_accounts:
                intent = new Intent(this, ManageUsersActivity.class);
                startActivity(intent);
                break;
            case R.id.manage_restaurants:
//                intent = new Intent(this, ManageTemplatesActivity.class);
//                startActivity(intent);
                break;
        }
    }
}
