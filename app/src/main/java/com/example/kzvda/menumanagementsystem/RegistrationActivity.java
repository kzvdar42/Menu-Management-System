package com.example.kzvda.menumanagementsystem;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

    }

    public void onClick(View v) {
        Drawable photo = ((ImageView) findViewById(R.id.setPhoto)).getDrawable();
        String username = ((TextView)findViewById(R.id.setUsername)).getText().toString();
        String password = ((TextView)findViewById(R.id.setPassword)).getText().toString();
        String repeatedPassword = ((TextView)findViewById(R.id.repeatPassword)).getText().toString();
        String phoneNumber = ((TextView)findViewById(R.id.setPhoneNumber)).getText().toString();
        boolean isUser = ((RadioButton) findViewById(R.id.isUser)).isChecked();

        if (password.equals(repeatedPassword)) {
            HashMap<String, Object> user = new HashMap<>();
            if (isUser) {
                user.put("type",0);
            } else {
                user.put("type",1);
            }
            user.put("username",username);
            user.put("phone_num",phoneNumber);
//            user.put("photo", photo);
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra(MainActivity.EXTRA_MESSAGE,user);
            startActivity(intent);
        }
    }
}
