package com.example.kzvda.menumanagementsystem.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kzvda.menumanagementsystem.R;
import com.example.kzvda.menumanagementsystem.serverApi.Models.SimpleResponceModel;
import com.example.kzvda.menumanagementsystem.viewModel.EditTemplateViewModel;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTemplateActivity extends AppCompatActivity {

    private EditText nameField;
    private EditText descriptionField;
    private CheckBox addToMenuBox;
    private EditText priceField;
    private ImageView imageButton;
    private EditTemplateViewModel mViewModel;
    private int dishId;
    private int restaurantId;
    private boolean isInMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_template);

        Intent intent = getIntent();
        restaurantId = intent.getIntExtra("restId", -1);
        dishId = intent.getIntExtra("id", -1);
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        int price = intent.getIntExtra("price", -1);
        isInMenu = intent.getBooleanExtra("isInMenu", false);
        int photoSrc = intent.getIntExtra("photoSrc", -1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> finish());
        ActionBar actionbar = getSupportActionBar();
        Objects.requireNonNull(actionbar).setDisplayHomeAsUpEnabled(true);

        nameField = findViewById(R.id.setName);
        descriptionField = findViewById(R.id.setDescription);
        addToMenuBox = findViewById(R.id.add_to_templates);
        priceField = findViewById(R.id.setPrice);
        imageButton = findViewById(R.id.dishImage);

        nameField.setText(name);
        descriptionField.setText(description);
        priceField.setText(String.valueOf(price));
        addToMenuBox.setChecked(isInMenu);
        imageButton.setImageResource(photoSrc);
        mViewModel = ViewModelProviders.of(this).get(EditTemplateViewModel.class);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateButton:
                String name = nameField.getText().toString();
                String description = descriptionField.getText().toString();
                int price = Integer.valueOf(priceField.getText().toString());
                mViewModel.editTemplate(restaurantId, dishId, name, description, price, isInMenu).enqueue(new Callback<SimpleResponceModel>() {
                    @Override
                    public void onResponse(Call<SimpleResponceModel> call, Response<SimpleResponceModel> response) {
                        if (response.body().getResult().equals("OK")) {
                            finish();
                        } else {
                            Toast.makeText(getApplication().getBaseContext(), "NO: " + response.body().getResult() + " " + response.body().getInfo(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SimpleResponceModel> call, Throwable t) {
                        Toast.makeText(getApplication().getBaseContext(), getString(R.string.something_is_wrong) + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case R.id.deleteButton:
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.are_you_sure))
                        .setCancelable(true)
                        .setPositiveButton("Yes", (dialog, id) -> {
                            mViewModel.deleteTemplate(dishId);
                            finish();
                        })
                        .setNegativeButton("No", null)
                        .show();
                break;
        }
    }
}
