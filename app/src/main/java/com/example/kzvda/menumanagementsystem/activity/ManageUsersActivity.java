package com.example.kzvda.menumanagementsystem.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.kzvda.menumanagementsystem.R;

import java.util.Objects;

public class ManageUsersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.users);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> finish());
        ActionBar actionbar = getSupportActionBar();
        Objects.requireNonNull(actionbar).setDisplayHomeAsUpEnabled(true);
    }


    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.manage_accounts:
                break;
            case R.id.send_notiication:
                intent = new Intent(this, SendNotificationActivity.class);
                startActivity(intent);
                break;
        }
    }
}
