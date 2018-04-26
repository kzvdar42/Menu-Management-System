package com.example.kzvda.menumanagementsystem.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kzvda.menumanagementsystem.R;
import com.example.kzvda.menumanagementsystem.serverApi.Models.SimpleResponceModel;
import com.example.kzvda.menumanagementsystem.viewModel.ChangePersonalInfoViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePersonalInfoActivity extends AppCompatActivity {

    private TextView usernameField;
    private TextView phoneNumberField;
    private TextView passwordField;
    private TextView repeatedPasswordField;
    private SharedPreferences sharedPref;
    private ChangePersonalInfoViewModel mViewModel;

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

        usernameField.setText(sharedPref.getString("username", "Username"));
        String phone_num = sharedPref.getString("phone_num", "");
        phoneNumberField.setText(sharedPref.getString("phone_num", ""));

        mViewModel = ViewModelProviders.of(this).get(ChangePersonalInfoViewModel.class);
    }

    public void onClick(View v) {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        String repeatedPassword = repeatedPasswordField.getText().toString();
        String phoneNumber = phoneNumberField.getText().toString();

        if (checkInput()) {
            Callback<SimpleResponceModel> callback = new Callback<SimpleResponceModel>() {
                @Override
                public void onResponse(Call<SimpleResponceModel> call, Response<SimpleResponceModel> response) {
                    if (response.body().getResult().equals("OK")) {
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("username", username);
                        editor.putString("phone_num", phoneNumber);
                        editor.commit();
                        finish();
                    } else {
                        Toast.makeText(getApplication().getBaseContext(), "NO: " + response.body().getResult() + " " + response.body().getInfo(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<SimpleResponceModel> call, Throwable t) {
                    Toast.makeText(getApplication().getBaseContext(), getString(R.string.something_is_wrong) + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            };
            if (password.isEmpty()) {
                mViewModel.changePersonalInfo(sharedPref.getString("username", ""), username, phoneNumber).enqueue(callback);
            } else {
                mViewModel.changePersonalInfo(sharedPref.getString("username", ""), username, password, phoneNumber).enqueue(callback);
            }
        } else {
            Toast.makeText(this, getString(R.string.wrong_input), Toast.LENGTH_LONG).show();
        }
    }


    private boolean checkInput() {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        String repeatedPassword = repeatedPasswordField.getText().toString();
        String phoneNumber = phoneNumberField.getText().toString();
        boolean result = username.length() <= 16;
        result = result && (password.isEmpty() || (password.length() >= 6 && password.equals(repeatedPassword)));
        result = result && (phoneNumber.isEmpty() || phoneNumber.matches("^((\\+7|7|8)+([0-9]){10})$"));
        return result;
    }
}
