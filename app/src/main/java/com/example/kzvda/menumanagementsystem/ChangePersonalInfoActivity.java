package com.example.kzvda.menumanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class ChangePersonalInfoActivity extends AppCompatActivity {

    private TextView usernameField;
    private TextView phoneNumberField;
    private TextView passwordField;
    private TextView repeatedPasswordField;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_personal_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.change_personal_info);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usernameField = findViewById(R.id.setUsername);
        phoneNumberField = findViewById(R.id.setPhoneNumber);
        passwordField = findViewById(R.id.setPassword);
        repeatedPasswordField = findViewById(R.id.repeatPassword);

        sharedPref = this.getSharedPreferences("user", Context.MODE_PRIVATE);

        usernameField.setText(sharedPref.getString("username","Username"));
        phoneNumberField.setText(sharedPref.getString("phone_num","Phone number"));
    }

    public void onClick(View v) {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        String repeatedPassword = repeatedPasswordField.getText().toString();
        String phoneNumber = phoneNumberField.getText().toString();


        SharedPreferences.Editor editor = sharedPref.edit();
        if (password.length() != 6 && password.equals(repeatedPassword)) {
            editor.putString("username", username);
            editor.putString("phone_num", phoneNumber);
            editor.apply();

            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
