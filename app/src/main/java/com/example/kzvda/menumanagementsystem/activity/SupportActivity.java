package com.example.kzvda.menumanagementsystem.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.kzvda.menumanagementsystem.R;

import java.util.Objects;

public class SupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.new_report);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> finish());
        ActionBar actionbar = getSupportActionBar();
        Objects.requireNonNull(actionbar).setDisplayHomeAsUpEnabled(true);
    }

    public void onClick(View v) {
        finish();
    }
}
