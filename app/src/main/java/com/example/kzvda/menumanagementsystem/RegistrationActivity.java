package com.example.kzvda.menumanagementsystem;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kzvda.menumanagementsystem.ViewModel.ViewModel;
import com.example.kzvda.menumanagementsystem.db.Entity.RestaurantEntity;

import java.util.List;
import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {

    List<RestaurantEntity> restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.registration_label);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ActionBar actionbar = getSupportActionBar();
        Objects.requireNonNull(actionbar).setDisplayHomeAsUpEnabled(true);


    }

    public void onClick(View v) {
        String username = ((TextView) findViewById(R.id.setUsername)).getText().toString();
        String password = ((TextView) findViewById(R.id.setPassword)).getText().toString();
        String repeatedPassword = ((TextView) findViewById(R.id.repeatPassword)).getText().toString();
        String phoneNumber = ((TextView) findViewById(R.id.setPhoneNumber)).getText().toString();
        boolean isUser = ((RadioButton) findViewById(R.id.isUser)).isChecked();

        SharedPreferences sharedPref = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        ViewModel mViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        mViewModel.getRestaurantsList().observeForever(words -> {
            restaurants = mViewModel.getRestaurantsList().getValue();
            if (password.length() != 6 && password.equals(repeatedPassword)) {
                if (isUser) {
                    editor.putInt("usertype", 0);
                } else {
                    editor.putInt("usertype", 1);
                    int restaurantId = restaurants.get(0).getId();
                    String restaurantName = ((TextView) findViewById(R.id.setRestaurantName)).getText().toString();
                    for (int i = 0; i < restaurants.size(); i++) {
                        RestaurantEntity restaurant = restaurants.get(i);
                        String restName = restaurant.getName();
                        if (restName.equals(restaurantName)) {
                            restaurantId = restaurant.getId();
                            Toast.makeText(this, "The Restaurant ID is: "+restaurantId+" ("+restName+")", Toast.LENGTH_LONG).show();
                        }
                    }
                    editor.putInt("restaurantId", restaurantId);
                }
                editor.putString("username", username);
                editor.putString("phone_num", phoneNumber);
                editor.apply();

                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    public void onRadioButtonClick(View v) {
        boolean isUser = ((RadioButton) findViewById(R.id.isUser)).isChecked();
        View restaurantName = findViewById(R.id.inputLayoutRestaurantName);
        if (isUser) {
            restaurantName.setVisibility(View.GONE);
        } else {
            restaurantName.setVisibility(View.VISIBLE);
        }
    }

    public void changeFragment(View v) {
        boolean isUser = ((RadioButton) findViewById(R.id.isUser)).isChecked();
        View repeatPasswordView = findViewById(R.id.inputLayoutRepeatPassword);
        View phoneNumberView = findViewById(R.id.inputLayoutPhoneNumber);
        View radioButtonGroup = findViewById(R.id.radioGroup);
        View restaurantNameView = findViewById(R.id.inputLayoutRestaurantName);
        TextView startOfText = findViewById(R.id.start_of_text);
        TextView endOfText = findViewById(R.id.end_of_text);
        Button button = findViewById(R.id.button);

        if (repeatPasswordView.getVisibility() == View.VISIBLE) {
            repeatPasswordView.setVisibility(View.GONE);
            radioButtonGroup.setVisibility(View.GONE);
            restaurantNameView.setVisibility(View.GONE);
            phoneNumberView.setVisibility(View.GONE);
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.login);
            button.setText(R.string.login);
            startOfText.setText(R.string.dont_have_an_account);
            endOfText.setText(R.string.create_new_account);

        } else {
            repeatPasswordView.setVisibility(View.VISIBLE);
            radioButtonGroup.setVisibility(View.VISIBLE);
            if (!isUser) {
                restaurantNameView.setVisibility(View.VISIBLE);
            }
            phoneNumberView.setVisibility(View.VISIBLE);
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.registration_label);
            button.setText(R.string.create_account);
            startOfText.setText(R.string.already_have_an_account);
            endOfText.setText(R.string.log_in);
        }
    }
}
