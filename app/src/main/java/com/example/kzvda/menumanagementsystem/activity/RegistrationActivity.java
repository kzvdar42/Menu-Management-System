package com.example.kzvda.menumanagementsystem.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kzvda.menumanagementsystem.R;
import com.example.kzvda.menumanagementsystem.db.Entity.RestaurantEntity;
import com.example.kzvda.menumanagementsystem.serverApi.Models.LoginModel;
import com.example.kzvda.menumanagementsystem.serverApi.Models.RegistrationModel;
import com.example.kzvda.menumanagementsystem.serverApi.RequestBodies.RegRequest;
import com.example.kzvda.menumanagementsystem.viewModel.RegistrationViewModel;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    List<RestaurantEntity> restaurants;
    RegistrationViewModel mViewModel;

    EditText usernameField;
    EditText passwordField;
    EditText repeatedPasswordField;
    EditText phoneNumberField;
    EditText restaurantNameField;
    RadioButton isUserField;


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

        mViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        usernameField = findViewById(R.id.setUsername);
        passwordField = findViewById(R.id.setPassword);
        repeatedPasswordField = findViewById(R.id.repeatPassword);
        phoneNumberField = findViewById(R.id.setPhoneNumber);
        restaurantNameField = findViewById(R.id.setRestaurantName);
        isUserField = findViewById(R.id.isUser);
    }

    public void onClick(View v) {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        String repeatedPassword = repeatedPasswordField.getText().toString();
        String phoneNumber = phoneNumberField.getText().toString();
        boolean isUser = isUserField.isChecked();
        boolean isRegister = findViewById(R.id.inputLayoutRepeatPassword).getVisibility() == View.VISIBLE;
        if (isRegister) {
            if (checkInput()) {
                RegRequest regRequest = (isUser) ? new RegRequest("registration", username, password) : new RegRequest("registration", username, password, phoneNumber);
                mViewModel.register(regRequest).enqueue(new Callback<RegistrationModel>() {
                    @Override
                    public void onResponse(@NonNull Call<RegistrationModel> call, @NonNull Response<RegistrationModel> response) {
                        if (response.body().getResult().equals("OK")) {
                            registration();
                        } else {
                            Toast.makeText(getApplication().getBaseContext(), "NO: " + response.body().getResult() + " " + response.body().getInfo(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<RegistrationModel> call, @NonNull Throwable t) {
                        Toast.makeText(getApplication().getBaseContext(), getString(R.string.something_is_wrong) + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(getApplication().getBaseContext(), "Incorrect input", Toast.LENGTH_LONG).show();
            }

        } else {
            RegRequest regRequest = new RegRequest("login", username, password);
            mViewModel.login(regRequest).enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    LoginModel body = response.body();
                    if (body.getResult().equals("OK")) {
                        login(body.getLogin(), body.getRights(), body.getConfigrmed(), body.getRest(), body.getPhoneNum());
                    } else {
                        Toast.makeText(getApplication().getBaseContext(), "NO: " + response.body().getResult() + " " + response.body().getInfo(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    Toast.makeText(getApplication().getBaseContext(), getString(R.string.something_is_wrong) + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void onRadioButtonClick(View v) {
        boolean isUser = ((RadioButton) findViewById(R.id.isUser)).isChecked();
        View restaurantName = findViewById(R.id.inputLayoutRestaurantName);
        View phoneNumber = findViewById(R.id.inputLayoutPhoneNumber);
        if (isUser) {
            restaurantName.setVisibility(View.GONE);
            phoneNumber.setVisibility(View.GONE);
        } else {
            restaurantName.setVisibility(View.VISIBLE);
            phoneNumber.setVisibility(View.VISIBLE);
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

    private void registration() {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        String repeatedPassword = repeatedPasswordField.getText().toString();
        String phoneNumber = phoneNumberField.getText().toString();
        boolean isUser = isUserField.isChecked();
        SharedPreferences sharedPref = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", username);
        editor.putInt("usertype", (isUser) ? 0 : 1);
        editor.putBoolean("verified", false);
        editor.putString("phone_num", phoneNumber);
        editor.apply();

        goToMain();
    }

    private void login(String username, int usertype, boolean verified, Integer restaurant, String phoneNumber) {
        SharedPreferences sharedPref = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", username);
        editor.putInt("usertype", usertype);
        if (usertype == 1) {
            editor.putBoolean("verified", verified);
            editor.putInt("restaurantId", restaurant);
        }
        editor.putString("phone_num", phoneNumber);
        editor.apply();

        goToMain();
    }

    private boolean checkInput() {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        String repeatedPassword = repeatedPasswordField.getText().toString();
        String phoneNumber = phoneNumberField.getText().toString();
        boolean isUser = isUserField.isChecked();
        boolean result = username.length() <= 16;
        result = result && password.length() >= 6 && password.equals(repeatedPassword);
        result = result && isUser || (!phoneNumber.isEmpty() && phoneNumber.matches("\\(?\\+[0-9]{1,3}\\)? ?-?[0-9]{1,3} ?-?[0-9]{3,5} ?-?[0-9]{4}( ?-?[0-9]{3})?"));
        return result;
    }

    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
